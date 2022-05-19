package com.yil.company.repository;

import com.yil.company.model.CompanyAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {
    Page<CompanyAddress> findAllByAndCompanyIdAndDeletedTimeIsNull(Pageable pageable, Long companyId);
}
