package com.finalproject.dto;

import com.finalproject.model.entity.Leave;
import com.finalproject.model.entity.LeaveReason;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Data transfer object to move activity data from controller to service
 *
 * @see Leave
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeaveDTO {

    @Size(max = 500, message = "{validation.leave.description.size}")
    private String description;
    @NotNull(message = "{validation.leave.leaveReason.not_null}")
    private LeaveReason leaveReason;
    @NotNull(message = "{validation.leave.startTime.not_null}" )
    private Date startTime;
    @NotNull(message = "{validation.leave.endTime.not_null}" )
    private Date endTime;
}
