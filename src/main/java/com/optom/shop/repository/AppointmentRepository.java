package com.optom.shop.repository;

import com.optom.shop.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(
            LocalDateTime start,
            LocalDateTime end
    );
}