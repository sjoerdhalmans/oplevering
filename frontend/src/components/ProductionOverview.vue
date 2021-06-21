<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-row>
          <v-col cols="3"><h1>Produced Item</h1></v-col>
          <v-col cols="3"><h1>Quantity/Minute</h1></v-col>
          <v-col cols="3"><h1>Active</h1></v-col>
        </v-row>
        <v-divider></v-divider>
        <div class="items">
          <div
            v-bind:key="productionLine.id"
            v-for="productionLine in productionLines"
          >
            <v-row class="entry">
              <v-col cols="3" class="text">{{
                productionLine.producedItem.name
              }}</v-col>
              <v-col cols="3" class="text">{{
                productionLine.quantityPerMinute
              }}</v-col>
              <v-col cols="3">
                <v-switch
                  @change="updateProductionLine(productionLine)"
                  v-model="productionLine.active"
                ></v-switch>
              </v-col>
              <v-col cols="3" class="text">
                <i
                  @click="
                    $router.push({
                      name: 'productionmanagement',
                      params: { productionLineProp: productionLine },
                    })
                  "
                  class="pr-10 fas fa-edit edit"
                ></i>
                |
                <i
                  class="pl-10 edit fas fa-trash-alt"
                  @click="deleteProductionLine(productionLine)"
                ></i>
              </v-col>
            </v-row>
            <v-divider></v-divider>
          </div>
        </div>
        <v-divider class="mt-5"></v-divider>
        <div class="buttonstyle">
          <v-btn @click="$router.push('/production/management')" color="primary"
            >Create New Production Line</v-btn
          >
        </div>
      </div>
    </v-card>
  </div>
</template>

<style scoped>
.text {
  margin: auto;
}

.edit {
  cursor: pointer;
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

.items {
  height: 55vh;
  overflow-y: scroll;
  overflow-x: hidden;
}

.v-btn {
  align-items: center;
  margin: auto;
  color: black;
  font-family: "PT Serif", serif;
  width: auto;
}
</style>

<script>
import axios from "axios";
export default {
  name: "ProductionOverview",

  data() {
    return {
      productionLines: [],
      materials: [],
      loadedLines: [],
    };
  },

  async mounted() {
    await this.getMaterials();
    await this.getProductionLines();
    await this.fillProductionLines();
  },

  methods: {
    async deleteProductionLine(productionLine) {
      const token = await this.$auth.getTokenSilently();

      await axios.delete("http://localhost:3000/deleteproductionline/" + productionLine.id, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });

      this.getProductionLines();
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

    async getProductionLines() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getproductionlines", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.productionLines = res.data;
        });
    },

    async fillProductionLines() {
      this.productionLines.forEach(async (productionLine) => {
        const filledProductionLine = await this.fillMaterials(productionLine);

        this.loadedLines.push(filledProductionLine);
      });
    },

    async fillMaterials(emptyLine) {
      await this.materials.forEach((material) => {
        emptyLine.producedItem.requiredMaterials.forEach((requiredMaterial) => {
          if (material.id == requiredMaterial.materialId) {
            requiredMaterial.name = material.name;
            requiredMaterial.price = material.price;
          }
        });
      });

      return emptyLine;
    },

    async updateProductionLine(productionLine) {
      const token = await this.$auth.getTokenSilently();
      console.log(productionLine);

      await axios.put(
        "http://localhost:3000/updateproductionline",
        productionLine,
        {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        }
      );
    },
  },
};
</script>