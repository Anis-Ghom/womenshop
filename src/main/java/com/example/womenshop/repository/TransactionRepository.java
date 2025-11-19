package com.example.womenshop.repository;

import com.example.womenshop.model.Transaction;
import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction t);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByProductId(int productId);
}
