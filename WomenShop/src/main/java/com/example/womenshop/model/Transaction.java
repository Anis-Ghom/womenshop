package com.example.womenshop.model;

import java.time.LocalDateTime;

public class Transaction {
    private int transactionsId;
    private int productsId;
    private TransactionType transactionsType;
    private int transactionsQuantity;
    private double transactionsAmount;
    private LocalDateTime transactionsDate;

    public enum TransactionType {
        PURCHASE, SALE
    }

    public Transaction(int transactionsId, int productsId, TransactionType transactionsType,
                       int transactionsQuantity, double transactionsAmount, LocalDateTime transactionsDate) {
        this.transactionsId = transactionsId;
        this.productsId = productsId;
        this.transactionsType = transactionsType;
        this.transactionsQuantity = transactionsQuantity;
        this.transactionsAmount = transactionsAmount;
        this.transactionsDate = transactionsDate;
    }

    // Getters & setters
    public int getTransactionsId() { return transactionsId; }
    public void setTransactionsId(int transactionsId) { this.transactionsId = transactionsId; }
    public int getProductsId() { return productsId; }
    public void setProductsId(int productsId) { this.productsId = productsId; }
    public TransactionType getTransactionsType() { return transactionsType; }
    public void setTransactionsType(TransactionType transactionsType) { this.transactionsType = transactionsType; }
    public int getTransactionsQuantity() { return transactionsQuantity; }
    public void setTransactionsQuantity(int transactionsQuantity) { this.transactionsQuantity = transactionsQuantity; }
    public double getTransactionsAmount() { return transactionsAmount; }
    public void setTransactionsAmount(double transactionsAmount) { this.transactionsAmount = transactionsAmount; }
    public LocalDateTime getTransactionsDate() { return transactionsDate; }
    public void setTransactionsDate(LocalDateTime transactionsDate) { this.transactionsDate = transactionsDate; }
}
