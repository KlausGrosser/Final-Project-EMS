package com.finalproject.model.entity;

import lombok.*;
import javax.persistence.*;
import java.time.Duration;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column
    private String startTime;

    @Column
    private String endTime;

    @Column
    private Duration duration;

    @ManyToOne
    private User username;

}
