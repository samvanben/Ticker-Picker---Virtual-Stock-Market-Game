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
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/api/transactions")
public class TransactionController {
//    private UserDao userDao;
//    private TransactionDao transactionDao;
//
//    public TransactionController(TransactionDao transactionDao, UserDao userDao) {
//        this.transactionDao = transactionDao;
//        this.userDao = userDao;
//    }
//
//    @RequestMapping(path = "/admin", method = RequestMethod.GET)
//    public List<Transaction> listAllTransactionAsAdmin(){
//        return transactionDao.getAllTransactionsAsAdmin();
//    }
//
//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public List<Transaction> listAllTransactionsOfCurrentUser(Principal user){
//        String username = user.getName();
//        int userId = userDao.findIdByUsername(username);
//        return transactionDao.getTransactionsByUserId(userId);
//    }
//
//    @RequestMapping(path = "/{gameId}", method = RequestMethod.GET)
//    public List<Transaction> listTransactionsOfCurrentUserByGameId(@PathVariable int gameId, Principal user){
//        String username = user.getName();
//        int userId = userDao.findIdByUsername(username);
//        return transactionDao.getTransactionsByUserGame(userId, gameId);
//    }
//
//    @RequestMapping(path = "/{gameId}/{stockId}", method = RequestMethod.GET)
//    public List<Transaction> listTransactionsOfCurrentUserByGameIdAndStockId(@PathVariable int gameId, @PathVariable int stockId, Principal user){
//        String username = user.getName();
//        int userId = userDao.findIdByUsername(username);
//        return transactionDao.getTransactionsByUserGameStock(userId, stockId, gameId);
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path = "", method = RequestMethod.POST)
//    public boolean createNewTransaction(@Valid @RequestBody Transaction transaction) {
//        transactionDao.createTransaction(transaction);
//        return true;
//    }
//
//    @RequestMapping(path = "/{transactionId}", method = RequestMethod.PUT)
//    public boolean updateTransaction(@Valid @RequestBody Transaction transactionToUpdate, @PathVariable int transactionId) {
//        transactionToUpdate.setTransactionId(transactionId);
//        try {
//            return transactionDao.updateTransaction(transactionToUpdate, transactionId);
//        } catch (DaoException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found");
//        }
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(path = "/{transactionId}", method = RequestMethod.DELETE)
//    public boolean deleteTransaction(@Valid @PathVariable int transactionId) {
//        return transactionDao.deleteTransaction(transactionId);
//    }
}
