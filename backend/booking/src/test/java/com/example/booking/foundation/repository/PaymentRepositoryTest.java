package com.example.booking.foundation.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.booking.entity.Payment;
import com.example.booking.foundation.repository.impl.PaymentRepositoryImpl;

@SpringBootTest
@ActiveProfiles("test")
@Import(PaymentRepositoryImpl.class)
public class PaymentRepositoryTest {

    @Autowired
    IPaymentRepository repo;

    @Test
    void shouldSavePayment() {

        Payment p = new Payment(100, "SUCCESS");

        Payment saved = repo.save(p);

        assertNotNull(saved.getId());
    }

}
