package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTransactionDao implements TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Transaction> getAllTransactionsAsAdmin() {
        return null;
    }

    @Override
    public List<Stock> getTransactionsByUserGameStock(int userId, int gameId, int stockId) {
        return null;
    }

    @Override
    public List<Stock> getTransactionsByUserGame(int userId, int gameId) {
        return null;
    }

    @Override
    public List<Stock> getTransactionsByUserId(int userId) {
        return null;
    }

    @Override
    public List<Stock> getTransactionsByUsername(String username) {
        return null;
    }

    @Override
    public List<Stock> getTransactionsByUsernameGame(String username, int gameId) {
        return null;
    }

    @Override
    public int createTransaction(Transaction transactionToCreate) {
        return 0;
    }

    @Override
    public boolean updateTransaction(Transaction transactionToUpdate, int transactionId) {
        return false;
    }

    @Override
    public boolean deleteTransaction(int transactionId) {
        return false;
    }
}
