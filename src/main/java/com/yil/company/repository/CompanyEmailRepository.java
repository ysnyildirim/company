package com.yil.company.repository;

import com.yil.company.model.CompanyEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEmailRepository extends JpaRepository<CompanyEmail, Long> {
    Page<CompanyEmail> findAllByCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId);
}
