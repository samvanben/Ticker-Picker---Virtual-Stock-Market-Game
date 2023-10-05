<template>
  <div class="portfolio">
      <div id="portfolio-bin" class="portfolio-bin">
        <h2 id="bin-head">Portfolio</h2>
        <h5>Input Number of Shares:</h5>
        <input v-model="numbers" type="number" id />
        <table id="portfolio-tab">
          <tr>
            <th>Ticker Symbol</th>
            <th>Price</th>
            <th>Exchange</th>
            <th>Number of Shares</th>
            
            <th>Buy</th>
            <th>Sell</th>
          </tr>
          <tr v-for="(stock, index) in stockList" v-bind:key="index">
            <td>${{stock.symbol}}</td>
            <td>${{stock.close}}</td>
            <td>{{stock.exchange}}</td>
            <td>{{sharesTest(index)}}</td>
            <!-- <td>{{sharesList[index]}}</td> -->
             
            <td v-on:click.prevent="buyStocks(stock.symbol, stock)" button id="buy">+</td>
            <td v-on:click.prevent="sellStocks(stock.symbol, stock)" button id="sell">-</td>
          </tr>
        </table>

        <h3>Balance: ${{balance}}</h3>

        <!-- <h3>Value: ${{value}}</h3> -->

        <button id = "end-button" v-on:click.prevent="endGameEarly">End Game Early</button>

    </div>
  </div>
</template>

<script>
import BalanceService from '../services/BalanceService';
import GameService from '../services/GameService';
import StockService from '../services/StockService';

export default {
  name: 'balance',
  data() {
    return {
      balance: 0,
      value: 0,
      stockList: [],
      numbers: 0,
      sharesList: [],
      test: [],
      // testShares: [
      //   {
      //     symbol: 'TSLA', quantity: 0
      //   },
      //   {
      //     symbol: 'AAPL', quantity: 0
      //   },
      //   {
      //     symbol: 'GOOGL', quantity: 0
      //   },
      //   {
      //     symbol: 'JPM', quantity: 0
      //   },
      //   {
      //     symbol: 'MSFT', quantity: 0
      //   }
      // ]
    };
  },

  methods: {
    buyStocks(symbol, stock) {
      BalanceService.buyStock(this.$route.params.id, symbol, this.numbers, stock).then((response) => {
        if(response.data == true){
          this.balance = BalanceService.getBalance(this.$route.params.id).then((response) => {
            this.balance = response.data;
            let doesntExist = true;
            this.sharesList.find((share) => {
        if(share.symbol.toUpperCase() === symbol) {
          share.quantity = parseInt(share.quantity) + parseInt(this.numbers);
          doesntExist = false;
        }
      })
          if(doesntExist == true){
            
            let newShare = {symbol: symbol, quantity: this.numbers}
            this.sharesList.push(newShare)
          }
      })
          
        }
      })
      // let currentShares = this.sharesList.find((share) => {
      //   if(share.symbol.toUpperCase() === symbol) {
      //     share.quantity = parseInt(share.quantity) + parseInt(this.numbers);
      //   }
      // })
      // console.log(currentShares)
    },
    sellStocks(symbol, stock) {
      BalanceService.sellStock(this.$route.params.id, symbol, this.numbers, stock).then((response) => {
        if(response.data == true){
          this.balance = BalanceService.getBalance(this.$route.params.id).then((response) => {
            this.balance = response.data;
            this.sharesList.map((share) => {
              console.log(share.symbol + ' ' + symbol)
        if(share.symbol.toUpperCase() === symbol) {
          console.log('reach selling if statement')
          share.quantity -= parseInt(this.numbers);
        }
      })
          })
        }
      })
      // let currentShares = this.sharesList.find((share) => {
      //   if(share.symbol.toUpperCase() === symbol) {
      //     share.quantity -= parseInt(this.numbers);
      //   }
      // })
      // console.log(currentShares)
    },
    endGameEarly() {
      GameService.endGame(this.$route.params.id).then((response) => {
        console.log(response)
        if(response.data == true){
            this.$router.push('/lobby')
          }
          console.log(response.data)
      })
    },
    getShares(symbol) {
      StockService.getShare(this.$route.params.id, symbol).then((response) => {
        console.log(response)
        this.sharesList.push(response.data);
      })
    },
    shares(symbol) {
        return this.sharesList.forEach((share) => {
          console.log(share)
          if(share.symbol.toUpperCase() === symbol.toUpperCase()) {
            console.log(share.quantity);
            return share.quantity;
          }
        })
      },

    sharesTest(index) {
      console.log(index)
      // this.stockList.forEach((stock) => {
      //   this.sharesList.forEach((share) => {
      //     if(share.symbol.toUpperCase() === stock.symbol.toUpperCase()) {
      //       this.test.push(share.quantity)
      //     }
      //   })
      // })
      let currentShares = this.sharesList.find((share) => {
        if(share.symbol.toUpperCase() === index) {
          return share;
        }
      })
      if(currentShares === undefined){
        return 0;
      }
      console.log(currentShares)
      return currentShares.quantity
      }
    
  },

  created() {
    BalanceService.getBalance(this.$route.params.id).then((response) => {
      this.balance = response.data;
    }),
    BalanceService.getValue(this.$route.params.id).then((response) => {
      this.value = response.data;
    }),
    StockService.getGameStockList().then((response) => {
      this.stockList = response.data;
    }),
    StockService.getAllShares(this.$route.params.id).then((response) => {
      console.log(this.$route.params.id + ' parameter id')
      this.sharesList = response.data;
      console.log(this.sharesList)
    })
    // function(){
    //   for(let stock of this.stockList) {
    //     this.shareList.push(this.getShares(stock.symbol))
    //   }
    // }
  }

  // computed: { shares(symbol) {
  //     console.log(symbol)
  //       return this.sharesList.forEach((share) => {
  //         if(share.symbol.toUpperCase() === symbol.toUpperCase()) {
  //           return share.quantity
  //         }
  //       })
  //     }}
}
</script>

<style scoped>
    .portfolio {
    margin-left: 10%;
    width: 80vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 20px;
    margin-bottom: 50px;
  }
  #portfolio-bin {
    grid-area: bin;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  #portfolio-tab {
    color: white;
    margin-bottom: 15px;
  }
  .portfolio-bin {
    background-color: rgba(69, 69, 69, 0.75);
    width: 1fr;
    height: 100%;
    text-align: center;
    border-radius: 20px;
    border-width: 1px;
    box-shadow: 12px 12px 10px black;
  }
  .portfolio div h2 {
    color: white;
  }
  h3{
    color: white;
  }
  h5{
    color: white;
    text-decoration: underline;
  }
  th {
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 15px;
  }
  td {
    padding-bottom: 5px;
  }
  #buy {
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
  #buy:hover {
    background-color: rgb(43, 114, 43);
    font-weight: bold;
  }
  #sell {
  background-color: rgb(255, 0, 0, 0.849);
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
  #sell:hover {
    background-color: rgba(255, 0, 0, 0.63);
    font-weight: bold;
  }
  #end-button {
    background-color: rgba(219, 41, 41, 0.849);
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 30px;
    padding-right: 30px;
    border-radius: 10px;
    border-style: none;
    color: white;
    font-weight: bold;
    margin-bottom: 20px;
}
#end-button:hover {
    background-color: rgba(219, 41, 41, 0.699);
}
</style>