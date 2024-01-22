package com.bbm.khoevent.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String attendeeName;
    private String attendeeEmail;
    private boolean isChecked;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
