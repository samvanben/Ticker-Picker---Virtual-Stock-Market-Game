package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockListDTO {

    @JsonProperty("TSLA")
    private StockApiDTO tsla;
    @JsonProperty("AAPL")
    private StockApiDTO aapl;

    @JsonProperty("GOOGL")
    private StockApiDTO googl;
    @JsonProperty("JPM")
    private StockApiDTO jpm;

    @JsonProperty("MSFT")
    private StockApiDTO msft;

    public StockApiDTO getTSLA() {
        return tsla;
    }

    public void setTSLA(StockApiDTO TSLA) {
        this.tsla = TSLA;
    }

    public StockApiDTO getAAPL() {
        return aapl;
    }

    public void setAAPL(StockApiDTO AAPL) {
        this.aapl = AAPL;
    }

    public StockApiDTO getGoogl() {
        return googl;
    }

    public void setGoogl(StockApiDTO googl) {
        this.googl = googl;
    }

    public StockApiDTO getJpm() {
        return jpm;
    }

    public void setJpm(StockApiDTO jpm) {
        this.jpm = jpm;
    }

    public StockApiDTO getMsft() {
        return msft;
    }

    public void setMsft(StockApiDTO msft) {
        this.msft = msft;
    }
}

//{
//        "IBM": {
//        "symbol": "IBM",
//        "exchange": "NYSE",
//        "mic_code": "XNYS",
//        "currency": "USD",
//        "datetime": "2023-09-27",
//        "timestamp": 1695915680,
//        "close": "143.17"
//        },
//        "AAPL": {
//        "symbol": "AAPL",
//        "exchange": "NASDAQ",
//        "mic_code": "XNGS",
//        "currency": "USD",
//        "datetime": "2023-09-27",
//        "timestamp": 1695915693,
//        "close": "170.43"
//        }