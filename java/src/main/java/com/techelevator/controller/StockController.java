package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
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

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Stock> listAllStocks(){
        return stockDao.getAllStocks();
    }

    @RequestMapping(path = "/{stockSymbol}", method = RequestMethod.GET)
    public Stock getStockBySymbol(@PathVariable String symbol){
        return stockDao.getStockBySymbol(symbol);
    }

    @RequestMapping(path = "/list-my-stocks", method = RequestMethod.GET)
    public List<Stock> getCurrentLoggedInUserAllStocks(Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Stock> returnList = stockDao.getStocksByOneUser(userId);
        return returnList;
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

//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "", method = RequestMethod.POST)
//    public boolean create(@Valid @RequestBody Stock stock) {
//        gameDao.createGame()
//        return true;
//    }

    @RequestMapping(path = "/{stockSymbol}", method = RequestMethod.PUT)
    public boolean update(@Valid @RequestBody Stock stockToUpdate, @PathVariable int stockId) {
        stockToUpdate.setStockId(stockId);
        try {
            return stockDao.updateStock(stockToUpdate, stockId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Not Found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{stockSymbol}", method = RequestMethod.DELETE)
    public boolean delete(@Valid @PathVariable int stockId) {
        return stockDao.deleteStock(stockId);
    }
}
