package com.finalproject.model.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @SequenceGenerator(name = "companyIdSeq", sequenceName = "company_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyIdSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String name;

    @Column(name = "ceo", nullable = false)
    private String ceo;

    @Column(name = "address", nullable = false)
    private String address;

/*
    @OneToMany (cascade = {CascadeType.ALL})
    @ToString.Exclude
    private Set<User> employee;
*/

}