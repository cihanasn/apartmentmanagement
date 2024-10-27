package com.example.apartmentmanagement.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Kullanıcının ID'si

    @NotNull(message = "Description cannot be null")
    private String description;  // İşlem açıklaması

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;  // İşlem tutarı

    @NotNull(message = "Date cannot be null")
    private LocalDate date;  // İşlem tarihi

    @NotNull(message = "Type cannot be null")
    private String type;  // "income" veya "expense"

    // Getters ve Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

