<template>
  <div class="portfolio">
      <div id="portfolio-bin" class="portfolio-bin">
        <h2 id="bin-head">Portfolio</h2>
        <table id="portfolio-tab">
          <tr>
            <th>Ticker Symbol</th>
            <th>Price</th>
            <th>Exchange</th>
            <th>Shares</th>
            <th>Buy</th>
            <th>Sell</th>
          </tr>
          <tr>
            <td>$TSLA</td>
            <td>$246.38</td>
            <td>NASDAQ</td>
            <input type="number" id />
            <td button id="buy">+</td>
            <td button id="sell">-</td>
          </tr>
          <tr>
            <td>$AAPL</td>
            <td>$174.10</td>
            <td>NASDAQ</td>
            <input type="number" id />
            <td button id="buy">+</td>
            <td button id="sell">-</td>
          </tr>
          <tr>
            <td>$GOOGL</td>
            <td>$132.31</td>
            <td>NASDAQ</td>
            <input type="number" id />
            <td button id="buy">+</td>
            <td button id="sell">-</td>
          </tr>
          <tr>
            <td>$JPM</td>
            <td>$147.59</td>
            <td>NYSE</td>
            <input type="number" id />
            <td button id="buy">+</td>
            <td button id="sell">-</td>
          </tr>
          <tr>
            <td>$MSFT</td>
            <td>$313.64</td>
            <td>NASDAQ</td>
            <input type="number" id />
            <td button id="buy">+</td>
            <td button id="sell">-</td>
          </tr>
        </table>

    </div>
  </div>
</template>

<script>
export default {

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
</style>