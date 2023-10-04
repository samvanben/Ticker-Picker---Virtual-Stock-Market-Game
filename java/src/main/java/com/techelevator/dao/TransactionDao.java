package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionDao {
    List<Transaction> getAllTransactionsAsAdmin();
    List<Transaction> getTransactionsByUserGameStock(int userId,  int stockId, int gameId);
    List<Transaction> getTransactionsByUserGame(int userId, int gameId);
    List<Transaction> getTransactionsByUserId(int userId);
    List<Transaction> getTransactionsByUsername(String username);
    List<Transaction> getTransactionsByUsernameGame(String username, int gameId);

    // change 4 below four methods
    boolean createTransactionForStock(int quantity,  int userId, int gameId, String symbol);
    boolean updateTransactionForStock(int quantity,  int userId, int gameId, String symbol);
    int getStockQuantity(int userId, int gameId, String symbol);
    Map<String, Integer> listActiveStocks(int userId, int gameId);


//    int createTransaction(Transaction transactionToCreate);
//    boolean updateTransaction(Transaction transactionToUpdate, int transactionId);
    boolean deleteTransaction(int transactionId);
}
