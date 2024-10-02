package com.aleclemente.paymentslipapi.controllers;

import com.aleclemente.paymentslipapi.controllers.dtos.PaymentSlipDTO;
import com.aleclemente.paymentslipapi.controllers.mappers.PaymentSlipMapper;
import com.aleclemente.paymentslipapi.exceptions.ValidationException;
import com.aleclemente.paymentslipapi.controllers.dtos.NewPaymentSlipDTO;
import com.aleclemente.paymentslipapi.models.PaymentSlip;
import com.aleclemente.paymentslipapi.services.PaymentSlipService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/paymentSlips")
@Api(tags = "Payment Slip Controller")
public class PaymentSlipController {

    private final PaymentSlipService paymentSlipService;
    private final PaymentSlipMapper paymentSlipMapper;

    public PaymentSlipController(
            final PaymentSlipService paymentSlipService,
            final PaymentSlipMapper paymentSlipMapper) {
        this.paymentSlipService = paymentSlipService;
        this.paymentSlipMapper = paymentSlipMapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody NewPaymentSlipDTO newPaymentSlipDTO) {
        try{
            final var mappedPaymentSlip = paymentSlipMapper.mapPaymentSlip(newPaymentSlipDTO);
            PaymentSlip paymentSlip = paymentSlipService.create(mappedPaymentSlip);
            PaymentSlipDTO result = paymentSlipMapper.mapPaymentSlipDTO(paymentSlip);
            return ResponseEntity.created(URI.create("/payments/" + result.id())).body(result);
        }catch (ValidationException ex){
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try{
            Optional<PaymentSlip> paymentSlip = paymentSlipService.findById(id);
            PaymentSlipDTO result = paymentSlipMapper.mapPaymentSlipDTO(paymentSlip.orElse(null));
            return ResponseEntity.ok(result);
        }catch (ValidationException ex){
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        paymentSlipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
