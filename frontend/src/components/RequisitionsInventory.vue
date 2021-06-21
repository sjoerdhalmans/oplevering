<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-row>
          <v-col cols="4"><h1>Name</h1></v-col>
          <v-col cols="4"><h1>Type</h1></v-col>
          <v-col cols="4"><h1>Quantity</h1></v-col>
        </v-row>
        <v-divider></v-divider>
        <div v-bind:key="item.id" v-for="item in filledItems">
          <v-row class="entry">
            <v-col cols="4">{{ item.name }}</v-col>
            <v-col cols="4">{{ item.type }}</v-col>
            <v-col cols="4">{{ item.quantity }}</v-col>
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
  name: "RequisitionsInventory",

  data() {
    return {
      materials: [],
      emptyItems: [],
      filledItems: [],
      producedItems: [],
    };
  },

  async mounted() {
    await this.getItems();
    await this.getMaterials();
    await this.getEmptyItems();
    await this.fillItems();
  },

  methods: {
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

    async getItems() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getitems", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.producedItems = res.data;
        });
    },

    async getEmptyItems() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getallstoreditems", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.emptyItems = res.data;
        });
    },

    async fillItems() {
      this.emptyItems.forEach(async (emptyItem) => {
        if (emptyItem.type == "Material") {
          const filledItem = await this.fillMaterials(emptyItem);

          this.filledItems.push(filledItem);
        } else if (emptyItem.type == "Produced Item") {
          const filledItem = await this.fillProducedItems(emptyItem);

          this.filledItems.push(filledItem);
        }
      });
    },

    async fillMaterials(emptyItem) {
      await this.materials.forEach((material) => {
        if (material.id == emptyItem.itemId) {
          emptyItem.name = material.name;
        }
      });

      emptyItem.quantity = emptyItem.quantity.toString() + " KG";

      return emptyItem;
    },

    async fillProducedItems(emptyItem) {
      await this.producedItems.forEach((item) => {
        if (item.id == emptyItem.itemId) {
          emptyItem.name = item.name;
        }
      });

      emptyItem.quantity = emptyItem.quantity.toString() + " Units";

      return emptyItem;
    },
  },
};
</script>