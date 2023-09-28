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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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

    @RequestMapping(path = "/my-games", method = RequestMethod.GET)
    public List<Game> getCurrentLoggedInUserGameList(Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        List<Game> returnList = gameDao.getGamesByUserId(userId);
        return returnList;
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
}
