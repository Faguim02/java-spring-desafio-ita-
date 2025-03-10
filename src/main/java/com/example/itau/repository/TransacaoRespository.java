package com.example.itau.repository;

import com.example.itau.model.TransacaoModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransacaoRespository {
    private final List<TransacaoModel> transacaoModels = new CopyOnWriteArrayList<>();

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
