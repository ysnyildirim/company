package com.yil.company.model;

import com.yil.company.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CompanyAddress")
public class CompanyAddress extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "CompanyAddress_Sequence_Generator",
            sequenceName = "Seq_CompanyAddress",
            allocationSize = 1)
    @GeneratedValue(generator = "CompanyAddress_Sequence_Generator")
    @Column(name = "Id")
    private Long id;

    @Column(name = "CompanyId")
    private Long companyId;

    @Column(name = "CountryId")
    private Long countryId;

    @Column(name = "CityId")
    private Long cityId;

    @Column(name = "DistrictId")
    private Long districtId;

    @Column(name = "StreetId")
    private Long streetId;

    @Column(name = "ExteriorDoorId")
    private Long exteriorDoorId;

    @Column(name = "InteriorDoorId")
    private Long interiorDoorId;
}
