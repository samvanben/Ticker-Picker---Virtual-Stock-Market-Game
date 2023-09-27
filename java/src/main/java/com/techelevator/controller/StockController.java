package com.techelevator.controller;

import com.techelevator.api.StocksApi;
import com.techelevator.model.StockApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @Autowired
    private StocksApi stocksApi;

    @GetMapping(path = "/stocks")
    public StockApiDTO getStocks(){
        return stocksApi.stockData();
    }


}
