package com.bbm.khoevent.service.impl;

import com.bbm.khoevent.dto.request.AuthenticationRequest;
import com.bbm.khoevent.dto.request.CommunityRequest;
import com.bbm.khoevent.dto.response.CommunityResponse;
import com.bbm.khoevent.dto.response.TokenResponse;
import com.bbm.khoevent.exception.BadRequestException;
import com.bbm.khoevent.exception.EntityNotFoundException;
import com.bbm.khoevent.mapper.Mapper;
import com.bbm.khoevent.model.Community;
import com.bbm.khoevent.model.Token;
import com.bbm.khoevent.repository.CommunityRepository;
import com.bbm.khoevent.repository.TokenRepository;
import com.bbm.khoevent.security.JwtService;
import com.bbm.khoevent.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final AuthenticationManager authenticationManager;
    private final CommunityRepository communityRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Mapper mapper;

    @Override
    public String createCommunity(CommunityRequest request) {
        if (communityRepository.existsByNameOrEmail(request.getName(), request.getEmail())){
            throw new BadRequestException("Erro ao criar comunidade! Nome e Email já foram usados");
        }
        Community communityToBeSaved = Community.builder()
                .name(request.getName())
                .description(request.getDescription())
                .email(request.getEmail())
                .website(request.getWebsite())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        communityRepository.save(communityToBeSaved);
        return "Comunidade Criada com Sucesso";
    }

    @Override
    public List<CommunityResponse> findAllCommunities() {
        var communities = communityRepository.findAll();
        return mapper.mapToCommunityResponseList(communities);
    }

    /** @Author: Belmiro Mungoi
     * Esse metodo é usado para retornar os dados da comunidade ao cliente
     * */
    @Override
    public CommunityResponse findById(Long id) {
        var community = communityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comunidade não foi encontrada"));
        return mapper.mapToCommunityResponse(community);
    }

    /** @Author: Belmiro Mungoi
     * Esse metodo é usado para pegar os dados da entidade comunidade
     * */
    @Override
    public Community getCommunityById(Long id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comunidade não foi encontrada"));
    }

    /** @Author: Belmiro Mungoi
     Actualizar comunidade
    * 1 - É necessário primeiro pegarmos a comunidade a ser editada,
    *  e para fazer isso, usamos o getCommunityById(id), que vai nos retornar a comunidade em questão.
    * 2 - Depois pegar a comunidade e colocar os novos valores.
    * 3 - Depois disso é só fazer o save e retornar uma mensagem ao cliente.
    * */
    @Override
    public String update(CommunityRequest communityRequest, Long id) {
        Community community = getCommunityById(id);
        community.setName(communityRequest.getName());
        community.setDescription(communityRequest.getDescription());
        community.setEmail(communityRequest.getEmail());
        community.setWebsite(communityRequest.getWebsite());
        communityRepository.save(community);

        return "Comunidade actualizada com sucesso";
    }

    @Override
    public TokenResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var community = communityRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(community);
        saveUserToken(community, jwtToken);
        return TokenResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void saveUserToken(Community community, String jwtToken) {
        var token = Token.builder()
                .community(community)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
