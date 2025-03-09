package com.example.itau.controller;

import com.example.itau.repository.TransacaoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransacaoController {

    @Autowired
    TransacaoRespository transacaoRespository;

}
