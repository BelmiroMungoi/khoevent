package com.bbm.khoevent.controller;

import com.bbm.khoevent.dto.request.TicketRequest;
import com.bbm.khoevent.dto.response.TicketResponse;
import com.bbm.khoevent.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    // Aqui não há nada de novo, já sabem sobre isso, kkkkkkkkkk
    @PostMapping("/{id}")
    public ResponseEntity<String> create(@Valid @RequestBody TicketRequest request,
                                               @PathVariable("id") Long eventId) {
        return new ResponseEntity<>(ticketService.createTicket(request, eventId), CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAllTickets() {
        return ResponseEntity.ok(ticketService.findAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TicketResponse>> findAllTicketsByEventId(@PathVariable("id") Long eventId) {
        return ResponseEntity.ok(ticketService.findAllTicketsByEventId(eventId));
    }
}
