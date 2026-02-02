package com.optom.shop.repository;

import com.optom.shop.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Spring generates: SELECT * FROM patients WHERE ic_number = ?
    Optional<Patient> findByIcNumber(String icNumber);

    // Optional: Search by name (case-insensitive)
    List<Patient> findByFullNameContainingIgnoreCase(String name);
}