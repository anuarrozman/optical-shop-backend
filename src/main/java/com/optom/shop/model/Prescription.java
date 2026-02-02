package com.optom.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // OD = Right Eye
    private BigDecimal sphereOd;
    private BigDecimal cylOd;
    private Integer axisOd;

    // OS = Left Eye
    private BigDecimal sphereOs;
    private BigDecimal cylOs;
    private Integer axisOs;

    private BigDecimal addPower; // For reading/multifocals
    private String notes;
    private LocalDateTime examDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonBackReference // Prevents infinite loops when converting to JSON
    private Patient patient;
}