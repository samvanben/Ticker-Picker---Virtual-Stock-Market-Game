package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Stock;
import com.techelevator.model.gameUser;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@Controller
@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/games")
public class GameController {
    private UserDao userDao;
    private GameDao gameDao;
    private StockDao stockDao;

    public GameController(UserDao userDao, GameDao gameDao, StockDao stockDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.stockDao = stockDao;
    }

    @RequestMapping( method = RequestMethod.GET)
    public List<Game> getAllGamesAsAdmin(){

        return gameDao.getAllGames();
    }

    @RequestMapping(path = "/{gameId}", method = RequestMethod.GET)
    public Game getPlayersByGameId(@PathVariable int gameId){
        return gameDao.getGameByGameId(gameId);
    }

    @RequestMapping(path = "/list-my-stocks", method = RequestMethod.GET)
    public List<Stock> getCurrentLoggedInUserAllStocks(Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Stock> returnList = stockDao.getStocksByOneUser(userId);
        return returnList;
    }

    @RequestMapping(path = "/game{gameId}/list-my-stocks", method = RequestMethod.GET)
    public List<Stock> getCurrentLoggedInUserCertainGameStocks(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Stock> returnList = stockDao.getStocksByOneUserOfGame(userId, gameId);
        return returnList;
    }

    @RequestMapping(path = "/game{gameId}/available-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameAvailableBalance(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal availableBalance = gameDao.getGameUserAvailableBalance(gameId, userId);
        return availableBalance;
    }

    @RequestMapping(path = "/game{gameId}/total-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameTotalBalance(Principal user, @PathVariable int gameId){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal totalBalance = gameDao.getGameUserTotalBalance(gameId, userId);
        return totalBalance;
    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "", method = RequestMethod.POST)
//    public boolean create(@Valid @RequestBody Stock stock) {
//        return stockDao.createStock(stock);
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
