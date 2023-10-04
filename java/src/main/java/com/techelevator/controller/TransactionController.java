package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.dao.StockDao;
import com.techelevator.dao.TransactionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Stock;
import com.techelevator.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/transactions")
@CrossOrigin
public class TransactionController {
    private UserDao userDao;
    private GameDao gameDao;
    private TransactionDao transactionDao;

    public TransactionController(TransactionDao transactionDao, UserDao userDao) {
        this.transactionDao = transactionDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public List<Transaction> listAllTransactionAsAdmin(){
        return transactionDao.getAllTransactionsAsAdmin();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transaction> listAllTransactionsOfCurrentUser(Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return transactionDao.getTransactionsByUserId(userId);
    }

    @RequestMapping(path = "/game{gameId}", method = RequestMethod.GET)
    public List<Transaction> listTransactionsOfCurrentUserByGameId(@PathVariable int gameId, Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return transactionDao.getTransactionsByUserGame(userId, gameId);
    }

    @RequestMapping(path = "/game{gameId}/stock{stockId}", method = RequestMethod.GET)
    public List<Transaction> listTransactionsOfCurrentUserByGameIdAndStockId(@PathVariable int gameId, @PathVariable int stockId, Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return transactionDao.getTransactionsByUserGameStock(userId, stockId, gameId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{transactionId}", method = RequestMethod.DELETE)
    public boolean deleteTransaction(@Valid @PathVariable int transactionId) {
        return transactionDao.deleteTransaction(transactionId);
    }

    @RequestMapping(path = "/{gameId}/{symbol}", method = RequestMethod.GET)
    public int getCurrentHoldingStockQuantity(@PathVariable int gameId, @PathVariable String symbol, Principal user){
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);
        return (transactionDao.getStockQuantity(userId, gameId, symbol) == -1) ? 0 : transactionDao.getStockQuantity(userId, gameId, symbol);
    }
}
