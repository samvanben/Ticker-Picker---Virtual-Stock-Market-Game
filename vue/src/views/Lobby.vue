<template>
  <div id="lobby">
      <div id="lobby-bin">
        <h2 id="lobby-head">The Trading Floor</h2>
        <div id="current-buttons">
          <router-link id="newGame-tag" v-bind:to="{name: 'newGame'}" v-if="$store.state.token != ''"><button id="newGame">+ New Game</button></router-link>

        </div>
        <table id="current-games">
          <tr>
            <th>Game Name</th>
            <th>Game Start Date</th>
            <th>Game End Date</th>
            <!-- user ranking -->
            <th>Balance</th>
            <!-- <th>Portfolio Value</th> -->
            <th>View Portfolio</th>
          </tr>
          <tr v-for="game in games" v-bind:key="game.gameId">
            <td>{{game.nameOfGame}}</td>
            <td>{{game.startDate}}</td>
            <td>{{game.endDate}}</td>
            <!-- index of -->
            <td>${{game.availableBalance}}</td>
            <!-- <td>${{game.totalBalance}}</td> -->
            <td><router-link v-bind:to="{name: 'portfolio', params:{id:game.gameId}}" id="port-tag" v-if="$store.state.token != ''"><button id="portfolio">View Portfolio</button></router-link></td>
          </tr>
          <!-- v-on:click="setGameId(game.gameId)" -->
        </table>
    </div>
  </div>
</template>

<script>
import GameService from '../services/GameService';

export default {
    name: "Lobby",
    data() {
      return {
        games: [],
        
      }
    },
    methods: {
      setGameId(gameId) {
        console.log(gameId)
        this.$store.commit('SET_GAME_ID', gameId)
      }
    },
    created() {
    GameService.getAllGames().then((response) => {
      this.games = response.data;
    })
  }
}
</script>

<style>
#lobby {
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
}

#lobby-bin {
    background-color: rgba(69, 69, 69, 0.75);
    width: 60%;
    height: 100%;
    text-align: center;
    border-radius: 20px;
    border-width: 1px;
    box-shadow: 12px 12px 10px black;
    display: flex;
    flex-direction: column;
    align-items: center;
}
#lobby-bin table {
    color: white;
    width: 80%;
    margin-bottom: 60px;
}
#new-game {
    background-color: rgb(90, 212, 90);
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 50px;
    border-radius: 20px;
    border-style: none;
    margin-bottom: 15px;
    margin-left: 10px;
    margin-right: 10px;
}
#new-game:hover {
    background-color: rgb(43, 114, 43);
}
.port-button {
    background-color: rgb(90, 212, 90);
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 30px;
    padding-right: 30px;
    border-radius: 10px;
    border-style: none;
}
.port-button:hover {
    background-color: rgb(43, 114, 43);
}
th {
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 20px;
  }
td {
    padding-top: 30px;
  }

</style>