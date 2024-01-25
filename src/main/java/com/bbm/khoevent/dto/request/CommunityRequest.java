package com.bbm.khoevent.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommunityRequest {

    @NotBlank
    @Size(min = 3, max = 120)
    private String name;

    @NotBlank
    @Size(min = 50, max = 1000)
    private String description;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String website;

    @NotBlank
    @Size(min = 5, max = 20)
    private String password;
}
