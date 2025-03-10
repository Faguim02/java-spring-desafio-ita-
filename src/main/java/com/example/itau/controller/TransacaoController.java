package com.example.itau.controller;

import com.example.itau.model.TransacaoModel;
import com.example.itau.repository.TransacaoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransacaoController {

    @Autowired
    TransacaoRespository transacaoRespository;

    @PostMapping("/transacao")
    ResponseEntity<Void> receiveTransaction(@RequestBody TransacaoModel transacaoReq) {

        this.transacaoRespository.save(transacaoReq);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
