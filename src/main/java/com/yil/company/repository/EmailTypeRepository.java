package com.yil.company.repository;

import com.yil.company.model.EmailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTypeRepository extends JpaRepository<EmailType, Long> {
    List<EmailType> findAllByDeletedTimeIsNull();

}
