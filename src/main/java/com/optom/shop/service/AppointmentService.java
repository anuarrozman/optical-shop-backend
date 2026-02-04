package com.optom.shop.service;

import com.optom.shop.model.Appointment;
import com.optom.shop.model.AppointmentStatus; // 1. Add this (We'll move the enum in next step)
import com.optom.shop.model.Patient;
import com.optom.shop.repository.AppointmentRepository;
import com.optom.shop.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public Appointment scheduleAppointment(Long patientId, Appointment appointment) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        appointment.setPatient(patient);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Transactional
    public Appointment updateAppointmentStatus(Long id, String status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Convert string to our Enum
        appointment.setStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment rescheduleAppointment(Long id, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setAppointmentDateTime(newDateTime);
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getTodaysAppointments() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        return appointmentRepository.findByAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(
                startOfDay,
                endOfDay
        );
    }

    public List<Appointment> getAppointmentsForDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return appointmentRepository.findByAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(
                startOfDay,
                endOfDay
        );
    }
}