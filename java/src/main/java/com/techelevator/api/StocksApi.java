package com.techelevator.api;

import com.techelevator.model.StockApiDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StocksApi implements StocksApiInterface {
    RestTemplate restTemplate = new RestTemplate();

    static String apiAddress = "https://api.twelvedata.com/eod?symbol=IBM&symbol=IBM&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
    //static String apiAddress = "https://api.twelvedata.com/eod?symbol=IBM,AAPL&dp=2&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";

    public StockApiDTO stockData(){
        return restTemplate.getForObject(apiAddress, StockApiDTO.class);
    }

}
