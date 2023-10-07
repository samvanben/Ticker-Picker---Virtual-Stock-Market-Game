package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
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

    // change 3 below all
    @Override
    public boolean createTransactionForStock(int quantity,  int userId, int gameId, String symbol){
        int transactionId = 0;
        String createTransactionSql = "INSERT INTO transaction (user_id, stock_symbol, game_id, quantity) VALUES (?, ?, ?, ?) returning transaction_id; ";

        try{
            transactionId = jdbcTemplate.queryForObject(createTransactionSql, int.class, userId, symbol, gameId, quantity);
            return transactionId == 0 ? false : true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
    }

    @Override
    public boolean updateTransactionForStock(int quantity,  int userId, int gameId, String symbol) {
        String updateTransactionQuantitySql = "UPDATE transaction SET quantity = ? WHERE user_id=? AND game_id=? AND stock_symbol=?; ";
        try {
            int numberOfRows = jdbcTemplate.update(updateTransactionQuantitySql, quantity, userId, gameId, symbol);
            if (numberOfRows == 0 ){
                throw new DaoException("Zero rows affected, expected at least one");
            }
            return numberOfRows==0 ? false : true;
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        } catch (DataIntegrityViolationException e){
            throw new DaoException("data integrity violation", e);
        }
    }

    @Override
    public int getStockQuantity(int userId, int gameId, String symbol) {
        int quantity = -1;
        String getQuantitySql = "SELECT quantity FROM transaction WHERE user_id=? AND game_id=? AND stock_symbol=?; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(getQuantitySql, userId, gameId, symbol);
            if (results.next()){
                quantity = results.getInt("quantity");
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return quantity;
    }

    @Override
    public Map<String, Integer> listActiveStocks(int userId, int gameId) {
        Map<String, Integer> activeStocks = new HashMap<>();
        String sql = "SELECT * FROM transaction WHERE user_id=? AND game_id=? AND quantity>0; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, gameId);
            while (results.next()){
                String symbol = results.getString("stock_symbol");
                int share = results.getInt("quantity");
                activeStocks.put(symbol, share);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return activeStocks;
    }

    @Override
    public List<Transaction> listAllStocks(int userId, int gameId) {
        List<Transaction> stocks = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE user_id=? AND game_id=?; ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, gameId);
            while (results.next()){
                Transaction transaction = new Transaction();
                String symbol = results.getString("stock_symbol");
                int share = results.getInt("quantity");

                transaction.setSymbol(symbol);
                transaction.setQuantity(share);
                transaction.setGameId(results.getInt("game_id"));
                transaction.setUserId(results.getInt("user_id"));
                stocks.add(transaction);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return stocks;
    }

    private Transaction mapRowToTransaction(SqlRowSet results) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(results.getInt("transaction_id"));
        transaction.setUserId(results.getInt("user_id"));
        transaction.setSymbol(results.getString("stock_symbol"));
        transaction.setGameId(results.getInt("game_id"));
        transaction.setQuantity(results.getInt("quantity"));
        return transaction;
    }
}
