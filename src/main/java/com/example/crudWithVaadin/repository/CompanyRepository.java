package com.example.crudWithVaadin.repository;

import com.example.crudWithVaadin.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
