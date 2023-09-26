package com.techelevator.dao;

import com.techelevator.model.Stock;

import java.util.List;

public interface StockDao {
    List<Stock> getAllStocks();
    Stock getStockBySymbol(String symbol);
}
