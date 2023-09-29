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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/game{gameId}", method = RequestMethod.POST)
    public boolean createNewTransaction(@Valid @RequestBody Transaction transaction, Principal user, @PathVariable int gameId) {
        // get current user's user id
        String username = user.getName();
        int userId = userDao.findIdByUsername(username);

        // get transaction amount and commission to check if available balance can cover the transaction
        BigDecimal transactionAmount = transaction.getPrice().multiply(BigDecimal.valueOf(transaction.getNumberOfShares()));
        BigDecimal commission = BigDecimal.valueOf(19.95);
        BigDecimal totalAmount = transactionAmount.add(commission);
        // if available balance can cover the transaction and it's buy type, transaction is created and money was subtracted from available balance
        if(transaction.getTransactionType().equals("buy")){
            if(totalAmount.compareTo(gameDao.getAvailableBalanceByUserGame(userId, gameId)) <= 0){
                gameDao.subtractFromGameUserAvailableBalance(totalAmount, gameId, userId);
                int rowAffected = transactionDao.createTransaction(transaction);
                return rowAffected == 0 ? false : true;
            } else {
                new IOException("Insufficient Fund");
            }
            // if it's a sell type, sell it and add amount to available balance
        } else if(transaction.getTransactionType().equals("sell")){
            gameDao.addToGameUserAvailableBalance(totalAmount, gameId, userId);
            int rowAffected = transactionDao.createTransaction(transaction);
            return rowAffected == 0 ? false : true;
        }
        return false;
    }

    @RequestMapping(path = "/{transactionId}", method = RequestMethod.PUT)
    public boolean updateTransaction(@Valid @RequestBody Transaction transactionToUpdate, @PathVariable int transactionId) {
        transactionToUpdate.setTransactionId(transactionId);
        try {
            return transactionDao.updateTransaction(transactionToUpdate, transactionId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{transactionId}", method = RequestMethod.DELETE)
    public boolean deleteTransaction(@Valid @PathVariable int transactionId) {
        return transactionDao.deleteTransaction(transactionId);
    }
}
