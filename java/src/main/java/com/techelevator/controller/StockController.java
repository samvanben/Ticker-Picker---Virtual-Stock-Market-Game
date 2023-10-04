package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Stock;
import com.techelevator.model.StockListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.techelevator.api.StocksApi;
import com.techelevator.model.StockApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/stocks")
public class StockController {
    private StockDao stockDao;
    private UserDao userDao;
    private GameDao gameDao;

    public StockController(StockDao stockDao, UserDao userDao, GameDao gameDao) {
        this.stockDao = stockDao;
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    @RequestMapping(path = "/stocks", method = RequestMethod.GET)
    public List<Stock> listAllStocks(){
        return stockDao.getAllStocks();
    }

    @RequestMapping(path = "/{gameId}/list-my-stocks", method = RequestMethod.GET)
    public List<Stock> getCurrentLoggedInUserGameStocksByGameId(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Stock> returnList = stockDao.getStocksByOneUserOfGame(userId, gameId);
        return returnList;
    }

    @RequestMapping(path = "/{gameId}/available-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameAvailableBalance(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal availableBalance = gameDao.getGameUserAvailableBalance(gameId, userId);
        return availableBalance;
    }

    @RequestMapping(path = "/{gameId}/total-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameTotalBalance(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal totalBalance = gameDao.getGameUserTotalBalance(gameId, userId);
        return totalBalance;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public boolean createStock(@Valid @RequestBody Stock stock) {
        stockDao.createStock(stock);
        return true;
    }

    private StocksApi stocksApi = new StocksApi();

    @GetMapping(path = "/{symbol}")
    public StockApiDTO getStocks(@PathVariable String symbol){
        return stocksApi.stockDataSymbol(symbol);
    }

    @GetMapping(path = "/stock_list")
    public StockListDTO getStockList(){
        return stocksApi.stockList();
    }

    @GetMapping(path = "/trendy_stock")
    public StockApiDTO getStocks(){
        return stocksApi.stockData();
    }

}
