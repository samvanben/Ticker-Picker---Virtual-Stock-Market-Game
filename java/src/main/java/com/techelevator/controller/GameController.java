package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.GameUserDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.GameUser;
import com.techelevator.model.StockApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RestController
@CrossOrigin
//@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/games")
public class GameController {
    private UserDao userDao;
    private GameDao gameDao;
    private GameUserDao gameUserDao;
    private StockDao stockDao;

    public GameController(UserDao userDao, GameDao gameDao, StockDao stockDao, GameUserDao gameUserDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.stockDao = stockDao;
        this.gameUserDao = gameUserDao;
    }

    @RequestMapping( method = RequestMethod.GET)
    public List<Game> getAllGamesAsAdmin(){
        return gameDao.getAllGames();
    }

    @RequestMapping(path = "/my-games", method = RequestMethod.GET)
    public List<Game> getCurrentLoggedInUserGameList(Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Game> returnList = gameDao.getGamesByUserId(userId);
        return returnList;
    }

    @RequestMapping(path = "/{gameId}/players", method = RequestMethod.GET)
    public List<GameUser> getCertainGamePlayers(@PathVariable int gameId){
        return gameUserDao.getPlayerByGameId(gameId);
    }

    @RequestMapping(path = "/{gameId}/add-player", method = RequestMethod.GET)
    public Map<String, Integer> listPlayersForAdding(@PathVariable int gameId){
        Map<String, Integer> players = gameDao.getListOfPlayersAvailableToBeAdd(gameId);
        return players;
    }

    @RequestMapping(path = "/{gameId}/leaderboard", method = RequestMethod.GET)
    public Map<String, BigDecimal> LeaderboardOfAGame(@PathVariable int gameId){
        Map<String, BigDecimal> orderedMap = new LinkedHashMap<>();
        return gameDao.orderGameMembersByTotalBalanceByGameId(gameId);
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
    @RequestMapping(path = "/add-player", method = RequestMethod.POST)
    public boolean addUserToGame(@Valid @RequestBody GameUser gameUser) {

        // get players of current game, make sure the user to be added is not already in the game
        boolean exist = false;
        List<GameUser> currentGamePlayer = gameUserDao.getPlayerByGameId(gameUser.getGameId());
        for(GameUser player: currentGamePlayer){
            if(player.getUserId()==gameUser.getUserId()){
                exist = true;
            }
        }
        if(!exist){
            return gameUserDao.createGameUser(gameUser)==0 ? false : true;
        } else {
            throw new DaoException("Player already in this game!");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public boolean create(@Valid @RequestBody Game game) {
        return gameDao.createGame(game)==0 ? false : true;
    }

    @RequestMapping(path = "/{gameId}", method = RequestMethod.PUT)
    public boolean update(@Valid @RequestBody Game gameToUpdate, @PathVariable int gameId) {
        gameToUpdate.setGameId(gameId);
        try {
            gameDao.updateGame(gameToUpdate, gameId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Not Found");
        }
        return true;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{gameId}", method = RequestMethod.DELETE)
    public boolean delete(@Valid @PathVariable int gameId) {
        return gameDao.deleteGame(gameId)==0 ? false : true;
    }
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(path = "/{gameId}/buy/{symbol}", method = RequestMethod.PUT)
//    public boolean buyStocksBalanceChange(@Valid @PathVariable int gameId, Principal user, String symbol) {
//        String username = user.getName();
//        int userId = userDao.findIdByUsername(username);
//        BigDecimal amount = stockDao.getStockPriceBySymbol(symbol);
//        return gameDao.subtractFromGameUserAvailableBalance(amount, gameId, userId);
//    }
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(path = "/{gameId}/{userId}/sell/{symbol}", method = RequestMethod.PUT)
//    public boolean sellStocksBalanceChange(@Valid @PathVariable int gameId, int userId, String symbol) {
//        BigDecimal amount = stockDao.getStockPriceBySymbol(symbol);
//        return gameDao.addToGameUserAvailableBalance(amount, gameId, userId);
//    }

    @RequestMapping(path = "{gameId}/buy/{symbol}/{numbers}", method = RequestMethod.PUT)
    public BigDecimal buyStock(@PathVariable String symbol, @PathVariable int gameId, @PathVariable int numbers, Principal user, @RequestBody StockApiDTO stock) {
        // get current user's user id
        String username = user.getName();

        int userId = userDao.findIdByUsername(username);

//        BigDecimal stockPrice = stockDao.getStockPriceBySymbol(symbol);
        // get transaction amount and commission to check if available balance can cover the transaction
        BigDecimal transactionAmount = BigDecimal.valueOf(stock.getClose()).multiply(BigDecimal.valueOf(numbers));
        BigDecimal commission = BigDecimal.valueOf(19.95);
        BigDecimal totalAmount = transactionAmount.add(commission);
        // if available balance can cover the transaction, proceed to buy
        if(totalAmount.compareTo(gameDao.getAvailableBalanceByUserGame(userId, gameId)) <= 0){
            gameDao.subtractFromGameUserAvailableBalance(totalAmount, gameId, userId);
        } else {
            new IOException("Insufficient Fund");
        }
        return gameDao.getAvailableBalanceByUserGame(userId, gameId);
    }

    @RequestMapping(path = "{gameId}/sell/{symbol}/{numbers}", method = RequestMethod.PUT)
    public BigDecimal sellStock(@PathVariable String symbol, @PathVariable int gameId, @PathVariable int numbers, Principal user, @RequestBody StockApiDTO stock) {
        // get current user's user id
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);

//        BigDecimal stockPrice = stockDao.getStockPriceBySymbol(symbol);
        // get transaction amount and commission, proceed to sell
        BigDecimal transactionAmount = BigDecimal.valueOf(stock.getClose()).multiply(BigDecimal.valueOf(numbers));
        BigDecimal commission = BigDecimal.valueOf(19.95);
        BigDecimal totalAmount = transactionAmount.add(commission);
        gameDao.addToGameUserAvailableBalance(totalAmount, gameId, userId);
        return gameDao.getAvailableBalanceByUserGame(userId, gameId);
    }
}
