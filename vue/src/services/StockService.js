import axios from 'axios';

export default {

  getAllStocks() {
    return axios.get('/allStocks')
  },

  getSearchStock(symbol) {
    return axios.get(`/api/stocks/${symbol}`)
  },

}