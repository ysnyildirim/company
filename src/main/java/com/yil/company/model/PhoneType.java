package com.yil.company.model;


import com.yil.company.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "PhoneType")
public class PhoneType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PhoneType_Sequence_Generator",
            sequenceName = "Seq_PhoneType",
            allocationSize = 1)
    @GeneratedValue(generator = "PhoneType_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
}

