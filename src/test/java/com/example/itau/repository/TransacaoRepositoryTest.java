package com.example.itau.repository;

import com.example.itau.model.TransacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("transactio repository test")
public class TransacaoRepositoryTest {

    @InjectMocks
    private TransacaoRespository transacaoRespository;

    @Test
    void shoudSaveTransaction() {
        TransacaoModel transacaoModel = new TransacaoModel();
        transacaoModel.setValor(10.5);
        transacaoModel.setDataHora(OffsetDateTime.now());

        transacaoRespository.save(transacaoModel);

        List<TransacaoModel> transacaoModels = transacaoRespository.findAllTransactions();

        Assertions.assertEquals(transacaoModels.getFirst(), transacaoModel);

    }

}
