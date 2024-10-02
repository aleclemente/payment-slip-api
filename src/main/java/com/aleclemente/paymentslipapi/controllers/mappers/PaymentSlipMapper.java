package com.aleclemente.paymentslipapi.controllers.mappers;

import com.aleclemente.paymentslipapi.controllers.dtos.NewPaymentSlipDTO;
import com.aleclemente.paymentslipapi.controllers.dtos.PaymentSlipDTO;
import com.aleclemente.paymentslipapi.models.PaymentSlip;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentSlipMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public PaymentSlip mapPaymentSlip(final NewPaymentSlipDTO paymentSlipDTO) {
        return MODEL_MAPPER.map(paymentSlipDTO, PaymentSlip.class);
    }

    public PaymentSlipDTO mapPaymentSlipDTO(final PaymentSlip paymentSlip){
        return MODEL_MAPPER.map(paymentSlip, PaymentSlipDTO.class);
    }
}
