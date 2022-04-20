package com.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "company")
public class Company {

    @Id
    @SequenceGenerator(name = "companyIdSeq", sequenceName = "company_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyIdSeq")
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String CEO;
    @Column
    private String address;

    @OneToMany (cascade = {CascadeType.ALL})
    private Set<User> employee;

    public Company(String name, String CEO, String address) {
        this.name = name;
        this.CEO = CEO;
        this.address = address;
    }

    public Company(String name) {
        this.name = name;
    }
}
