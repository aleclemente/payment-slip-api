package com.aleclemente.paymentslipapi.controllers.dtos;

public record PaymentSlipDTO(
        String id,
        String customerId,
        String documentValue,
        String dateOfBirth,
        String dueDate,
        String paymentValue,
        String paymentDate,
        String paymentStatus
) {
}
