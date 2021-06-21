<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-slider
          class="slider"
          v-model="slider.val"
          :color="slider.color"
          :label="slider.label"
          :thumb-color="slider.color"
          max="50"
          min="0"
          thumb-label="always"
          @change="calculatePrice()"
        ></v-slider>

        <v-select
          v-model="selectedItem"
          name="material_id"
          :items="loadedItems"
          label="Producable Items"
          outlined
          @change="calculatePrice()"
        >
          <template v-slot:item="{ item }">
            {{ item.name }} || Unit Price {{ item.price }}
            {{ item.currency }} || {{ item.shortDescription }}
          </template>
          <template v-slot:selection="{ item }">
            {{ item.name }} || Unit Price {{ item.price }}
            {{ item.currency }} || {{ item.shortDescription }}
          </template>
        </v-select>

        <v-data-table
          v-if="selectedItem"
          :headers="headers"
          :items="selectedItem.requiredMaterials"
          :items-per-page="5"
          class="elevation-1"
        ></v-data-table>

        <div v-if="!productionLine" class="buttonstyle">
          <v-btn color="primary" @click="createProductionLine()">
            Create ProductionLine
          </v-btn>
        </div>

        <div v-if="productionLine" class="buttonstyle">
          <v-btn color="primary" @click="updateProductionLine()">
            Update ProductionLine
          </v-btn>
        </div>
      </div>
    </v-card>
  </div>
</template>

<style scoped>
.slider {
  width: 75%;
}

.buttonstyle {
  width: auto;
  text-align: center;
  padding-top: 3.6%;
}

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
  height: 100%;
}

.v-btn {
  text-align: left;
  color: black;
  font-family: "PT Serif", serif;
  width: auto;
}
</style>

<script>
import axios from "axios";
export default {
  name: "ProductionManagement",

  props: {
    productionLineProp: {},
  },

  data() {
    return {
      headers: [
        {
          text: "Material Name",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Needed quantity in kilos/minute", value: `totalQuantity` },
        { text: "Price per kilo in euros", value: `price` },
        { text: "Total price/minute in euros", value: `totalPrice` },
      ],
      materials: [],
      items: [],
      loadedItems: [],
      calculatedPrice: 0,
      selectedItem: { name: "test" },
      slider: { label: "Produced/minute", val: 0, color: "blue darken-3" },
      productionLine: this.productionLineProp,
    };
  },

  async mounted() {
    await this.getMaterials();
    await this.getItems();
    await this.fillItems();

    if (this.productionLine) {
      this.selectedItem = this.productionLine.producedItem;
      this.slider.val = this.productionLine.quantityPerMinute;

      // this.selectedItem = this.loadedItems[0];
      this.selectedItem = await this.findItem();

      await this.calculatePrice();
    }
  },

  methods: {
    async findItem() {
      let foundItem = {};

      await this.loadedItems.forEach((loadedItem) => {
        if (loadedItem.id == this.productionLine.producedItem.id) {
          foundItem = loadedItem;
        }
      });

      return foundItem;
    },

    calculatePrice() {
      if (this.selectedItem) {
        this.selectedItem.requiredMaterials.forEach((material) => {
          console.log(this.slider.val);

          this.$set(
            material,
            "totalQuantity",
            this.slider.val * material.quantity
          );

          this.$set(
            material,
            "totalPrice",
            material.price * material.totalQuantity
          );
        });
      }
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

    async createProductionLine() {
      const token = await this.$auth.getTokenSilently();

      const productionLine = {
        producedItem: this.selectedItem,
        quantityPerMinute: this.slider.val,
      };

      await axios.post(
        "http://localhost:3000/createproductionline",
        productionLine,
        {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        }
      );

      this.selectedItem = {};
      this.slider.val = 0;

      this.$router.push("/production/overview");
    },

    async updateProductionLine() {
      const token = await this.$auth.getTokenSilently();

      const productionLine = {
        id: this.productionLine.id,
        active: this.productionLine.active,
        producedItem: this.selectedItem,
        quantityPerMinute: this.slider.val,
      };

      console.log()

      await axios.put(
        "http://localhost:3000/updateproductionline",
        productionLine,
        {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        }
      );

      this.selectedItem = {};
      this.slider.val = 0;
      this.$router.push("/production/overview");
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
          this.items = res.data;
        });
    },

    async fillItems() {
      await this.items.forEach(async (item) => {
        const filledItem = await this.fillMaterials(item);

        this.loadedItems.push(filledItem);
      });
    },

    async fillMaterials(emptyItem) {
      await this.materials.forEach((material) => {
        emptyItem.requiredMaterials.forEach((requiredMaterial) => {
          if (material.id == requiredMaterial.materialId) {
            requiredMaterial.name = material.name;
            requiredMaterial.price = material.price;
          }
        });
      });

      return emptyItem;
    },
  },
};
</script>