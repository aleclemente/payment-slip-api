package com.aleclemente.paymentslipapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PaymentSlipNotFoundException extends RuntimeException {
    public PaymentSlipNotFoundException(String id) {
        super("payment Slip not found with id: " + id);
    }
}
