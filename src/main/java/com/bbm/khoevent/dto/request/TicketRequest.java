package com.bbm.khoevent.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TicketRequest {

    private String attendeeName;
    private String attendeeEmail;
    private boolean isChecked;
}
