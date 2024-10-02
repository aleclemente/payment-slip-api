package com.aleclemente.paymentslipapi.services;

import com.aleclemente.paymentslipapi.exceptions.PaymentSlipNotFoundException;
import com.aleclemente.paymentslipapi.models.PaymentSlip;
import com.aleclemente.paymentslipapi.repositories.PaymentSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentSlipService
{

    private PaymentSlipRepository repository;

    public PaymentSlipService(final PaymentSlipRepository repository) {
        this.repository = repository;
    }

    private static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional
    public PaymentSlip create(PaymentSlip paymentSlip) {
        String uuid = getUUID();
        paymentSlip.setId(uuid);
        return repository.save(paymentSlip);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Optional<PaymentSlip> findById(String id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(
                () -> new PaymentSlipNotFoundException(id)
        ));
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
