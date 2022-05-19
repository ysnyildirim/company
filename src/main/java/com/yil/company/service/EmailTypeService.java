package com.yil.company.service;

import com.yil.company.dto.EmailTypeDto;
import com.yil.company.model.EmailType;
import com.yil.company.repository.EmailTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmailTypeService {

    private final EmailTypeRepository emailTypeRepository;

    @Autowired
    public EmailTypeService(EmailTypeRepository emailTypeRepository) {
        this.emailTypeRepository = emailTypeRepository;
    }

    public static EmailTypeDto toDto(EmailType f) {
        if (f == null)
            throw new NullPointerException("Email type is null");
        EmailTypeDto dto = new EmailTypeDto();
        dto.setName(f.getName());
        dto.setId(f.getId());
        return dto;
    }

    public EmailType save(EmailType emailType) {
        return emailTypeRepository.save(emailType);
    }

    public EmailType findById(Long id) throws EntityNotFoundException {
        return emailTypeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
    }

    public List<EmailType> findAllByDeletedTimeIsNull() {
        return emailTypeRepository.findAllByDeletedTimeIsNull();
    }

}
