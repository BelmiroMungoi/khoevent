package com.bbm.khoevent.service;

import com.bbm.khoevent.dto.request.EventRequest;
import com.bbm.khoevent.dto.response.EventResponse;
import com.bbm.khoevent.model.Event;

import java.util.List;

public interface EventService {

    String createEvent(EventRequest eventRequest, Long communityId);

    List<EventResponse> findAllEvents();

    EventResponse findEventById(Long id);

    Event getEventById(Long id);

    String update(EventRequest eventRequest, Long eventId);
}
