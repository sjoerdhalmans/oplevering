<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <v-row>
          <v-col cols="4"><h1>Created</h1></v-col>
          <v-col cols="4"><h1>Type</h1></v-col>
          <v-col cols="4"><h1>Quantity</h1></v-col>
        </v-row>
        <v-divider></v-divider>
        <div v-bind:key="requisition.id" v-for="requisition in requisitions">
          <v-row class="entry">
            <v-col cols="4">{{ requisition.created }}</v-col>
            <v-col cols="4">{{ requisition.name }}</v-col>
            <v-col cols="4">{{ requisition.quantity }}</v-col>
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
  name: "RequisitionsOverview",

  data() {
    return {
      materials: [],
      requisitions: [],
      emptyRequisitions: [],
    };
  },

  async mounted() {
    await this.getMaterials();
    await this.getEmptyRequisitions();
    await this.fillRequisitions();
    console.log(this.requisitions);
    console.log(this.emptyRequisitions)
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

    async getEmptyRequisitions() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getallrequisitions", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.emptyRequisitions = res.data;
        });
    },

    async fillRequisitions() {
      console.log(this.emptyRequisitions)
      this.emptyRequisitions.forEach(async (emptyRequisition) => {
        if (emptyRequisition.orderItemType == "Material") {
          const filledRequisition = await this.fillMaterials(emptyRequisition);

          this.requisitions.push(filledRequisition);
        }
      });
    },

    async fillMaterials(emptyRequisition) {
      await this.materials.forEach((material) => {
        if (material.id == emptyRequisition.orderedItemId) {
          emptyRequisition.name = material.name;
        }
      });

      emptyRequisition.quantity = emptyRequisition.quantity.toString() + " KG";

      return emptyRequisition;
    },
  },
};
</script>