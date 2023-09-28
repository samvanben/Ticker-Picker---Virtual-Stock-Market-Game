package com.techelevator.api;

import com.techelevator.model.Stock;
import com.techelevator.model.StockApiDTO;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
//import org.json.JSONArray;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

//import org.json.JSONObject;

@Component
public class StocksApi{
    // API base URL
    private static String API_BASE_URL = "https://api.twelvedata.com/eod";
    final static String API_KEY = "&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";

//    public Stock getStockDetail(String symbol) throws IOException, ParseException {
//        Stock stock = new Stock();
//        String url = API_BASE_URL + "?symbol=" + symbol + API_KEY;
//        URL requestURL = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
//        StringBuffer responseData = new StringBuffer();
//
//        //TODO why is it red, need to fix
//
//
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("User-Agent", "twelve_java/1.0");
//        connection.connect();
//        if (connection.getResponseCode() != 200) {
//            throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
//        }
//        Scanner scanner = new Scanner(requestURL.openStream());
//        while (scanner.hasNextLine()) {
//
//            responseData.append(scanner.nextLine());
//        }
//
//        JSONParser parser = new JSONParser(String.valueOf(responseData));
//
//        //TODO confusing part
//        JSONObject json = (JSONObject) parser.parse();
//        JSONObject meta = (JSONObject) json.get("meta");
//        JSONArray values = (JSONArray) json.get("values");
//
//        stock.setSymbol(symbol);
//        stock.setAskPrice(BigDecimal.valueOf(json.getDouble("close")));
//
//        connection.disconnect();
//
//        System.out.println(stock.getSymbol() + " " + stock.getAskPrice());
//
//        return stock;
//    }

//    "symbol": "AAPL",
//            "exchange": "NASDAQ",
//            "mic_code": "XNAS",
//            "currency": "USD",
//            "datetime": "2021-09-16",
//            "close": "148.79"

    RestTemplate restTemplate = new RestTemplate();

//    static String apiAddress = "https://api.twelvedata.com/eod?symbol=AAPL&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
    //static String apiAddress = "https://api.twelvedata.com/eod?symbol=AAPL&symbol=IBM&symbol=TSLA&symbol=AMZN&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";
    //static String apiAddress = "https://api.twelvedata.com/eod?symbol=IBM,AAPL&dp=2&apikey=b2f7d6135e3341aeb6b6c2171c137ce7";

//    public StockApiDTO stockData(){
//        return restTemplate.getForObject(apiAddress, StockApiDTO.class);
//    }

    public StockApiDTO stockData(){
        return restTemplate.getForObject(API_BASE_URL + "?symbol=TSLA" + "&dp=2&" + API_KEY, StockApiDTO.class);
    }

}
