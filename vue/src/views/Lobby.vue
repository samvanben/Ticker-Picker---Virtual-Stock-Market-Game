<template>
  <div id="lobby">
      <div id="lobby-bin">
        <h2 id="lobby-head">The Trading Floor</h2>
        <div id="current-buttons">
          <button id="new-game">+ New Game</button>
        </div>
        <table id="current-games">
          <tr>
            <th>Game Name</th>
            <th>Game Start Date</th>
            <th>Game End Date</th>
            <th>User Standing</th>
            <th>Balance</th>
            <th>View Portfolio</th>
          </tr>
          <tr class="row">
            <td>Game One</td>
            <td>12/12/2023</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
            <td>$88,000.00</td>
            <td><button class="port-button">View Portfolio</button></td>
          </tr>
          <tr class="row">
            <td>Game Two</td>
            <td>12/12/2023</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
            <td>$88,000.00</td>
            <td><button class="port-button">View Portfolio</button></td>
          </tr>
          <tr class="row">
            <td>Game Three</td>
            <td>12/12/2023</td>
            <td>12/12/2023</td>
            <td>1 / 10</td>
            <td>$88,000.00</td>
            <td><button class="port-button">View Portfolio</button></td>
          </tr>
        </table>
    </div>
  </div>
</template>

<script>
export default {
    name: "Lobby",
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