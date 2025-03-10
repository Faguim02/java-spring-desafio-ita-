package com.example.itau.controller;

import com.example.itau.model.TransacaoModel;
import com.example.itau.repository.TransacaoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

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
}
