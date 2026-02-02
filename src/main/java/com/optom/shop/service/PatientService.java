package com.optom.shop.service;

import com.optom.shop.model.Patient;
import com.optom.shop.model.Prescription;
import com.optom.shop.repository.PatientRepository;
import com.optom.shop.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor // This automatically creates a constructor for all "final" fields
public class PatientService {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public Patient createPatient(Patient patient) {
        // Business Logic Example: Check if IC Number already exists
        if (patient.getIcNumber() != null) {
            // You could add a custom check here later
        }
        return patientRepository.save(patient);
    }

    public Prescription addPrescription(Long patientId, Prescription prescription) {
        Patient patient = getPatientById(patientId);
        prescription.setPatient(patient);
        return prescriptionRepository.save(prescription);
    }

    public Patient getPatientByIc(String icNumber) {
        return patientRepository.findByIcNumber(icNumber)
                .orElseThrow(() -> new RuntimeException("Patient not found with IC: " + icNumber));
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = getPatientById(id);
        patient.setFullName(patientDetails.getFullName());
        patient.setIcNumber(patientDetails.getIcNumber());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}