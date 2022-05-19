package com.yil.company.service;

import com.yil.company.dto.CompanyAddressDto;
import com.yil.company.model.CompanyAddress;
import com.yil.company.repository.CompanyAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CompanyAddressService {
    private final CompanyAddressRepository companyAddressRepository;

    @Autowired
    public CompanyAddressService(CompanyAddressRepository companyAddressRepository) {
        this.companyAddressRepository = companyAddressRepository;
    }

    public static CompanyAddressDto toDto(CompanyAddress entity) {
        if (entity == null)
            throw new NullPointerException("Company address is null");
        CompanyAddressDto dto = new CompanyAddressDto();
        dto.setId(entity.getId());
        dto.setCompanyId(dto.getCompanyId());
        dto.setCountryId(dto.getCountryId());
        dto.setCityId(dto.getCityId());
        dto.setDistrictId(dto.getDistrictId());
        dto.setStreetId(dto.getStreetId());
        dto.setExteriorDoorId(dto.getExteriorDoorId());
        dto.setInteriorDoorId(dto.getInteriorDoorId());
        return dto;
    }

    public CompanyAddress findById(Long id) throws EntityNotFoundException {
        return companyAddressRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public CompanyAddress save(CompanyAddress company) {
        return companyAddressRepository.save(company);
    }

    public Page<CompanyAddress> findAllByAndCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId) {
        return companyAddressRepository.findAllByAndCompanyIdAndDeletedTimeIsNull(pageable, companyId);
    }
}
