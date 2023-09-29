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
        <div id="current-buttons">
          <button id="view-all">View Games</button>
          <button id="new-game">+ New Game</button>
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
        <button id="view-all">View Games</button>
    </div>
    <div id="home-bin-3" class="home-bin-lower">
        <h2 id="bin3-head">Stock To Watch</h2>
        <table id="hot-stock">
          <tr>
            <th>Company</th>
            <th>Ticker Symbol</th>
            <th>Price</th>
            <th>% Change</th>
            <th>$ Change</th>
          </tr>
          <tr>
            <td>Apple</td>
            <td>$AAPL</td>
            <td>$174.10</td>
            <td id="percentage">0.58%</td>
            <td id="dollar">$1.04</td>
          </tr>
        </table>  
    </div>
    <div id="home-bin-4" class="home-bin-lower">
        <h2 id="bin4-head">Bin 4</h2>
    </div>
  </div>
</template>

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
    margin-bottom: 15px;
  }
  #past-games {
    color: white;
    margin-bottom: 15px;
  }
  #hot-stock {
    color: white;
    margin-bottom: 15px;
  }
  th {
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 15px;
  }
  td {
    padding-bottom: 5px;
  }
  .home div h2 {
    color: white;
  }
  #home-bin-1 {
    grid-area: bin1;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  #home-bin-2 {
    grid-area: bin2;
    display: flex;
    flex-direction: column;
    align-items: center;
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
  }
   #home-bin-4 {
    grid-area: bin4;
    display: flex;
    flex-direction: column;
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
  #percentage {
    color: rgb(90, 212, 90);
  }
  #dollar {
    color: rgb(90, 212, 90); 
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

<script>
export default {
  name: "home"
};
</script>
