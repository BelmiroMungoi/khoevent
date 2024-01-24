package com.bbm.khoevent.dto.request;

import lombok.Data;

import java.time.LocalDate;


@Data
public class EventRequest {

    private String title;
    private String description;
    private LocalDate date;
    private String status;
    private String startTime;
    private String endTime;
    private int eventLimit;
}
