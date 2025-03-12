package com.example.itau.controller;

import com.example.itau.dto.EstatisticaResDto;
import com.example.itau.model.TransacaoModel;
import com.example.itau.repository.TransacaoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class TransacaoController {

    @Autowired
    TransacaoRespository transacaoRespository;
    Logger logger = Logger.getLogger(TransacaoController.class.getName());

    @PostMapping("/transacao")
    ResponseEntity<Void> receiveTransaction(@RequestBody TransacaoModel transacaoReq) {

        try {

            logger.log(Level.INFO, "Verificando os dados do usuario...");

            if (transacaoReq.getValor() == null || transacaoReq.getValor() < 0 || transacaoReq.getDataHora() == null) {
                logger.log(Level.WARNING, "o valor da transação ou dataHora está vazia");
                return ResponseEntity.unprocessableEntity().build();
            }

            if (OffsetDateTime.now().isBefore(transacaoReq.getDataHora())) {
                logger.log(Level.WARNING, "a data da transação está invalida, data futura");
                return ResponseEntity.unprocessableEntity().build();
            }

            logger.log(Level.INFO, "Os dados estão corretos!");
            logger.log(Level.INFO, "Salvando informações...");

            this.transacaoRespository.save(transacaoReq);

            logger.log(Level.INFO, "Salvo com sucesso!");

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/transacao")
    ResponseEntity<Void> clearTransactions() {
        logger.log(Level.INFO, "Deletando transações...");

        this.transacaoRespository.clear();

        logger.log(Level.INFO, "Deletado com sucesso!");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    ResponseEntity<EstatisticaResDto> calculateStatistic() {
        logger.log(Level.INFO, "Buscando transações...");

        List<TransacaoModel> transacaoModels = this.transacaoRespository.findAllTransactions();

        OffsetDateTime dateActual = OffsetDateTime.now();

        Duration duration = Duration.between(transacaoModels.getLast().getDataHora(), dateActual);

        logger.log(Level.INFO, "Verificando data das transações...");

        if (duration.getSeconds() > 60) {
            logger.log(Level.WARNING, "a data das transações foram a mais de 60 segundos atrás");
            return ResponseEntity.status(HttpStatus.OK).body(new EstatisticaResDto(0L,0.0,0.0,0.0,0.0));
        }

        logger.log(Level.INFO, "Calculando...");

        DoubleSummaryStatistics statistics = transacaoModels.stream()
                .mapToDouble(TransacaoModel::getValor)
                .summaryStatistics();

        EstatisticaResDto estatisticaResDto = new EstatisticaResDto(
                statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                statistics.getMin(),
                statistics.getMax()
        );

        logger.log(Level.INFO, "OK!");

        return ResponseEntity.status(HttpStatus.OK).body(estatisticaResDto);

    }

    @GetMapping("generate")
    String generateDateActual() {
        return OffsetDateTime.now().toString();
    }
}
