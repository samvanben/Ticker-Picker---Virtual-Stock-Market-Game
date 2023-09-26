package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcStockDao implements StockDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcStockDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stock";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()) {
                Stock stock = mapRowToStock(results);
                stocks.add(stock);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return stocks;
    }

    @Override
    public Stock getStockBySymbol(String symbol) {
        Stock stock = new Stock();
        String sql = "SELECT * FROM stock WHERE symbol = ?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, symbol.toUpperCase());
            if(results.next()) {
                stock = mapRowToStock(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException( "cannot connect to server or database", e);
        }
        return stock;
    }

    @Override
    public Stock getStockByStockId(int stockId) {
        return null;
    }

    @Override
    public List<Stock> getStocksByOneUser(int userId) {
        return null;
    }

    @Override
    public List<Stock> getStocksByAllUsersOfGame(int gameId) {
        return null;
    }

    @Override
    public List<Stock> getStocksByOneUserOfGame(int userId, int gameId) {
        return null;
    }

    @Override
    public int createStock(String symbol, String company_name) {
        return 0;
    }

    @Override
    public boolean updateStock(Stock updatedStock, int stockId) {
        return false;
    }

    @Override
    public boolean deleteStock(int stockId) {
        return false;
    }

    private Stock mapRowToStock(SqlRowSet results) {
        Stock stock = new Stock();
        stock.setStockId(results.getInt("stock_id"));
        stock.setSymbol(results.getString("symbol"));
        return stock;
    }
}
