package com.bbm.khoevent.repository;

import com.bbm.khoevent.model.Event;
import com.bbm.khoevent.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    //Esse metódo vai fazer uma busca por todos os tickets pertencentes ao evento selecionado
    List<Ticket> findAllByEventId(Long eventId);

    //Esse metódo vai verificar se um usuário já está escrito no evento selecionado
    boolean existsByAttendeeEmailAndEvent(String email, Event event);
}
