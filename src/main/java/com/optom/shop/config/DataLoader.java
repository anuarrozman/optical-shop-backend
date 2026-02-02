package com.optom.shop.config;

import com.optom.shop.model.Appointment; // Make sure to import this
import com.optom.shop.model.Patient;
import com.optom.shop.model.Prescription;
import com.optom.shop.repository.AppointmentRepository; // Make sure to import this
import com.optom.shop.repository.PatientRepository;
import com.optom.shop.repository.PrescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository; // 1. Add this

    // 2. Update Constructor to include AppointmentRepository
    public DataLoader(PatientRepository patientRepository,
                      PrescriptionRepository prescriptionRepository,
                      AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (patientRepository.count() == 0) {
            System.out.println("Inserting dummy patients, prescriptions, and appointments...");

            // 3. Create Patient (Anuar)
            Patient p1 = new Patient();
            p1.setFullName("Anuar Rozman");
            p1.setIcNumber("980323106245");

            // 4. Create Prescription for Anuar
            Prescription rx = new Prescription();
            rx.setSphereOd(new BigDecimal("-2.50"));
            rx.setSphereOs(new BigDecimal("-2.25"));
            rx.setPatient(p1);
            p1.getPrescriptions().add(rx);

            // 5. Save Patient first so he has an ID for the appointment
            patientRepository.save(p1);

            // 6. Create Appointment for Anuar
            Appointment appt = new Appointment();
            appt.setPatient(p1);
            appt.setAppointmentDateTime(LocalDateTime.now().plusDays(2)); // Appointment in 2 days
            appt.setReason("Annual Eye Checkup");

            appointmentRepository.save(appt);

            // 7. Create Patient (Fatin)
            Patient p2 = new Patient();
            p2.setFullName("Fatin Aqilah");
            p2.setIcNumber("061227140374");
            patientRepository.save(p2);

            System.out.println("Dummy data loaded successfully!");
        } else {
            System.out.println("Database already has data. Skipping seeder.");
        }
    }
}