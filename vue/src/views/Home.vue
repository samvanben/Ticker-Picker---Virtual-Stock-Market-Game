<template>
  <div class="home">
    <div id="home-bin-1" class="home-bin-upper">
        <h2 id="bin1-head">Current Games</h2>
        <table id="current-games">
          <tr>
            <th>Game Name</th>
            <th>Game End Date</th>
            <th>Current Place</th>
          </tr>
          <tr v-for="game in games.slice(0,3)" v-bind:key="game.gameId">
            <td>{{game.nameOfGame}}</td>
            <td>{{game.endDate}}</td>
            <td>balance</td>
          </tr>
        </table>
        <div id="current-buttons">
          <router-link id="lobby-tag" v-bind:to="{name: 'lobby'}" v-if="$store.state.token != ''"><button id="view-all">View Games</button></router-link>
          <router-link id="newGame-tag" v-bind:to="{name: 'newGame'}" v-if="$store.state.token != ''"><button id="newGame">+ New Game</button></router-link>
        </div>
    </div>
    <div id="home-bin-2" class="home-bin-upper">
        <h2 id="bin2-head">Past Games</h2>
        <table id="past-games">
          <tr>
            <th>Game Name</th>
            <th>Game End Date</th>
            <th>Result</th>
          </tr>
          <tr>
            <td>Game One</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
          </tr>
          <tr>
            <td>Game Two</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
          </tr>
          <tr>
            <td>Game Three</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
          </tr>
        </table>
        <router-link id="leaderboard-tag" v-bind:to="{name: 'leaderboard'}" v-if="$store.state.token != ''"><button id="view-all">View Games</button></router-link>
    </div>
    <div id="home-bin-3" class="home-bin-lower">
        <h2 id="bin3-head">Stock To Watch</h2>
        <table id="hot-stock">
          <tr>
            <th>Company</th>
            <th>Ticker Symbol</th>
            <th>Price</th>
            <th>Exchange</th>
          </tr>
          <tr>
            <td>Apple</td>
            <td>$AAPL</td>
            <td>$174.10</td>
            <td>NASDAQ</td>
          </tr>
        </table>  
    </div>
    <div id="home-bin-4" class="home-bin-lower">
      <div id="quick-search">
        <h2 id="bin4-head">Search Stocks: </h2>
        <input v-on:keyup.enter="getStock" v-model="stockToSearch" type="search" placeholder="  Search for a stock...">
      </div>
        <table id="search-stock">
          <tr>
            <th>Ticker Symbol</th>
            <th>Price</th>
            <th>Exchange</th>
          </tr>
          <tr>
            <td>${{searchStock.symbol}}</td>
            <td>${{searchStock.close}}</td>
            <td>{{searchStock.exchange}}</td>
          </tr>
        </table>
    </div>
  </div>
</template>

<script>
import StockService from '../services/StockService';
import GameService from '../services/GameService';

export default {
  name: "home",
  data() {
    return {
      stockToSearch: "",
      searchStock: {
        symbol: "----",
        close: "---.--",
        exchange: "-----"
      },
      // We want to return a list of games the current user is in
      games: [],
    };
  },
  methods: {
    viewStock(symbol) {
      this.$router(`/${symbol}`)
    },
    getStock() {
      StockService.getSearchStock(this.stockToSearch).then((response) => {
        this.searchStock = response.data;
      })
    }
  },
    created() {
      GameService.getAllGames().then((response) => {
        this.games = response.data;
      })
    }
}
</script>

<style scoped>
  .home {
    margin-left: 10%;
    width: 80vw;
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: 
      "bin1 bin2"
      "bin3 bin4";
    gap: 20px;
    margin-bottom: 50px;
  }
  #current-games {
    color: white;
    margin-bottom: 10px;
  }
  #past-games {
    color: white;
    margin-bottom: 10px;
  }
  #hot-stock {
    color: white;
    margin-bottom: 15px;
  }
  th {
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 0px;
  }
  .home div h2 {
    color: white;
  }
  #home-bin-1 {
    grid-area: bin1;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 5px;
  }
  #home-bin-2 {
    grid-area: bin2;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 5px;
  }
  #new-game {
    background-color: rgb(90, 212, 90);
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 30px;
    padding-right: 30px;
    border-radius: 20px;
    border-style: none;
    margin-bottom: 15px;
    margin-left: 10px;
    margin-right: 10px;
  }
  #new-game:hover {
    background-color: rgb(43, 114, 43);
    font-weight: bold;
  }

  #view-all {
    background-color: rgb(90, 212, 90);
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 30px;
    padding-right: 30px;
    border-radius: 20px;
    border-style: none;
    margin-bottom: 15px;
    margin-top: 10px;
    margin-left: 10px;
    margin-right: 10px;
  }
  #view-all:hover {
    background-color: rgb(43, 114, 43);
    font-weight: bold;
  }
   
  #home-bin-3 {
    grid-area: bin3;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 5px;
  }
   #home-bin-4 {
    grid-area: bin4;
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-bottom: 5px;
  }
  #quick-search {
    display: flex;
    align-items: center;
  }
  .home-bin-upper {
    background-color: rgba(69, 69, 69, 0.75);
    width: 1fr;
    height: 100%;
    text-align: center;
    border-radius: 20px;
    border-width: 1px;
    box-shadow: 12px 12px 10px black;
  }
  .home-bin-lower {
    background-color: rgba(69, 69, 69, 0.75);
    width: 1fr;
    height: 100%;
    text-align: center;
    border-radius: 20px;
    border-width: 1px;
    box-shadow: 12px 12px 10px black;
  }
  #search-stock {
    color: white;
  }
  input {
    margin-left: 20px;
    border-radius: 5px;
    border-style: none;
    height: 50%;
  }
  


  @media (max-width: 1000px) {
    .home{
      grid-template-columns: 1fr;
      grid-template-areas: 
        "bin1" 
        "bin2"
        "bin3" 
        "bin4";
    }
  }
</style>
