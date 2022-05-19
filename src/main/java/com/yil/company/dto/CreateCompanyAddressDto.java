package com.yil.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyAddressDto {
    private Long countryId;
    private Long cityId;
    private Long districtId;
    private Long streetId;
    private Long exteriorDoorId;
    private Long interiorDoorId;
}
