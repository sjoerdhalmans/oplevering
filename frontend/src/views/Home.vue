<template>
  <div class="home">
    <h1 class="headtitle">The way of keeping our company connected</h1>

    <v-row>
      <v-col class="lowercard">
        <v-card class="mx-auto" max-width="400">
          <v-card-text>
            <div class="card-box">
              <img class="card-image" src="../assets/productio.png" />
            </div>
            <h1 class="blacktext">Stay connected, stay updated</h1>
            <p class="blacktext description">
              Making sure that all employees feel connected is very important,
              Productio tries to create a community feeling within it's company
              by making sure all departments can easily communicate with eachother
              and make approaching different colleagues feel safer and easier. Let's
              make remote work feel more like that cosy office feeling!
            </p>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col class="">
        <v-card class="mx-auto" max-width="500">
          <v-card-text>
            <div class="card-box">
              <img class="card-image" src="../assets/Dolphin.png" />
            </div>
            <h1 class="blacktext">
              One central system for all employees to use
            </h1>
            <p class="blacktext description">
              Being a large company brings many challenges, one of these is
              making big decisions, by making sure all our data is in one
              centralized system for all departments, we are able to make
              decisions based on data of all the different departments.
              It also enables our departments to quickly communicate with
              each other and remove any delays. All of this enables us to
              do one thing, turning our educated guesses into well thought out
              decisions.
            </p>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col class="lowercard">
        <v-card class="mx-auto" max-width="400">
          <v-card-text class="pb-3">
            <div class="card-box">
              <img
                class="card-image2"
                src="../assets/Exterminator-Dolhpin.png"
              />
            </div>
            <h1 class="blacktext">
              Keeping our data secure using the newest security technology
            </h1>
            <p class="blacktext description">
              Utilizing new and proven security techniques, Productio strives to be a secure
              platform that is safe and efficient to use. With all our data
              being encrypted and a cloud-bases authentication system in place
              to make sure that users stick within their authorization,
              Productio really does strive to make their users feel safe.
            </p>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
    <!-- <div v-if="!$auth.loading">
      <h1 v-if="$auth.isAuthenticated">
        You are part of {{ $auth.user[`http://demozero.net/roles`][0] }}
      </h1>
    </div> -->
  </div>
</template>

<style scoped>
.headtitle {
  text-align: center;
  padding-top: 4%;
  padding-bottom: 4%;
}

.lowercard {
  padding-top: 4%;
}

.fullcard {
  width: 70%;
}

.description {
  padding-top: 5%;
}

.card-image {
  display: block;
  margin-left: auto;
  margin-right: auto;
  height: 50%;
  width: 50%;
  padding-bottom: 12%;
}
.card-image2 {
  display: block;
  margin-left: auto;
  margin-right: auto;
  height: 85%;
  width: 85%;
  padding-bottom: 12%;
}
.card-box {
  height: 100%;
  width: 100%;
}

.blacktext {
  color: black;
  font-family: "PT Serif", serif;
  text-align: center;
}
</style>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component({
  components: {},
})
export default class Home extends Vue {
  login() {
    this.$auth.loginWithRedirect({});
  }

  // Log the user out
  logout() {
    this.$auth.logout({
      returnTo: window.location.origin,
    });
  }
}
</script>
