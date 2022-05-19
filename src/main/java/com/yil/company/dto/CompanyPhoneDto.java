package com.yil.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPhoneDto {
    private Long id;
    private Long companyId;
    private Long number;
    private Long phoneTypeId;
}
