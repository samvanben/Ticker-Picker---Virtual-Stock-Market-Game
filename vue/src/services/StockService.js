import axios from 'axios';

export default {

  getPuppies() {
    return axios.get('/allStocks')
  }

}