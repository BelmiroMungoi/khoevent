package com.bbm.khoevent.controller;

import com.bbm.khoevent.dto.request.EventRequest;
import com.bbm.khoevent.dto.response.EventResponse;
import com.bbm.khoevent.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @PostMapping("/{id}")
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventRequest request, @PathVariable("id") Long id){
        return new ResponseEntity<>(eventService.createEvent(request, id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> findAllEvents() {
        return ResponseEntity.ok(eventService.findAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findEventById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.findEventById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@Valid @RequestBody EventRequest request, @PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.update(request, id));
    }
}
