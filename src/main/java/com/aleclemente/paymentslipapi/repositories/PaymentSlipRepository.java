package com.aleclemente.paymentslipapi.repositories;

import com.aleclemente.paymentslipapi.models.PaymentSlip;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentSlipRepository extends CrudRepository<PaymentSlip, String> {

    Optional<PaymentSlip> findById(String id);

    void deleteById(String id);
}
