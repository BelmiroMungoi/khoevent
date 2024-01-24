package com.bbm.khoevent.mapper;

import com.bbm.khoevent.dto.response.CommunityResponse;
import com.bbm.khoevent.dto.response.EventResponse;
import com.bbm.khoevent.dto.response.TicketResponse;
import com.bbm.khoevent.model.Community;
import com.bbm.khoevent.model.Event;
import com.bbm.khoevent.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper modelMapper;

    public CommunityResponse mapToCommunityResponse(Community community) {
        return modelMapper.map(community, CommunityResponse.class);
    }

    public List<CommunityResponse> mapToCommunityResponseList(List<Community> communities) {
        return communities.stream().map(this::mapToCommunityResponse)
                .collect(Collectors.toList());
    }

    public EventResponse mapToEventResponse(Event event) {
        return modelMapper.map(event, EventResponse.class);
    }

    public List<EventResponse> mapToEventResponseList(List<Event> events) {
        return events.stream().map(this::mapToEventResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse mapToTicketResponse(Ticket ticket) {
        return modelMapper.map(ticket, TicketResponse.class);
    }

    public List<TicketResponse> mapToTicketResponseList(List<Ticket> tickets) {
        return tickets.stream().map(this::mapToTicketResponse)
                .collect(Collectors.toList());
    }
}
