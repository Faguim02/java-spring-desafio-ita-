package com.example.itau.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("transactio repository test")
public class TransacaoRepositoryTest {

    @InjectMocks
    private TransacaoRespository transacaoRespository;

    @Nested
    class Save {

    }

    @Nested
    class Clear {

    }

    @Nested
    class FindAllTransaction {

    }

}
