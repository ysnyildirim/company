package com.yil.company.service;

import com.yil.company.dto.CompanyDto;
import com.yil.company.model.Company;
import com.yil.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public static CompanyDto toDto(Company company) throws NullPointerException {
        if (company == null)
            throw new NullPointerException("Company is null");
        CompanyDto dto = new CompanyDto();
        dto.setId(company.getId());
        dto.setTitle(company.getTitle());
        dto.setFoundationDate(company.getFoundationDate());
        return dto;
    }

    public Company findById(Long id) throws EntityNotFoundException {
        return companyRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Page<Company> findAllByDeletedTimeIsNull(Pageable pageable) {
        return companyRepository.findAllByDeletedTimeIsNull(pageable);
    }
}
