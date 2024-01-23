package com.bbm.khoevent.service.impl;

import com.bbm.khoevent.dto.request.CommunityRequest;
import com.bbm.khoevent.model.Community;
import com.bbm.khoevent.repository.CommunityRepository;
import com.bbm.khoevent.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public String createCommunity(CommunityRequest request) {

        Community communityToBeSaved = Community.builder()
                .name(request.getName())
                .description(request.getDescription())
                .email(request.getEmail())
                .website(request.getWebsite())
                .password(request.getPassword())
                .build();
        communityRepository.save(communityToBeSaved);
        return "Comunidade Criada com Sucesso";
    }
}
