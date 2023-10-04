<template>
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <img id="icon-logo" src="..\src\assets\icon.png" alt="icon">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
#register {
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
.form-input-group label {
  padding-right: 0.5rem;
  font-weight: bold;
}
form p {
  margin-bottom: 3rem;
  margin-top: 2rem;
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
#icon-logo {
  margin-top: 2rem;
  height: 8rem;
  width: auto;
}
p > a {
  text-decoration: none;
  color: black;
}
p > a:hover {
  text-decoration: underline;
  color: black;
}
input {
  border-radius: 0.25rem;
  border: none;
}
@media (max-width: 1000px) {
  form {
  width: 400px;
  height: auto;
  }
  #icon-logo {
    width: 8em;
    height: auto;
  }
}
</style>
