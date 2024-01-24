package com.bbm.khoevent.service;



import com.bbm.khoevent.dto.request.TicketRequest;
import com.bbm.khoevent.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {

    String createTicket(TicketRequest request, Long eventId);

    List<TicketResponse> findAllTickets();

    List<TicketResponse> findAllTicketsByEventId(Long eventId);
}
