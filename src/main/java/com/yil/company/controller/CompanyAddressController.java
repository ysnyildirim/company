package com.yil.company.controller;

import com.yil.company.base.ApiHeaders;
import com.yil.company.base.PageDto;
import com.yil.company.dto.CompanyAddressDto;
import com.yil.company.dto.CreateCompanyAddressDto;
import com.yil.company.model.CompanyAddress;
import com.yil.company.service.CompanyAddressService;
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
@RequestMapping(value = "v1/companys/{companyId}/address")
public class CompanyAddressController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final CompanyAddressService companyAddressService;

    @Autowired
    public CompanyAddressController(CompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    @GetMapping
    public ResponseEntity<PageDto<CompanyAddressDto>> findAll(
            @PathVariable Long companyId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<CompanyAddress> companyPage = companyAddressService.findAllByAndCompanyIdAndDeletedTimeIsNull(pageable, companyId);
            PageDto<CompanyAddressDto> pageDto = PageDto.toDto(companyPage, CompanyAddressService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyAddressDto> findById(
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            CompanyAddress entity;
            try {
                entity = companyAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            CompanyAddressDto dto = CompanyAddressService.toDto(entity);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyAddressId,
                                 @PathVariable Long companyId,
                                 @Valid @RequestBody CreateCompanyAddressDto dto) {
        try {
            CompanyAddress entity = new CompanyAddress();
            entity.setCompanyId(companyId);
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity.setCreatedUserId(authenticatedCompanyAddressId);
            entity.setCreatedTime(new Date());
            entity = companyAddressService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyAddressId,
                                  @PathVariable Long companyId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreateCompanyAddressDto dto) {
        try {
            CompanyAddress entity = null;
            try {
                entity = companyAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity = companyAddressService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedCompanyAddressId,
                                         @PathVariable Long companyId,
                                         @PathVariable Long id) {
        try {
            CompanyAddress entity;
            try {
                entity = companyAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getCompanyId().equals(companyId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedCompanyAddressId);
            entity.setDeletedTime(new Date());
            entity = companyAddressService.save(entity);
            return ResponseEntity.ok("Company address deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
