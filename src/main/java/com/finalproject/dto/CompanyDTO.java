package com.finalproject.dto;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompanyDTO {

    @Column
    private String name;
    @Column
    private String CEO;
    @Column
    private String address;
}
