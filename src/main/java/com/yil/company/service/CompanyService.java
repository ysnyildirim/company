package com.yil.company.service;

import com.yil.company.dto.CompanyDto;
import com.yil.company.dto.CreateCompanyDto;
import com.yil.company.exception.CompanyNotFound;
import com.yil.company.model.Company;
import com.yil.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        dto.setContactId(company.getContactId());
        dto.setTitle(company.getTitle());
        dto.setFoundationDate(company.getFoundationDate());
        return dto;
    }

    public Company findById(Long id) throws CompanyNotFound {
        return companyRepository.findById(id).orElseThrow(() -> {
            throw new CompanyNotFound();
        });
    }

    public Company findByIdAndDeletedTimeIsNull(Long id) throws CompanyNotFound {
        Company company = companyRepository.findByIdAndDeletedTimeIsNull(id);
        if (company == null)
            throw new CompanyNotFound();
        return company;
    }


    public Company save(CreateCompanyDto dto, long userId) throws CompanyNotFound {
        Company company = new Company();
        return getCompanyInform(dto, userId, company);
    }

    private Company getCompanyInform(CreateCompanyDto dto, long userId, Company company) {
        company.setTitle(dto.getTitle());
        company.setFoundationDate(new Date());
        company.setContactId(dto.getContactId());
        if (company.getId() == null) {
            company.setCreatedUserId(userId);
            company.setCreatedDate(new Date());
        } else {
            company.setLastModifyUserId(userId);
            company.setLastModifyDate(new Date());
        }
        return companyRepository.save(company);
    }

    public Company replace(long id, CreateCompanyDto dto, long userId) throws CompanyNotFound {
        Company company = findById(id);
        return getCompanyInform(dto, userId, company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public Page<Company> findAllByDeletedTimeIsNull(Pageable pageable) {
        return companyRepository.findAllByDeletedTimeIsNull(pageable);
    }
}
