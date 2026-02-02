package com.optom.shop.controller;

import com.optom.shop.model.Patient;
import com.optom.shop.model.Prescription;
import com.optom.shop.service.PatientService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    // 4. Add a prescription to a specific patient
    @PostMapping("/{id}/prescriptions")
    public Prescription addPrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        return patientService.addPrescription(id, prescription);
    }

    // 5. Get all prescriptions for a specific patient
    @GetMapping("/{id}/prescriptions")
    public List<Prescription> getPatientPrescriptions(@PathVariable Long id) {
        return patientService.getPatientById(id).getPrescriptions();
    }

    // 6. Search patient by IC Number
    // URL will look like: http://localhost:8080/api/patients/search?ic=980323106245
    @GetMapping("/search")
    public Patient getByIc(@RequestParam String ic) {
        return patientService.getPatientByIc(ic);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientService.updatePatient(id, patientDetails);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}