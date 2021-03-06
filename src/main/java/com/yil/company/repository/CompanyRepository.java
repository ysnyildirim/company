package com.yil.company.repository;

import com.yil.company.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findAllByDeletedTimeIsNull(Pageable pageable);

    Company findByIdAndDeletedTimeIsNull(Long id);
}
