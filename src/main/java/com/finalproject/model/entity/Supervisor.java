package com.finalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Supervisor {

    @Id
    @SequenceGenerator(name = "supervisorIdSeq", sequenceName = "supervisor_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supervisorIdSeq")
    @Column(name = "id")
    private long id;

    String fullName;

    String department;
}
