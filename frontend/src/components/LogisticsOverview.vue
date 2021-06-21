<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-row>
          <v-col cols="5"><h1>Date of purchase</h1></v-col>
          <v-col cols="3"><h1>Material name</h1></v-col>
          <v-col cols="3"><h1>Material quantity in kg</h1></v-col>
          <v-col cols="1"><h1>Price</h1></v-col>
        </v-row>
        <v-divider></v-divider>
        <div class="items">
          <div v-bind:key="receipt.id" v-for="receipt in orderReceipts">
            <v-row class="entry">
              <v-col cols="5">{{ receipt.finished }}</v-col>
              <v-col cols="3">{{ receipt.materialName }}</v-col>
              <v-col cols="3">{{ receipt.quantity }}</v-col>
              <v-col cols="1">{{ receipt.price }} {{ receipt.currency }}</v-col>
            </v-row>
            <v-divider></v-divider>
          </div>
        </div>
      </div>
    </v-card>
  </div>
</template>

<style scoped>
.blacktext {
  color: black;
  font-family: "PT Serif", serif !important;
  padding: 1%;
  max-height: 100%;
}

.entry {
  padding-top: 10px;
  padding-bottom: 10px;
}

.content {
  padding: 1%;
  max-height: 75%;
}

.v-card {
  max-height: 100%;
}

.items {
  height: 73vh;
  overflow-y: scroll;
}

.v-btn {
  text-align: left;
  color: black;
  font-family: "PT Serif", serif;
  background-color: white !important;
  width: 100%;
}
</style>

<script>
import axios from "axios";
export default {
  name: "LogisticsOverview",

  data() {
    return {
      orderReceipts: [],
    };
  },

  async mounted() {
    this.getReceipts();
  },

  methods: {
    async getReceipts() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getreceipts", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.orderReceipts = res.data;
        });
    },
  },
};
</script>