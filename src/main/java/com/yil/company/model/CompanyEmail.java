package com.yil.company.model;

import com.yil.company.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CompanyEmail")
public class CompanyEmail extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "CompanyEmail_Sequence_Generator",
            sequenceName = "Seq_CompanyEmail",
            allocationSize = 1)
    @GeneratedValue(generator = "CompanyEmail_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "CompanyId", nullable = false)
    private Long companyId;
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "EmailTypeId", nullable = false)
    private Long emailTypeId;

}