package com.yil.company.service;

import com.yil.company.dto.CompanyPhoneDto;
import com.yil.company.model.CompanyPhone;
import com.yil.company.repository.CompanyPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CompanyPhoneService {
    private final CompanyPhoneRepository companyPhoneRepository;

    @Autowired
    public CompanyPhoneService(CompanyPhoneRepository companyPhoneRepository) {
        this.companyPhoneRepository = companyPhoneRepository;
    }

    public static CompanyPhoneDto toDto(CompanyPhone companyPhone) {
        if (companyPhone == null)
            throw new NullPointerException("Company phone is null");
        CompanyPhoneDto dto = new CompanyPhoneDto();
        dto.setId(companyPhone.getId());
        dto.setCompanyId(companyPhone.getCompanyId());
        dto.setPhoneTypeId(companyPhone.getPhoneTypeId());
        dto.setNumber(companyPhone.getNumber());
        return dto;
    }

    public CompanyPhone findById(Long id) throws EntityNotFoundException {
        return companyPhoneRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public CompanyPhone save(CompanyPhone company) {
        return companyPhoneRepository.save(company);
    }

    public Page<CompanyPhone> findAllByAndCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId) {
        return companyPhoneRepository.findAllByAndCompanyIdAndDeletedTimeIsNull(pageable, companyId);
    }
}
