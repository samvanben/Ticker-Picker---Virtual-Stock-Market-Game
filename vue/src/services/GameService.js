import axios from 'axios';

export default {

  createGame(gameBody) {
    return axios.post('/api/games', gameBody)
  },

  getAllGames() {
      return axios.get('/api/games/my-games')
  },

}