package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDao implements TransactionDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> getAllTransactionsAsAdmin() {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByUserGameStock(int userId,  int stockId, int gameId) {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=? AND stock_id=? AND game_id=?; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, userId, stockId, gameId);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByUserGame(int userId, int gameId) {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=? AND game_id=?; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, userId, gameId);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=?; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByUsername(String username) {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=(SELECT user_id FROM users WHERE username=?);";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, username);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public List<Transaction> getTransactionsByUsernameGame(String username, int gameId) {
        List<Transaction> results = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=(SELECT user_id FROM users WHERE username=?) AND game_id=?; ";
        try{
            SqlRowSet SqlRowSet = jdbcTemplate.queryForRowSet(sql, username, gameId);
            while (SqlRowSet.next()){
                results.add(mapRowToTransaction(SqlRowSet));
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return results;
    }

    @Override
    public int createTransaction(Transaction transactionToCreate) {
        int transactionId = 0;
        String sql = "INSERT INTO transaction (transaction_type, price, number_of_shares, user_id, stock_id, game_id) VALUES (?, ?, ?, ?, ?, ?) returning ; ";
        try {
            transactionId = jdbcTemplate.queryForObject(sql, int.class, transactionToCreate.getTransactionType(), transactionToCreate.getPrice(), transactionToCreate.getNumberOfShares(),
                    transactionToCreate.getUserId(), transactionToCreate.getStockId(), transactionToCreate.getGameId());
            transactionToCreate.setTransactionId(transactionId);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return transactionId;
    }

    @Override
    public boolean updateTransaction(Transaction transactionToUpdate, int transactionId) {
        boolean success = false;
        String sql = "UPDATE transaction SET transaction_type=?, price=?, number_of_shares=?, user_id=?, stock_id=?, game_id=? WHERE transaction_id=?;";
        try {
            int numberOfRows = jdbcTemplate.update(sql, transactionToUpdate.getTransactionType(), transactionToUpdate.getPrice(), transactionToUpdate.getNumberOfShares(),
                    transactionToUpdate.getUserId(), transactionToUpdate.getStockId(), transactionToUpdate.getGameId(), transactionId);
            if (numberOfRows == 0 ){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            success = true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
        return success;
    }

    @Override
    public boolean deleteTransaction(int transactionId) {
        boolean success = false;
        String sql = "DELETE FROM transaction WHERE transaction_id = ?";
        try{
            int numberOfRows = jdbcTemplate.update(sql, transactionId);
            if(numberOfRows==0){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            success = true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return success;
    }

    private Transaction mapRowToTransaction(SqlRowSet results) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(results.getInt("transaction_id"));
        transaction.setTransactionType(results.getString("transaction_type"));
        transaction.setPrice(results.getBigDecimal("price"));
        transaction.setNumberOfShares(results.getInt("number_of_shares"));
        transaction.setUserId(results.getInt("user_id"));
        transaction.setStockId(results.getInt("stock_id"));
        transaction.setGameId(results.getInt("game_id"));
        return transaction;
    }
}
