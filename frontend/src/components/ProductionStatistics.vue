<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-data-table
          :headers="headers"
          :items="tableData"
          :items-per-page="10"
          class="elevation-1"
        ></v-data-table>
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
import { add } from "lodash";
export default {
  name: "ProductionStatistics",

  data() {
    return {
      headers: [
        {
          text: "Material Name",
          align: "start",
          value: "name",
        },
        { text: "Needed quantity in kilos/minute", value: `totalQuantity` },
        { text: "Stored quantity in kilos", value: `storedQuantity` },
      ],
      materials: [],
      productionLines: [],
      loadedLines: [],
      tableData: [],
      neededMaterials: [],
      allMaterials: [],
      storedMaterials: [],
    };
  },

  async mounted() {
    await this.findAllStoredMaterials();
    await this.getMaterials();
    await this.getProductionLines();
    await this.fillProductionLines();
    await this.prepareTableData();
  },

  methods: {
    async findAllStoredMaterials() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getstoredmaterials", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.storedMaterials = res.data;
        });
    },

    async prepareTableData() {
      await this.findAllNeededMaterials();

      await this.matchNeededMaterials();

      this.allMaterials.forEach(async (material) => {
        const data = {
          name: material.name,
          totalQuantity: material.quantity,
          storedQuantity: await this.findStoredQuantity(material),
        };

        this.tableData.push(data);
      });
    },

    async findStoredQuantity(material) {
      let foundMaterial;

      await this.storedMaterials.forEach((storedMaterial) => {
        if (storedMaterial.itemId == material.id) {
          foundMaterial = storedMaterial;
        }
      });

      return foundMaterial.quantity;
    },

    async matchNeededMaterials() {
      await this.neededMaterials.forEach((neededMaterial) => {
        let added = false;

        this.allMaterials.forEach((material) => {
          if (neededMaterial.id == material.id) {
            material.quantity = material.quantity + neededMaterial.quantity;
            added = true;
          }
        });

        if (!added) {
          this.allMaterials.push(neededMaterial);
        }
      });
    },

    async findAllNeededMaterials() {
      await this.productionLines.forEach((productionLine) => {
        if (productionLine.active) {
          productionLine.producedItem.requiredMaterials.forEach(
            (neededMaterial) => {
              neededMaterial.quantity =
                neededMaterial.quantity * productionLine.quantityPerMinute;

              this.neededMaterials.push(neededMaterial);
            }
          );
        }
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
  },
};
</script>