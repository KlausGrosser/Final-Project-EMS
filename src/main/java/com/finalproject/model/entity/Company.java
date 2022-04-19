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
@Table(name = "company")
public class Company {

    @Id
    @SequenceGenerator(name = "companyIdSeq", sequenceName = "company_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyIdSeq")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "CEO_name", nullable = false)
    private String CEO;
    @Column(name = "address", nullable = false)
    private String address;

}
