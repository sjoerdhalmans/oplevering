<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <h1>Order Materials</h1>
        <v-divider></v-divider>
        <v-row class="content">
          <v-col cols="1"></v-col>
          <v-col cols="4">
            <v-select
              v-model="selectedMaterial"
              :items="materials"
              label="Material"
              outlined
            >
              <template v-slot:item="{ item }">
                {{ item.name }}
              </template>
              <template v-slot:selection="{ item }">
                {{ item.name }}
              </template>
            </v-select></v-col
          >
          <v-col cols="2"></v-col>
          <v-col cols="4">
            <v-text-field
              @change="calculatePrice()"
              v-model="selectedQuantity"
              type="number"
              label="Quantity in kilograms"
            ></v-text-field>
          </v-col>
          <v-col cols="1"></v-col>
        </v-row>
        <v-divider></v-divider>
        <div class="content">
          <h1>Price: {{ price }} {{ selectedMaterial.currency }}</h1>
          <v-btn class="mt-10" color="primary" @click="orderMaterials()"
            >Order materials</v-btn
          >
        </div>
      </div>
    </v-card>
  </div>
</template>

<style scoped>
.blacktext {
  color: black;
  font-family: "PT Serif", serif;
  padding: 1%;
}

.content {
  padding: 1%;
}

.v-btn {
  color: black;
  font-family: "PT Serif", serif;
  background-color: white !important;
}
</style>

<script>
import axios from "axios";
export default {
  name: "LogisticsOrderMaterials",

  data() {
    return {
      materials: [],
      selectedMaterial: {},
      selectedQuantity: 0,
      price: 0,
    };
  },

  async mounted() {
    await this.getMaterials();
    console.log(this.materials);
  },

  methods: {
    calculatePrice() {
      const quantity = parseInt(this.selectedQuantity);

      this.price = this.selectedMaterial.price * quantity;
    },

    async getMaterials() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getmaterials", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.materials = res.data;
        });
    },

    async orderMaterials() {
      const token = await this.$auth.getTokenSilently();

      const calldata = {
        price: this.price,
        currency: this.selectedMaterial.currency,
        materialName: this.selectedMaterial.name,
        quantity: this.selectedQuantity,
      };

      await axios.post("http://localhost:3000/archiveorder", calldata, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });

      const order = {
        type: "Material",
        itemId: this.selectedMaterial.id,
        quantity: this.selectedQuantity,
      };

      this.addMaterialToInventory(order);

      this.selectedMaterial = {};
      (this.price = 0), (this.selectedQuantity = 0);
    },

    async addMaterialToInventory(order) {
      const token = await this.$auth.getTokenSilently();

      await axios.post("http://localhost:3000/addinvitem", order, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });
    },
  },
};
</script>