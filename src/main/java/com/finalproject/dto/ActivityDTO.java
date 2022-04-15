package com.finalproject.dto;

import com.finalproject.model.entity.Activity;
import com.finalproject.model.entity.ActivityImportance;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object to move activity data from controller to service
 *
 * @see Activity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDTO {
    @NotBlank(message = "{validation.activity.name.not_blank}")
    @Size(min = 5, max = 100, message = "{validation.activity.name.size}")
    private String name;
    @Size(max = 500, message = "{validation.activity.description.size}")
    private String description;
    @NotNull(message = "{validation.activity.importance.not_null}")
    private ActivityImportance importance;
}
