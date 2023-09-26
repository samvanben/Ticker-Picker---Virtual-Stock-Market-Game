package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {
    List<Transaction> getAllTransactionsAsAdmin();
    List<Stock> getTransactionsByUserGameStock(int userId, int gameId, int stockId);
    List<Stock> getTransactionsByUserGame(int userId, int gameId);
    List<Stock> getTransactionsByUserId(int userId);
    List<Stock> getTransactionsByUsername(String username);
    List<Stock> getTransactionsByUsernameGame(String username, int gameId);

    int createTransaction(Transaction transactionToCreate);
    boolean updateTransaction(Transaction transactionToUpdate, int transactionId);
    boolean deleteTransaction(int transactionId);
}
