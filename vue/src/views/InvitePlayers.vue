<template>
  <div id="invitePlayers">
      <form>
          <h1>Add Players!</h1>
          <table id="invite-players">
              <tr>
                  <td>Usernames</td>
              </tr>
              <tr v-for="player in players" v-bind:key="player.id">
                  <td>{{player.id}}</td>
                  <td>{{player.username}}</td>
                  <button v-on:click="invite(player.id)">Add</button>
              </tr>
          </table>
      </form>
  </div>
</template>

<script scoped>
import GameService from '../services/GameService';

export default {
    name: "InvitePlayers",
    data() {
        return {
            players: [],
        }
    },
    created() {
        GameService.getPlayers(this.$route.params.id).then((response) => {
            console.log(response.data)
            this.players = response.data;
        })
    }
    
}
</script>

<style scoped>
label {
  margin-right: 0.5rem;
}
#newGame {
  width: 100vw;
  height: auto;
  display: flex;
  justify-content: center;
  align-items: center;
}
form {
  background: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(10px);
  border-radius: 20px;
  width:30em;
  height: auto;
  text-align: center;
}
form h1 {
  padding-top: 1rem;
  color:black;
  font-size: 1.35rem;
}
form input:first-of-type {
  margin-top: 2rem;
}
#invite {
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
  #invite:hover {
    background-color: rgb(43, 114, 43);
    font-weight: bold;
  }
  th {
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 15px;
  }
  td {
    padding-bottom: 5px;
  }
  #invite-players {
    grid-area: bin1;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  form button {
  margin-top: 2rem;
  padding-left: 3rem;
  padding-right: 3rem;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  background-color: rgb(56, 216, 56);
  border-radius: 1rem;
  border-style: none;
  box-shadow: 2px 2px 4px;
  font-weight: bold;
}
form button:hover {
  background-color: rgb(26, 116, 26);
}

</style>