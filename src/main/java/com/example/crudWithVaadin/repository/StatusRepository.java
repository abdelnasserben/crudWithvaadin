package com.example.crudWithVaadin.repository;

import com.example.crudWithVaadin.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
