import axios from 'axios';

export default {
    getBalance(gameId) {
        return axios.get(`/api/stocks/${gameId}/available-balance`)
    },

    getValue(gameId) {
        return axios.get(`/api/stocks/${gameId}/total-balance`)
    },

    buyStock(gameId, symbol, numbers, stock) {
        return axios.put(`/api/games/${gameId}/buy/${symbol}/${numbers}`, stock)
    },

    sellStock(gameId, symbol, numbers, stock) {
        return axios.put(`/api/games/${gameId}/sell/${symbol}/${numbers}`, stock)
    },
}