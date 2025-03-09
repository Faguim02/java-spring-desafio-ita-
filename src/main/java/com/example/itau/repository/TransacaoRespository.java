package com.example.itau.repository;

import com.example.itau.model.TransacaoModel;

import java.util.List;

public class TransacaoRespository {
    private List<TransacaoModel> transacaoModels;

    public void save(TransacaoModel transacaoModel) {
        transacaoModels.add(transacaoModel);
    }

    public void clear() {
        transacaoModels.clear();
    }

    public List<TransacaoModel> findAllTransactions() {
        return this.transacaoModels;
    }
}
