package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;


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
    private TransactionDao transactionDao;

    public GameController(UserDao userDao, GameDao gameDao, StockDao stockDao, GameUserDao gameUserDao, TransactionDao transactionDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.stockDao = stockDao;
        this.gameUserDao = gameUserDao;
        this.transactionDao = transactionDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Game> getAllGamesAsAdmin() {
        return gameDao.getAllGames();
    }

    @RequestMapping(path = "/my-games", method = RequestMethod.GET)
    public List<Game> getCurrentLoggedInUserGameList(Principal user) {
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Game> returnList = gameDao.getGamesByUserId(userId);
        return returnList;
    }

    @RequestMapping(path = "/my-games/active", method = RequestMethod.GET)
    public List<Game> getUserActiveGameList(Principal user) {
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Game> returnList = gameDao.getActiveGamesByUserId(userId);
        return returnList;
    }

    @RequestMapping(path = "/my-games/ended", method = RequestMethod.GET)
    public List<Game> getUserEndedGameList(Principal user) {
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Game> returnList = gameDao.getEndedGamesByUserId(userId);
        return returnList;
    }

    @RequestMapping(path = "/{gameId}/players", method = RequestMethod.GET)
    public List<GameUser> getCertainGamePlayers(@PathVariable int gameId) {
        return gameUserDao.getPlayerByGameId(gameId);
    }

    @RequestMapping(path = "/{gameId}/add-player", method = RequestMethod.GET)
    public List<User> listPlayersForAdding(@PathVariable int gameId) {
        List <User> users = gameDao.getListOfPlayersAvailableToBeAdd(gameId);
        return users;
    }

    @RequestMapping(path = "/{gameId}/leaderboard", method = RequestMethod.GET)
    public Map<String, BigDecimal> LeaderboardOfAGame(@PathVariable int gameId) {
        Map<String, BigDecimal> orderedMap = new LinkedHashMap<>();
        return gameDao.orderGameMembersByTotalBalanceByGameId(gameId);
    }

    @RequestMapping(path = "/{gameId}/available-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameAvailableBalance(Principal user, @PathVariable int gameId) {
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal availableBalance = gameDao.getGameUserAvailableBalance(gameId, userId);
        return availableBalance;
    }

    @RequestMapping(path = "/{gameId}/total-balance", method = RequestMethod.GET)
    public BigDecimal getCurrentLoggedInUserCertainGameTotalBalance(Principal user, @PathVariable int gameId) {
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
        for (GameUser player : currentGamePlayer) {
            if (player.getUserId() == gameUser.getUserId()) {
                exist = true;
            }
        }
        if (!exist) {
            return gameUserDao.createGameUser(gameUser) == 0 ? false : true;
        } else {
            throw new DaoException("Player already in this game!");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public int createGame(@Valid @RequestBody Game game) {
        return gameDao.createGame(game);
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
        return gameDao.deleteGame(gameId) == 0 ? false : true;
    }

    @RequestMapping(path = "{gameId}/buy/{symbol}/{numbers}", method = RequestMethod.PUT)
    public boolean buyStock(@PathVariable String symbol, @PathVariable int gameId, @PathVariable int numbers, Principal user) {
        // get current user's user id
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);

        // call the api to get the price of stock
        RestTemplate restTemplate = new RestTemplate();
        String API_BASE_URL = "https://api.twelvedata.com/eod";
        String API_KEY = "&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
        StockApiDTO stock = restTemplate.getForObject(API_BASE_URL + "?symbol=" + symbol.toUpperCase() + "&dp=2&" + API_KEY, StockApiDTO.class);

        // get price of the stock
        BigDecimal stockPrice = BigDecimal.valueOf(stock.getClose());
        // get transaction amount and commission to check if available balance can cover the transaction
        BigDecimal transactionAmount = stockPrice.multiply(BigDecimal.valueOf(numbers));
        // optional add commission to the transaction
//        BigDecimal commission = BigDecimal.valueOf(19.95);
//        BigDecimal totalAmount = transactionAmount.add(commission);
        // if available balance can cover the transaction, proceed to buy
        if (transactionAmount.compareTo(gameDao.getAvailableBalanceByUserGame(userId, gameId)) <= 0) {
            // implement logic of shares of stocks holding
            if (transactionDao.getStockQuantity(userId, gameId, symbol) == -1) {
                transactionDao.createTransactionForStock(numbers, userId, gameId, symbol);
            } else {
                int numbersOfSharesHolding = transactionDao.getStockQuantity(userId, gameId, symbol) + numbers;
                transactionDao.updateTransactionForStock(numbersOfSharesHolding, userId, gameId, symbol);
            }
            return gameDao.subtractFromGameUserAvailableBalance(transactionAmount, gameId, userId);
        } else {
            new IOException("Insufficient Fund");
        }
        return false;
    }

    @RequestMapping(path = "{gameId}/sell/{symbol}/{numbers}", method = RequestMethod.PUT)
    public boolean sellStock(@PathVariable String symbol, @PathVariable int gameId, @PathVariable int numbers, Principal user) {
        // get current user's user id
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);

        // call the api to get the price of stock
        RestTemplate restTemplate = new RestTemplate();
        String API_BASE_URL = "https://api.twelvedata.com/eod";
        String API_KEY = "&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
        StockApiDTO stock = restTemplate.getForObject(API_BASE_URL + "?symbol=" + symbol.toUpperCase() + "&dp=2&" + API_KEY, StockApiDTO.class);

        if (numbers <= transactionDao.getStockQuantity(userId, gameId, symbol)) {
            BigDecimal stockPrice = BigDecimal.valueOf(stock.getClose());
            // get transaction amount and commission, proceed to sell
            BigDecimal transactionAmount = stockPrice.multiply(BigDecimal.valueOf(numbers));
//            BigDecimal commission = BigDecimal.valueOf(19.95);
//            BigDecimal totalAmount = transactionAmount.subtract(commission);
            gameDao.addToGameUserAvailableBalance(transactionAmount, gameId, userId);

            // implement logic of shares of stocks holding
            int numbersOfSharesHolding = transactionDao.getStockQuantity(userId, gameId, symbol) - numbers;
            return transactionDao.updateTransactionForStock(numbersOfSharesHolding, userId, gameId, symbol);
        } else {
            new IOException("You don't have sufficient shares of stocks to sell");
        }
        return false;
    }

    private boolean sellStock(@PathVariable String symbol, @PathVariable int gameId, @PathVariable int numbers, int userId) {
        // call the api to get the price of stock
        RestTemplate restTemplate = new RestTemplate();
        String API_BASE_URL = "https://api.twelvedata.com/eod";
        String API_KEY = "&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
        StockApiDTO stock = restTemplate.getForObject(API_BASE_URL + "?symbol=" + symbol.toUpperCase() + "&dp=2&" + API_KEY, StockApiDTO.class);

        if (numbers <= transactionDao.getStockQuantity(userId, gameId, symbol)) {
            BigDecimal stockPrice = BigDecimal.valueOf(stock.getClose());
            // get transaction amount and commission, proceed to sell
            BigDecimal transactionAmount = stockPrice.multiply(BigDecimal.valueOf(numbers));
//            BigDecimal commission = BigDecimal.valueOf(19.95);
//            BigDecimal totalAmount = transactionAmount.subtract(commission);
            gameDao.addToGameUserAvailableBalance(transactionAmount, gameId, userId);

            // implement logic of shares of stocks holding
            int numbersOfSharesHolding = transactionDao.getStockQuantity(userId, gameId, symbol) - numbers;
            return transactionDao.updateTransactionForStock(numbersOfSharesHolding, userId, gameId, symbol);
        } else {
            new IOException("You don't have sufficient shares of stocks to sell");
        }
        return false;
    }

    @RequestMapping(path = "/{gameId}/current-holding", method = RequestMethod.GET)
    public Map<String, Integer> getListOfCurrentHoldingStocks(@PathVariable int gameId, Principal user) {
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return transactionDao.listActiveStocks(userId, gameId);
    }

    @RequestMapping(path = "/{gameId}/end-game", method = RequestMethod.GET)
    public boolean endGame(@PathVariable int gameId) {
        // get list of game player's userId
        List<GameUser> players = gameUserDao.getPlayerByGameId(gameId);
        List<Integer> listOfUserId = new ArrayList<>();
        for (GameUser gameUser : players) {
            listOfUserId.add(gameUser.getUserId());
        }
        // loop through userId, sell all holding stocks
        for (Integer currentUserId : listOfUserId) {
            Map<String, Integer> holdings = transactionDao.listActiveStocks(currentUserId, gameId);
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {

                // call the api to get the price of stock, in this case key is the stock symbol
                RestTemplate restTemplate = new RestTemplate();
                String API_BASE_URL = "https://api.twelvedata.com/eod";
                String API_KEY = "&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
                StockApiDTO stock = restTemplate.getForObject(API_BASE_URL + "?symbol=" + entry.getKey().toUpperCase() + "&dp=2&" + API_KEY, StockApiDTO.class);

                sellStock(entry.getKey(), gameId, entry.getValue(), currentUserId);
            }
        }
        // check if there are any holding shares for all users, return true if no, false if yes
        boolean done = true;
        for (Integer currentUserId : listOfUserId) {
            Map<String, Integer> holdings = transactionDao.listActiveStocks(currentUserId, gameId);
            if (!holdings.isEmpty()) {
                done = false;
            }
        }
        gameDao.setGameStatusToFalse(gameId);
        return done;
    }

    @RequestMapping(path = "/{gameId}/shares", method = RequestMethod.GET)
    public List<Transaction> getListOfTransactionStocks(@PathVariable int gameId, Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return transactionDao.listAllStocks(userId, gameId);
    }
}
