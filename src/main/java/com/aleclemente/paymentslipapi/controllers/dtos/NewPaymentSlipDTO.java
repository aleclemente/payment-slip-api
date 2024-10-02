package com.aleclemente.paymentslipapi.controllers.dtos;

public record NewPaymentSlipDTO(
        String customerId,
        String documentValue,
        String dueDate,
        String paymentValue,
        String paymentDate,
        String paymentStatus
) {
}
