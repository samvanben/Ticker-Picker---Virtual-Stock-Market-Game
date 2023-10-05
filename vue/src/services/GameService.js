import axios from 'axios';

export default {

  createGame(gameBody) {
    return axios.post('/api/games', gameBody)
  },

  getAllGames() {
      return axios.get('/api/games/my-games')
  },

  getCurrentGames() {
    return axios.get('/api/games/my-games/active')
  },

  getEndedGames() {
    return axios.get('/api/games/my-games/ended')
  },

  getPlayers(gameId) {
    return axios.get(`/api/games/${gameId}/add-player`)
  },

  addPlayer(player) {
    return axios.post('/api/games/add-player', player)
  },

  getLeaderboard(gameId) {
    return axios.get(`api/games/${gameId}/leaderboard/available-balance`)
  },

  endGame(gameId) {
    return axios.get(`/api/games/${gameId}/end-game`)
  }

}