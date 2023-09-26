<template>
  <div>
      <h1>List of all stocks</h1>
      <!-- We want to repeat this for each stock -->
      <div class="individual" v-for="stock in stocks" v-bind:key="stock.stockId">
        <h2>{{stock.name}} (ID: {{stock.stockId}})</h2>
        Symbol: {{stock.symbol}} <br />
        Price: {{stock.price}}
      </div>
  </div>
</template>

<script>
import stockService from '../services/StockService.js';

export default {
    name: "AllStocksComponent",
    data() {
        return{
            stocks: []
        }
    },
    // We want to load puppies as soon as someone visits the page
    created(){
        stockService.getStocks()
            .then(response => this.stocks = response.data)
    }
}
</script>

<style>
.individual{
    border: 1px solid black;
    background-color: whitesmoke;
}
</style>