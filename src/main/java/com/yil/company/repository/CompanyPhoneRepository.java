package com.yil.company.repository;

import com.yil.company.model.CompanyPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyPhoneRepository extends JpaRepository<CompanyPhone, Long> {
    Page<CompanyPhone> findAllByAndCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId);
}
