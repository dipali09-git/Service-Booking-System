package com.example.ServiceBookingSystemProject.Repository;
import com.example.ServiceBookingSystemProject.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Retrieve payments for bookings associated with the company’s services.
    @Query("SELECT p FROM Payment p WHERE p.booking.service.company.companyId = :companyId")
    List<Payment> findPaymentsByCompanyId(@Param("companyId") Long companyId);
    @Query("SELECT p FROM Payment p WHERE p.booking.user.userId = :userId")
    List<Payment> findPaymentsByUserId(@Param("userId") Long userId);

}

