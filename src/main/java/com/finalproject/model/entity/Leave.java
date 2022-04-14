package com.finalproject.model.entity;

import com.finalproject.util.converter.DurationConverter;
import com.finalproject.util.converter.LocalDateTimeConverter;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"leavesRequests"})
@ToString(exclude = {"leavesRequests"})
@Entity
@Table(name = "leaves")
public class Leave {
    @Id
    @SequenceGenerator(name = "leaveIdSeq", sequenceName = "leaves_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leaveIdSeq")
    @Column(name = "id")
    private Long id;

    @Column(length = 500)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private LeaveReason leaveReason;

    @Enumerated(value = EnumType.STRING)
    private LeaveStatus leaveStatus;

    @Column(name = "start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @Column(name = "end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @Column(name = "duration")
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "leaves", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users;

}
