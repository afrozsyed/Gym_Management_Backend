package com.gym.payment.repository;

import com.gym.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("select p from Payment p where (:memberId is null or p.member.id = :memberId) " +
        "and (:fromDate is null or p.paymentDate >= :fromDate) " +
        "and (:toDate is null or p.paymentDate <= :toDate)")
    List<Payment> findByFilters(@Param("memberId") Long memberId,
                               @Param("fromDate") LocalDate fromDate,
                               @Param("toDate") LocalDate toDate);
}
