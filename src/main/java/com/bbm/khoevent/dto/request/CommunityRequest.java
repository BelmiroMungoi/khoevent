package com.bbm.khoevent.dto.request;

import lombok.Data;

@Data
public class CommunityRequest {

    private String name;
    private String description;
    private String email;
    private String website;
    private String password;
}
