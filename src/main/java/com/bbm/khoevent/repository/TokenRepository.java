package com.bbm.khoevent.repository;

import com.bbm.khoevent.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
