package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.model.Transaction;
import com.example.apartmentmanagement.model.User;
import com.example.apartmentmanagement.request.TransactionRequest;
import com.example.apartmentmanagement.service.TransactionService;
import com.example.apartmentmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;  // UserService eklendi

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
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
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Optional<User> optionalUser = userService.getUserById(transactionRequest.getUserId());
        if (optionalUser.isPresent()) {
            Transaction transaction = new Transaction();
            transaction.setUser(optionalUser.get());  // Kullanıcıyı ayarlıyoruz
            transaction.setDescription(transactionRequest.getDescription());
            transaction.setAmount(transactionRequest.getAmount());
            transaction.setDate(transactionRequest.getDate());
            transaction.setType(transactionRequest.getType());

            Transaction savedTransaction = transactionService.saveTransaction(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Kullanıcı bulunamadı
        }
    }

    // Bir transaction güncelleyen endpoint
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody TransactionRequest transactionRequest) {
        return transactionService.getTransactionById(id)
                .map(existingTransaction -> {
                    Optional<User> optionalUser = userService.getUserById(transactionRequest.getUserId());
                    if (optionalUser.isPresent()) {
                        existingTransaction.setUser(optionalUser.get());  // Kullanıcıyı ayarlıyoruz
                        existingTransaction.setDescription(transactionRequest.getDescription());
                        existingTransaction.setAmount(transactionRequest.getAmount());
                        existingTransaction.setDate(transactionRequest.getDate());
                        existingTransaction.setType(transactionRequest.getType());

                        Transaction updatedTransaction = transactionService.saveTransaction(existingTransaction);
                        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<Transaction>(HttpStatus.NOT_FOUND);  // Kullanıcı bulunamadı
                    }
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

