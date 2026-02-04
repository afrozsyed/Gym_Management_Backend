package com.gym.payment.controller;

import com.gym.common.response.ApiResponse;
import com.gym.payment.dto.PaymentRequest;
import com.gym.payment.dto.PaymentResponse;
import com.gym.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@PreAuthorize("hasAnyRole('ADMIN','STAFF')")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> create(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Payment recorded", paymentService.create(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> list(@RequestParam(required = false) Long memberId,
                                                                   @RequestParam(required = false)
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                   LocalDate from,
                                                                   @RequestParam(required = false)
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                   LocalDate to) {
        return ResponseEntity.ok(ApiResponse.success("Payments fetched", paymentService.list(memberId, from, to)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Payment receipt", paymentService.getById(id)));
    }
}
