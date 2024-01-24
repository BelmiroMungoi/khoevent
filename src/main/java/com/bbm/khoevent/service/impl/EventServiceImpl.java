package com.bbm.khoevent.service.impl;

import com.bbm.khoevent.dto.request.EventRequest;
import com.bbm.khoevent.dto.response.EventResponse;
import com.bbm.khoevent.exception.EntityNotFoundException;
import com.bbm.khoevent.mapper.Mapper;
import com.bbm.khoevent.model.Community;
import com.bbm.khoevent.model.Event;
import com.bbm.khoevent.repository.EventRepository;
import com.bbm.khoevent.service.CommunityService;
import com.bbm.khoevent.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final Mapper mapper;

    // Vamos injectar essa aqui, para podermos, pegar a
    // comunidade para qual o evento esta a se criado
    private final CommunityService communityService;


    /** @Author: Belmiro Mungoi
    *  Criar Evento
     * 1 - É necessário primeiro pegarmos a comunidade que está a criar o evento,
     *  e para fazer isso chamamos o CommunityService, que já tem um metódo para pegar uma comunidade por id
     *  que é o metódo getCommunityById(id).
     * 2 - Depois disso vamos adicionar os dados do eventRequest nos respectivos campos do Event.
     * 3 - Depois vamos adicionar a comunidade no campo community(comunidade).
     * 4 - E por fim  vamos salvar e retornar uma msg ao cliente
     * */
    @Override
    public String createEvent(EventRequest eventRequest, Long communityId) {
        //Pegando a comunidade que está a criar o evento
        Community community = communityService.getCommunityById(communityId);

        Event event = Event.builder()
                .title(eventRequest.getTitle())
                .description(eventRequest.getDescription())
                .eventLimit(eventRequest.getEventLimit())
                .startTime(eventRequest.getStartTime())
                .endTime(eventRequest.getEndTime())
                .eventDate(eventRequest.getDate())
                .community(community)
                .build();
        eventRepository.save(event);

        return "Evento criado com sucesso";
    }

    @Override
    public List<EventResponse> findAllEvents() {
        return mapper.mapToEventResponseList(eventRepository.findAll());
    }

    @Override
    public EventResponse findEventById(Long id) {
        var event = eventRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("O evento inserido não foi encontrado"));
        return mapper.mapToEventResponse(event);
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("O evento inserido não foi encontrado"));
    }

    @Override
    public String update(EventRequest eventRequest, Long eventId) {
        Event event = getEventById(eventId);
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(eventRequest.getDate());
        event.setEventLimit(eventRequest.getEventLimit());
        event.setStartTime(eventRequest.getStartTime());
        event.setEndTime(event.getEndTime());
        eventRepository.save(event);
        return "Evento actualizado com sucesso";
    }
}
