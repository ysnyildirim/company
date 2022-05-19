package com.yil.company.model;

import com.yil.company.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CompanyPhone")
public class CompanyPhone extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "CompanyPhone_Sequence_Generator",
            sequenceName = "Seq_CompanyPhone",
            allocationSize = 1)
    @GeneratedValue(generator = "CompanyPhone_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "CompanyId", nullable = false)
    private Long companyId;
    @Column(name = "PhoneId", nullable = false)
    private Long number;
    @Column(name = "PhoneTypeId", nullable = false)
    private Long phoneTypeId;

}
