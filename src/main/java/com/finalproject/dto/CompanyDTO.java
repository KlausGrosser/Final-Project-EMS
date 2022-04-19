package com.finalproject.dto;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDTO {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "CEO_name", nullable = false)
    private String CEO;
    @Column(name = "address", nullable = false)
    private String address;
}
