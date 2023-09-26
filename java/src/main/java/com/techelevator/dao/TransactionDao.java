package com.techelevator.dao;

import com.techelevator.model.Stock;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {
    BigDecimal getAvailableBalanceByUserAndGame(int userId, int gameId);
    List<Stock> getStocksByUsersAndGame(int userId, int gameId);
    List<Stock> getTransactionsByUserGameStock(int userId, int gameId, int stockId);

}
