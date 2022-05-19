package com.yil.company.service;

import com.yil.company.dto.PhoneTypeDto;
import com.yil.company.model.PhoneType;
import com.yil.company.repository.PhoneTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PhoneTypeService {

    private final PhoneTypeRepository phoneTypeRepository;

    @Autowired
    public PhoneTypeService(PhoneTypeRepository phoneTypeRepository) {
        this.phoneTypeRepository = phoneTypeRepository;
    }

    public static PhoneTypeDto toDto(PhoneType f) {
        if (f == null)
            throw new NullPointerException("Phone type is null");
        PhoneTypeDto dto = new PhoneTypeDto();
        dto.setName(f.getName());
        dto.setId(f.getId());
        return dto;
    }

    public PhoneType save(PhoneType phoneType) {
        return phoneTypeRepository.save(phoneType);
    }

    public PhoneType findById(Long id) throws EntityNotFoundException {
        return phoneTypeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
    }

    public List<PhoneType> findAllByDeletedTimeIsNull() {
        return phoneTypeRepository.findAllByDeletedTimeIsNull();
    }

}
