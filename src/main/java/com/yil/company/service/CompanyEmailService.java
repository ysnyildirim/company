package com.yil.company.service;

import com.yil.company.dto.CompanyEmailDto;
import com.yil.company.model.CompanyEmail;
import com.yil.company.repository.CompanyEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CompanyEmailService {
    private final CompanyEmailRepository companyEmailRepository;

    @Autowired
    public CompanyEmailService(CompanyEmailRepository companyEmailRepository) {
        this.companyEmailRepository = companyEmailRepository;
    }

    public static CompanyEmailDto toDto(CompanyEmail companyEmail) {
        if (companyEmail == null)
            throw new NullPointerException("Company email is null");
        CompanyEmailDto dto = new CompanyEmailDto();
        dto.setCompanyId(companyEmail.getCompanyId());
        dto.setId(companyEmail.getId());
        dto.setEmailTypeId(companyEmail.getEmailTypeId());
        dto.setAddress(companyEmail.getAddress());
        return dto;
    }

    public CompanyEmail findById(Long id) throws EntityNotFoundException {
        return companyEmailRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public CompanyEmail save(CompanyEmail company) {
        return companyEmailRepository.save(company);
    }

    public Page<CompanyEmail> findAllByCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId) {
        return companyEmailRepository.findAllByCompanyIdAndDeletedTimeIsNull(pageable, companyId);
    }
}
