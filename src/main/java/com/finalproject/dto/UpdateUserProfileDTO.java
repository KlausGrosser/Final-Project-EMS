package com.finalproject.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdateUserProfileDTO {

    @NotBlank(message = "{validation.user.password.not_blank}")
    @Size(min = 5, max = 39, message = "{validation.user.password.size}")
    private String password;

    private String address;

    private int telephone;
}
