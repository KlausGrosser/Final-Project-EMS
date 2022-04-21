package com.finalproject.dto;

import com.finalproject.model.entity.Authority;
import com.finalproject.model.entity.Department;
import com.finalproject.model.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Data transfer object to transport user data from update method in controller to service
 *
 * @see User
 */
@Data
public class UpdateUserDTO {
    private long id;

    @NotBlank(message = "{validation.user.first_name.not_blank}")
    @Size(min = 2, max = 50, message = "{validation.user.first_name.size}")
    private String firstName;

    @NotBlank(message = "{validation.user.last_name.not_blank}")
    @Size(min = 2, max = 50, message = "{validation.user.last_name.size}")
    private String lastName;

    @NotBlank(message = "{validation.user.username.not_blank}")
    @Size(min = 5, max = 39, message = "{validation.user.username.size}")
    private String username;

    private String password;

    @Size(min = 1, message = "{validation.user.authorities.size}")
    private Set<Authority> authorities;

    private Department department;

    private String supervisorName;

    private boolean supervisorRole;

    private String company;
}
