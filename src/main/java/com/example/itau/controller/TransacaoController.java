package com.example.itau.controller;

import com.example.itau.dto.EstatisticaResDto;
import com.example.itau.model.TransacaoModel;
import com.example.itau.repository.TransacaoRespository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Tag(name = "Transações")
@RestController
public class TransacaoController {

    @Autowired
    TransacaoRespository transacaoRespository;
    Logger logger = Logger.getLogger(TransacaoController.class.getName());

    @Operation(summary = "salvar transação", description = "essa rota, recebe o valor e a data da transação e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A transação foi aceita (ou seja foi validada, está válida e foi registrada)"),
            @ApiResponse(responseCode = "422", description = "A transação não foi aceita por qualquer motivo (1 ou mais dos critérios de aceite não foram atendidos - por exemplo: uma transação com valor menor que 0)"),
            @ApiResponse(responseCode = "400", description = "A API não compreendeu a requisição do cliente (por exemplo: um JSON inválido)")
    })
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

    @Operation(summary = "deletar transações", description = "essa rota, remove todas as transações")
    @ApiResponse(responseCode = "200", description = "Todas as informações foram apagadas com sucesso")
    @DeleteMapping("/transacao")
    ResponseEntity<Void> clearTransactions() {
        logger.log(Level.INFO, "Deletando transações...");

        this.transacaoRespository.clear();

        logger.log(Level.INFO, "Deletado com sucesso!");

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "calcular estatisticas", description = "essa rota, retorna uma estatisca de suas transações na data estimada")
    @ApiResponse(responseCode = "200", description = "Um JSON com os campos count, sum, avg, min e max todos preenchidos com seus respectivos valores")
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

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

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

        stopWatch.stop();

        logger.log(Level.INFO, "OK! foram gastos " + stopWatch.getTotalTimeMillis() + " ms para calcular as estastisticas");

        return ResponseEntity.status(HttpStatus.OK).body(estatisticaResDto);

    }

    @Operation(summary = "criar uma data no padrão ISO")
    @ApiResponse(responseCode = "200")
    @GetMapping("generate")
    String generateDateActual() {
        return OffsetDateTime.now().toString();
    }
}
