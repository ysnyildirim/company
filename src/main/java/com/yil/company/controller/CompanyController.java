package com.yil.company.controller;

import com.yil.company.base.ApiConstant;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyDto;
import com.yil.company.dto.CreateCompanyDto;
import com.yil.company.model.Company;
import com.yil.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/cpy/v1/companies")
public class CompanyController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Tüm şirket bilgilerini getirir.")
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

    @Operation(summary = "Id bazlı şirket bilgilerini getirir.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        CompanyDto dto = CompanyService.toDto(company);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Yeni bir şirket bilgisi eklemek için kullanılır.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @Valid @RequestBody CreateCompanyDto request) {
        companyService.save(request, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Şirket bilgisi eklendi");
    }


    @Operation(summary = "Id bazlı şirket bilgisi güncellemek için kullanılır.")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                          @PathVariable Long id,
                                          @Valid @RequestBody CreateCompanyDto request) {
        companyService.replace(id, request, authenticatedUserId);
        return ResponseEntity.ok().body("Şirket bilgisi güncellendi.");
    }

    @Operation(summary = "Id bazlı şirket bilgisi silmek için kullanılır.")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @PathVariable Long id) {
        Company company = companyService.findById(id);
        companyService.delete(company);
        return ResponseEntity.ok("Şirket datası silindi.");
    }


}
