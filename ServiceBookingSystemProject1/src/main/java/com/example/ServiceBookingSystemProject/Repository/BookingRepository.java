package com.example.ServiceBookingSystemProject.Repository;

import com.example.ServiceBookingSystemProject.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Retrieve all bookings for services owned by the given company.
    @Query("SELECT b FROM Booking b WHERE b.service.company.companyId = :companyId")
    List<Booking> findBookingsByCompanyId(@Param("companyId") Long companyId);

    // Fetch bookings for a specific user
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId")
    List<Booking> findByUserId(@Param("userId") Long userId);
}
