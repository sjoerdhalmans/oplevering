<template>
  <div>
    <v-row class="ml-5 mr-5 departmentrow justify-center">
      <v-col
        v-bind:key="department.name"
        v-for="department in departments"
        sm="12"
        lg="2"
        class="departmentcol"
      >
        <div v-if="checkAuth(department.permittedRoles)" class="departmentdiv">
          <div class="departmentcontent">
            <h1>{{ department.name }}</h1>
            <div>
              <i class="fas fa-lock-open mt-5" style="font-size: 3em"></i>
            </div>
            <v-btn
              class="departmentbutton"
              color="primary"
              @click="$router.push(department.page)"
              >Enter</v-btn
            >
          </div>
        </div>
        <div v-if="!checkAuth(department.permittedRoles)" class="departmentdiv">
          <div class="lockeddepartmentcontent">
            <h1>{{ department.name }}</h1>
            <i class="fas fa-lock mt-5" style="font-size: 3em"></i>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
.departmentbutton {
  margin-top: 140%;
}

.departmentdiv {
  background-color: white;
  height: 600px;
  border-style: solid;
  border-width: 1px;
  text-align: center;
  position: relative;
}

.departmentrow {
  margin-top: 5%;
}

.departmentcontent {
  margin: 5%;
  height: 100%;
}

.lockeddepartmentcontent {
  margin: 5%;
  height: 100%;
  opacity: 50%;
}

.blackedout {
  background-color: gray;
  height: 100%;
  z-index: 5;
  position: absolute;
}
</style>

<script>
export default {
  name: "DepartmentOverview",
  data: () => ({
    userRoles: [],

    departments: [
      {
        name: "HR",
        page: "hr",
        permittedRoles: ["HR_Productio", "Management_Productio"],
      },
      {
        name: "Logistics",
        page: "logistics",
        permittedRoles: ["Logistics_Productio", "Management_Productio"],
      },
      {
        name: "Production",
        page: "production",
        permittedRoles: ["Production_Productio", "Management_Productio"],
      },
      {
        name: "Requisitions",
        page: "requisitions",
        permittedRoles: ["Requisitions_Productio", "Management_Productio"],
      },
      {
        name: "Sales",
        page: "sales",
        permittedRoles: ["Sales_Productio", "Management_Productio"],
      },
    ],
  }),

  async mounted() {
    console.log(await this.$auth.getTokenSilently()); 
    this.userRoles = await this.$auth.user["http://Productio.net/roles"];
  },

  methods: {
    checkAuth(permittedRoles) {
      let allowed = false;
      this.userRoles.forEach((role) => {
        permittedRoles.forEach((permittedRole) => {
          if (role == permittedRole) {
            allowed = true;
          }
        });
      });
      return allowed;
    },
  },
};
</script>
