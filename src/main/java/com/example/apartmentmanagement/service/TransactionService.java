package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.model.Transaction;
import com.example.apartmentmanagement.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Yeni bir transaction kaydetme veya var olanı güncelleme
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Tüm transactionları listeleme
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // ID'ye göre belirli bir transaction bulma
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // Belirli bir türdeki (gelir/gider) transactionları listeleme
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepository.findByType(type);
    }

    // ID'ye göre belirli bir transaction'ı silme
    public void deleteTransactionById(Long id) {
        transactionRepository.deleteById(id);
    }
}

