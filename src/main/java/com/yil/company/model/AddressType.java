package com.yil.company.model;

import com.yil.company.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "AddressType")
public class AddressType extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "AddressType_Sequence_Generator",
            sequenceName = "Seq_AddressType",
            allocationSize = 1)
    @GeneratedValue(generator = "AddressType_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
}
