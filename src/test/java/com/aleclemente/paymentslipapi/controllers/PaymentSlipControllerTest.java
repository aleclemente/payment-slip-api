package com.aleclemente.paymentslipapi.controllers;

import com.aleclemente.paymentslipapi.controllers.dtos.NewPaymentSlipDTO;
import com.aleclemente.paymentslipapi.controllers.dtos.PaymentSlipDTO;
import com.aleclemente.paymentslipapi.models.PaymentSlip;
import com.aleclemente.paymentslipapi.repositories.PaymentSlipRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@AutoConfigureMockMvc
//Random port para o teste não atrapalhar as portas da aplicação
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentSlipControllerTest {
    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PaymentSlipRepository paymentSlipRepository;

    @AfterEach
    void tearDown() {
        paymentSlipRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um boleto de pagamento")
    public void testCreate() throws Exception {

        var newPaymentSlipDTO = new NewPaymentSlipDTO(
                "1",
                "10.10",
                "2001-01-01",
                "2024-11-01",
                "",
                "");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(newPaymentSlipDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewPaymentSlipDTO.class);
        Assertions.assertEquals(newPaymentSlipDTO.customerId(), actualResponse.customerId());
        Assertions.assertEquals(newPaymentSlipDTO.documentValue(), actualResponse.documentValue());
        Assertions.assertEquals(newPaymentSlipDTO.dueDate(), actualResponse.dueDate());
        Assertions.assertEquals(newPaymentSlipDTO.paymentValue(), actualResponse.paymentValue());
        Assertions.assertEquals(newPaymentSlipDTO.paymentDate(), actualResponse.paymentDate());
        Assertions.assertEquals(newPaymentSlipDTO.paymentStatus(), actualResponse.paymentStatus());
    }

    @Test
    @DisplayName("Deve obter um boleto de pagamento por id")
    public void testGet() throws Exception {

        var newPaymentSlipDTO = new NewPaymentSlipDTO(
                "1",
                "10.10",
                "2001-01-01",
                "2024-11-01",
                "",
                "");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(newPaymentSlipDTO))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var paymentSlipDTO = mapper.readValue(createResult, PaymentSlipDTO.class);

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/payments/{id}", paymentSlipDTO.id())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, PaymentSlipDTO.class);

        Assertions.assertEquals(paymentSlipDTO.id(), actualResponse.id());
        Assertions.assertEquals(paymentSlipDTO.customerId(), actualResponse.customerId());
        Assertions.assertEquals(paymentSlipDTO.documentValue(), actualResponse.documentValue());
        Assertions.assertEquals(paymentSlipDTO.dueDate(), actualResponse.dueDate());
        Assertions.assertEquals(paymentSlipDTO.paymentValue(), actualResponse.paymentValue());
        Assertions.assertEquals(paymentSlipDTO.paymentDate(), actualResponse.paymentDate());
        Assertions.assertEquals(paymentSlipDTO.paymentStatus(), actualResponse.paymentStatus());
    }
}