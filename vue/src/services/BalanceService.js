import axios from 'axios';

export default {
    getBalance(gameId) {
        return axios.get(`/api/stocks/${gameId}/available-balance`)
    },

    getValue(gameId) {
        return axios.get(`/api/stocks/${gameId}/total-balance`)
    },

    // buyStock()
}