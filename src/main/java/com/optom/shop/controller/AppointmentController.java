package com.optom.shop.controller;

import com.optom.shop.model.Appointment;
import com.optom.shop.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> listAll() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/patient/{patientId}")
    public Appointment book(@PathVariable Long patientId, @RequestBody Appointment appointment) {
        return appointmentService.scheduleAppointment(patientId, appointment);
    }

    // Update only the status (SCHEDULED -> COMPLETED)
    @PatchMapping("/{id}/status")
    public Appointment updateStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }

    @PatchMapping("/{id}/reschedule")
    public Appointment reschedule(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDateTime) {
        return appointmentService.rescheduleAppointment(id, newDateTime);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }
}