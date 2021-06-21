<template>
  <div class="blacktext">
    <v-card>
      <div class="content">
        <h1>Request Materials</h1>
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
              v-model="selectedQuantity"
              type="number"
              label="Quantity in kilograms"
            ></v-text-field>
          </v-col>
          <v-col cols="1"></v-col>
        </v-row>
        <v-divider></v-divider>
        <div class="content">
          <v-btn
            class="mt-10"
            style="width: 20%"
            color="primary"
            @click="requestMaterials()"
            >Place Order</v-btn
          >
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
}

.entry {
  padding-top: 10px;
  padding-bottom: 10px;
}

.content {
  padding: 1%;
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
  name: "ProductionMaterials",

  data() {
    return {
      materials: [],
      selectedMaterial: {},
      selectedQuantity: 0,
    };
  },

  async mounted() {
    await this.getMaterials();
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

    async requestMaterials() {
      const token = await this.$auth.getTokenSilently();

      console.log(this.selectedMaterial)

      const requisition = {
        OrderedItemType: 'Material',
        OrderedItemId: this.selectedMaterial.id,
        Quantity: this.selectedQuantity,
        Requester: this.$auth.user.sub,
        Department: 'Production'
      };

      await axios.post("http://localhost:3000/createrequisition", requisition, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });

      this.selectedMaterial = {};
      this.selectedQuantity = 0;
    },
  },
};
</script>