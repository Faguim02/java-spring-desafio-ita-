package com.example.itau.repository;

import com.example.itau.model.TransacaoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
