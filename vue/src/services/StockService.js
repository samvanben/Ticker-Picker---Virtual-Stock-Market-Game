import axios from 'axios';

export default {

  getAllStocks() {
    return axios.get('/allStocks')
  },

  getSearchStock(symbol) {
    return axios.get(`/api/stocks/${symbol}`)
  },

  getGameStockList() {
    return axios.get('/api/stocks/stock_list')
  },

  getTrendyStock() {
    return axios.get('/api/stocks/trendy_stock')
  }
}