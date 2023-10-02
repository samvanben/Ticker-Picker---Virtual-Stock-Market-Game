package com.techelevator.dao;

import com.techelevator.model.Stock;
import com.techelevator.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface StockDao {
    List<Stock> getAllStocks();
    Stock getStockBySymbol(String symbol);
    Stock getStockByStockId(int stockId);
    List<Stock> getStocksByOneUser(int userId);
    List<Stock> getStocksByAllUsersOfGame(int gameId);
    List<Stock> getStocksByOneUserOfGame(int userId, int gameId);
    int createStock(Stock stock);
    boolean updateStock(Stock updatedStock, int stockId);
    boolean deleteStock(int stockId);
    BigDecimal getStockPriceByStockId(int stockId);
}
