package com.yil.company.controller;

import com.yil.company.base.ApiConstant;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyDto;
import com.yil.company.dto.CreateCompanyDto;
import com.yil.company.model.Company;
import com.yil.company.service.CompanyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/cmp/v1/companies")
public class CompanyController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companyPage = companyService.findAllByDeletedTimeIsNull(pageable);
        PageDto<CompanyDto> pageDto = PageDto.toDto(companyPage, CompanyService::toDto);
        return ResponseEntity.ok(pageDto);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        CompanyDto dto = CompanyService.toDto(company);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyDto> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                             @Valid @RequestBody CreateCompanyDto request) {
        Company company = new Company();
        company.setTitle(request.getTitle());
        company.setFoundationDate(request.getFoundationDate());
        company.setContactId(request.getContactId());
        company.setCreatedUserId(authenticatedUserId);
        company.setCreatedTime(new Date());
        company = companyService.save(company);
        CompanyDto responce = CompanyService.toDto(company);
        return ResponseEntity.created(null).body(responce);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyDto> replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                              @PathVariable Long id,
                                              @Valid @RequestBody CreateCompanyDto request) {
        Company company = companyService.findByIdAndDeletedTimeIsNull(id);
        company.setTitle(request.getTitle());
        company.setFoundationDate(request.getFoundationDate());
        company.setContactId(request.getContactId());
        company = companyService.save(company);
        CompanyDto responce = CompanyService.toDto(company);
        return ResponseEntity.ok(responce);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) {
        try {
            Company company;
            try {
                company = companyService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            company.setDeletedUserId(authenticatedUserId);
            company.setDeletedTime(new Date());
            companyService.save(company);
            return ResponseEntity.ok("Company deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
