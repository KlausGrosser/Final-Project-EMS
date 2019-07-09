package com.yurwar.trainingcourse.model;

import com.yurwar.trainingcourse.util.converter.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
@SequenceGenerator(name = "activitySeq", sequenceName = "activities_id_seq")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activitySeq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private ActivityImportance importance;

    @Column(name = "start_time", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean finished;

    @ManyToMany(mappedBy = "activities")
    private Set<User> users;
}
