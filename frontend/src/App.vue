<template>
  <v-app>
    <v-app-bar clipped-left app color="white" dark>
      <div class="d-flex align-center">
        <v-img
          alt="Productio Logo"
          class="shrink mr-2"
          contain
          src="./assets/productio.png"
          transition="scale-transition"
          width="40"
        />

        <h1 class="blacktext">Productio</h1>
      </div>

      <div class="ml-5" v-if="!$auth.loading">
        <!-- show login when not authenticated -->
        <v-btn
          depressed
          v-if="$auth.isAuthenticated"
          @click="$router.push('/')"
          >Home</v-btn
        >
      </div>

      <div class="ml-5" v-if="!$auth.loading">
        <!-- show login when not authenticated -->
        <v-btn
          depressed
          v-if="$auth.isAuthenticated"
          @click="$router.push('/departmentoverview')"
          >Departments</v-btn
        >
      </div>

      <v-spacer></v-spacer>

      <div v-if="!$auth.loading">
        <!-- show login when not authenticated -->
        <v-btn depressed v-if="!$auth.isAuthenticated" @click="login">Log in</v-btn>
        <!-- show logout when authenticated -->
        <div class="text-center" v-if="$auth.isAuthenticated">
          <v-menu open-on-hover offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn class="menu" depressed v-bind="attrs" v-on="on">
                menu <i class="fas fa-chevron-down"></i>
              </v-btn>
              <!-- <i class="fas fa-bars menu" dark v-bind="attrs" v-on="on"></i> -->
            </template>
            <v-list class="dropdown">
              <v-img
                alt="Productio Logo"
                class="shrink mr-2 logo"
                contain
                src="./assets/productio.png"
                transition="scale-transition"
                width="30"
              />
              <v-list-item v-for="(item, index) in items" :key="index">
                <v-list-item-title
                  style="cursor: pointer"
                  @click="item.action"
                  >{{ item.title }}</v-list-item-title
                >
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </div>
    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=PT+Serif&display=swap");

.v-btn {
  color: black;
  background-color: white !important;
}

.dropdown {
  text-align: center;
}

.logo {
  margin: 10px 30px;
  display: block;
}

.blacktext {
  color: black;
  font-family: "PT Serif", serif;
}

.v-main {
  background-color: lightcyan;
}
</style>

<script lang="ts">
import Vue from "vue";
import router from "./router";

export default Vue.extend({
  name: "App",

  components: {},

  data() {
    return {
      items: [
        {
          title: "Profile",
          action: () => router.push("/profile"),
        },
        {
          title: "Logout",
          action: () =>
            this.$auth.logout({
              returnTo: window.location.origin,
            }),
        },
      ],
    };
  },

  methods: {
    login() {
      this.$auth.loginWithRedirect({});
    },
  },
});
</script>
