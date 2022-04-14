package com.finalproject.dto;

import com.finalproject.model.entity.Leave;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Data transfer object to transport activity duration data from controller to service
 *
 * @see Leave
 */
@Data
public class LeaveDurationDTO {
    @PositiveOrZero(message = "{validation.leave.duration.days.positive_or_zero}")
    @NotNull(message = "{validation.leave.duration.days.not_null}")
    private Integer days;

}
