package com.bbm.khoevent.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;


@Data
public class EventRequest {

    @NotBlank
    @Size(min = 5, max = 200)
    private String title;

    @NotBlank
    @Size(min = 50, max = 1000)
    private String description;

    @NotNull
    private LocalDate date;

    private String status;
    @NotBlank
    private String startTime;
    @NotBlank
    private String endTime;

    @NotNull
    private int eventLimit;
}
