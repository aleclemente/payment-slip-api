package com.aleclemente.paymentslipapi.models;

import com.aleclemente.paymentslipapi.controllers.dtos.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class PaymentSlip {
    @Id
    private String id;

    @NotEmpty(message = "customerId cannot be null or empty")
    private Long customerId;

    @NotEmpty(message = "documentValue cannot be null or empty")
    private BigDecimal documentValue;

    @NotNull(message = "dueDate cannot be null or empty")
    private LocalDateTime dueDate;

    @NotEmpty(message = "paymentValue cannot be null or empty")
    private BigDecimal paymentValue;

    @NotNull(message = "paymentDate cannot be null or empty")
    private LocalDateTime paymentDate;

    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSlip paymentSlip = (PaymentSlip) o;
        return Objects.equals(id, paymentSlip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
