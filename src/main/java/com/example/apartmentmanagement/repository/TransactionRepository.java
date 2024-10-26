package com.example.apartmentmanagement.repository;

import com.example.apartmentmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(String type);
}

