package com.bbm.khoevent.service;

import java.time.LocalDate;

public interface EmailService {

    void sendEmail(String name, String destination, String eventName, LocalDate date);
}
