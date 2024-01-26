package com.bbm.khoevent.service.impl;

import com.bbm.khoevent.dto.request.TicketRequest;
import com.bbm.khoevent.dto.response.TicketResponse;
import com.bbm.khoevent.exception.BadRequestException;
import com.bbm.khoevent.mapper.Mapper;
import com.bbm.khoevent.model.Event;
import com.bbm.khoevent.model.Ticket;
import com.bbm.khoevent.repository.TicketRepository;
import com.bbm.khoevent.service.EmailService;
import com.bbm.khoevent.service.EventService;
import com.bbm.khoevent.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final Mapper mapper;
    private final EventService eventService;
    private final EmailService emailService;
    private final TicketRepository ticketRepository;

    @Override
    public String createTicket(TicketRequest request, Long eventId) {
        // Pegando o evento para o qual será feito o ticket
        Event event = eventService.getEventById(eventId);

        // Verifiquem o TicketRepository
        // Antes de criar o ticket, é necessário verificar se já existe um
        // ticket criando para o evento com o email do participante
        if (ticketRepository.existsByAttendeeEmailAndEvent(request.getAttendeeEmail(), event)) {
            throw new BadRequestException("Hahahahah hahahah! Você já está inscrito pra esse evento!");
        }
        Ticket ticketToBeSaved = Ticket.builder()
                .attendeeName(request.getAttendeeName())
                .attendeeEmail(request.getAttendeeEmail())
                .isChecked(false)
                .event(event)
                .build();
        var savedTicket = ticketRepository.save(ticketToBeSaved);

        emailService.sendEmail(savedTicket.getAttendeeName(),
                savedTicket.getAttendeeEmail(),
                event.getTitle(),
                event.getEventDate());
        return "Ticket criado com sucesso! Por favor verifique o seu email!";
    }

    // Pegando todos os tickets
    @Override
    public List<TicketResponse> findAllTickets() {
        return mapper.mapToTicketResponseList(ticketRepository.findAll());
    }

    // Pegando todos os tickets de um evento em especifico
    @Override
    public List<TicketResponse> findAllTicketsByEventId(Long eventId) {
        return mapper.mapToTicketResponseList(ticketRepository.findAllByEventId(eventId));
    }
}
