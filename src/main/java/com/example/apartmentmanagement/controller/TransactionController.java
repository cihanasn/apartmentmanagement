package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.model.Transaction;
import com.example.apartmentmanagement.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Tüm transactionları listeleyen endpoint
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // ID'ye göre belirli bir transaction getiren endpoint
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> new ResponseEntity<>(transaction, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Yeni bir transaction kaydeden endpoint
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    // Bir transaction güncelleyen endpoint
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        return transactionService.getTransactionById(id)
                .map(existingTransaction -> {
                    transaction.setId(id);  // Güncellenecek nesnenin ID'sini set ediyoruz
                    Transaction updatedTransaction = transactionService.saveTransaction(transaction);
                    return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ID'ye göre bir transaction silen endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (transactionService.getTransactionById(id).isPresent()) {
            transactionService.deleteTransactionById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Belirli bir türdeki (gelir/gider) transactionları listeleyen endpoint
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable String type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}

