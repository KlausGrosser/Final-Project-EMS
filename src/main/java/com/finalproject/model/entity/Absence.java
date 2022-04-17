package com.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "absences")
public class Absence {

    @Id
    @SequenceGenerator(name = "absenceIdSeq", sequenceName = "absence_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "absenceIdSeq")
    @Column(name = "id")
    private Long id;

    private LocalDate absenceDay;

    @ManyToOne
    private User employee;

    public Absence(LocalDate absenceDay, User employee) {
        this.absenceDay = absenceDay;
        this.employee = employee;
    }
}
