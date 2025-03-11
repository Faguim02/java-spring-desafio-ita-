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

@RestController
public class TransacaoController {

    @Autowired
    TransacaoRespository transacaoRespository;

    @PostMapping("/transacao")
    ResponseEntity<Void> receiveTransaction(@RequestBody TransacaoModel transacaoReq) {

        try {

            if (transacaoReq.getValor() == null || transacaoReq.getValor() < 0 || transacaoReq.getDataHora() == null) {
                return ResponseEntity.unprocessableEntity().build();
            }

            if (OffsetDateTime.now().isBefore(transacaoReq.getDataHora())) {
                return ResponseEntity.unprocessableEntity().build();
            }

            this.transacaoRespository.save(transacaoReq);

            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (HttpMessageNotReadableException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/transacao")
    ResponseEntity<Void> clearTransactions() {
        this.transacaoRespository.clear();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatistica")
    ResponseEntity<EstatisticaResDto> calculateStatistic() {
        List<TransacaoModel> transacaoModels = this.transacaoRespository.findAllTransactions();

        OffsetDateTime dateActual = OffsetDateTime.now();

        Duration duration = Duration.between(transacaoModels.getLast().getDataHora(), dateActual);

        if (duration.getSeconds() > 60) {
            return ResponseEntity.status(HttpStatus.OK).body(new EstatisticaResDto(0l,0.0,0.0,0.0,0.0));
        }

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

        return ResponseEntity.status(HttpStatus.OK).body(estatisticaResDto);

    }

    @GetMapping("generate")
    String act() {
        return OffsetDateTime.now().toString();
    }
}
