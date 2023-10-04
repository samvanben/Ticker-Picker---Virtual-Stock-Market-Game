import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import NotFound from "../views/NotFound.vue"

import Lobby from "../views/Lobby.vue"
import GenLeader from "../views/GeneralLeaderboard.vue"
import SpecLeader from "../views/SpecificLeaderboard.vue"


import NewGame from "../views/NewGame.vue"
import Portfolio from "../views/Portfolio.vue"


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      // temporary, goes to 404 page
      path: "/lobby",
      name: "lobby",
      component: Lobby,
    },
    {
      // temporary, goes to 404 page
      path: "/leaderboard",
      name: "leaderboard",
      component: GenLeader,
    },
    {
      // temporary, goes to 404 page
      path: "/news",
      name: "news",
      component: NotFound,
    },
    {

      path: "/leaderboard/spec",
      name: "spec-leaderboard",
      component: SpecLeader,
    },
    {
      path: "/new_game",
      name: "newGame",
      component: NewGame,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/portfolio:id",
      name: "portfolio",
      component: Portfolio,
      meta: {
        requiresAuth: true
      }
    },
    {
      path:'/:pathMatch(.*)*',
      name:'NotFound',
      component:NotFound
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
