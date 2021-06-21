<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-row>
          <v-col cols="11">
            <v-row>
              <v-col cols="5"><h1>Creation date</h1></v-col>
              <v-col cols="3"><h1>Material name</h1></v-col>
              <v-col cols="3"><h1>Material quantity in kg</h1></v-col>
            </v-row></v-col
          >
        </v-row>

        <v-divider></v-divider>
        <div v-bind:key="order.id" v-for="order in orders">
          <v-row>
            <v-col cols="11">
              <v-btn depressed>
                <v-row class="entry">
                  <v-col cols="5">{{ order.created }}</v-col>
                  <v-col cols="3">{{ order.materialName }}</v-col>
                  <v-col cols="3">{{ order.quantity }}</v-col>
                </v-row>
              </v-btn>
            </v-col>
            <v-col cols="1">
              <v-btn @click="removeOrder(order)">Finish</v-btn>
            </v-col>
          </v-row>

          <v-divider></v-divider>
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
  height: 100%;
}

.entry {
  padding-top: 10px;
  padding-bottom: 10px;
}

.content {
  padding: 1%;
  height: 100%;
}

.v-card {
  height: 100%;
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
  name: "LogisticsManageOrders",

  data() {
    return {
      orders: [],
      dialog: false,
    };
  },

  async mounted() {
    this.getOrders();
  },

  methods: {
    async removeOrder(order) {
      console.log(order);
      const token = await this.$auth.getTokenSilently();

      await axios.delete("http://localhost:3000/deleteorder/" + order.id, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });

      this.dialog = false;
      this.getOrders();
    },

    async getOrders() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getorders", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.orders = res.data;
        });
    },
  },
};
</script>