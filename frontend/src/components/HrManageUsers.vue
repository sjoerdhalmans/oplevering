<template>
  <v-row>
    <v-col>
      <v-card :loading="loadingUsers" class="mx-auto" max-width="400" tile>
        <h3 class="blacktext">Users</h3>
        <v-divider></v-divider>
        <v-list-item-group>
          <div v-bind:key="user.nickname" v-for="user in users">
            <v-list-item @click="changeSelectedUser(user)" style="width: 100%">
              <v-list-item-content>
                <v-list-item-title>{{ user.email }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-divider></v-divider>
          </div>
        </v-list-item-group>
      </v-card>
    </v-col>
    <v-col>
      <v-card
        v-if="selectedUser.nickname != undefined"
        class="mx-auto"
        max-width="400"
        tile
        :loading="loadingRoles"
      >
        <h3 class="blacktext">Actions</h3>
        <v-divider></v-divider>
        <v-list-item-group v-if="loadingRoles == false">
          <v-list-item @click="changeSelectedAction('assignroles')">
            <v-list-item-content>
              <v-list-item-title>Add Roles</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-divider></v-divider>
          <v-list-item @click="changeSelectedAction('removeroles')">
            <v-list-item-content>
              <v-list-item-title>Remove Roles</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-card>
    </v-col>
    <v-col>
      <v-card v-if="selectedAction != ''" class="mx-auto" max-width="400" tile>
        <h3 class="blacktext">Roles</h3>
        <v-divider></v-divider>
        <v-list-item-group multiple active-class="" v-model="selectedRoles">
          <div v-bind:key="role.name" v-for="role in loadedActionRoles">
            <v-list-item>
              <template v-slot:default="{ active }">
                <v-list-item-content>
                  <v-list-item-title
                    v-text="role.name.split('_', 1)[0]"
                  ></v-list-item-title>
                </v-list-item-content>

                <v-checkbox :input-value="active"></v-checkbox>
              </template>
            </v-list-item>
            <v-divider></v-divider>
          </div>
        </v-list-item-group>
        <v-dialog v-model="dialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on" depressed> confirm </v-btn>
          </template>

          <v-card>
            <v-card-title class="headline grey lighten-2">
              Editing permissions
            </v-card-title>

            <v-card-text>
              Are you sure you want to edit permissions?
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-row>
                <v-btn color="primary" text @click="dialog = false"> No </v-btn>
                <v-btn color="primary" text @click="changePermissions()">
                  Yes
                </v-btn>
              </v-row>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-card>
    </v-col>
  </v-row>
</template>

<style scoped>
.blacktext {
  color: black;
  font-family: "PT Serif", serif;
  padding: 1%;
}

.v-btn {
  color: black;
  font-family: "PT Serif", serif;
  background-color: white !important;
  width: 100%;
}
</style>

<script>
import axios from "axios";
export default {
  name: "HrManageUsers",

  data() {
    return {
      loadingRoles: false,
      loadingUsers: false,
      allRoles: [],
      users: [],
      loadedActionRoles: [],
      selectedUserRoles: [],
      selectedUser: {},
      selectedAction: "",
      selectedRoles: [],
      dialog: false,
    };
  },

  async mounted() {
    this.loadingUsers = true;
    const token = await this.$auth.getTokenSilently();

    await this.getAllRoles();

    await axios
      .get("http://localhost:3000/getusers", {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      })
      .then((res) => {
        this.users = res.data;
        this.loadingUsers = false;
      });
  },

  methods: {
    async changePermissions() {
      this.dialog = false;
      if (this.selectedAction == "removeroles") {
        await this.removeRoles();
      } else if (this.selectedAction == "assignroles") {
        await this.assignRoles();
      }

      this.loadedActionRoles = [];
      this.selectedUserRoles = [];
      this.selectedUser = {};
      this.selectedAction = "";
      this.selectedRoles = [];
    },

    async assignRoles() {
      const token = await this.$auth.getTokenSilently();

      const calldata = {
        roles: [],
        userId: this.selectedUser.user_id,
      };

      await this.selectedRoles.forEach((roleindex) => {
        calldata.roles.push(this.loadedActionRoles[roleindex].id);
      });

      console.log(calldata);

      await axios
        .post("http://localhost:3000/assignroles", calldata, {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
    },

    async removeRoles() {
      const token = await this.$auth.getTokenSilently();

      const calldata = {
        roles: [],
        userId: this.selectedUser.user_id,
      };

      await this.selectedRoles.forEach((roleindex) => {
        calldata.roles.push(this.loadedActionRoles[roleindex].id);
      });

      await axios
        .delete("http://localhost:3000/removeroles", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
          data: calldata,
        })
    },

    async changeSelectedUser(user) {
      if (this.selectedUser == user) {
        this.selectedUser = {};
        this.selectedAction = "";
        this.selectedUserRoles = [];
      } else {
        this.selectedUser = user;
        this.selectedAction = "";
        this.selectedUserRoles = await this.getUserRoles();
        this.loadingRoles = false;
      }
    },

    changeSelectedAction(action) {
      if (this.selectedAction == action) {
        this.selectedAction = "";
        this.loadedActionRoles = [];
      } else {
        this.selectedAction = action;
        this.updateRoles(action);
      }
    },

    async updateRoles(action) {
      console.log(this.allRoles)
      console.log(this.loadedActionRoles)
      if (action == "removeroles") {
        this.loadedActionRoles = this.selectedUserRoles;
      } else if (action == "assignroles") {
        this.loadedActionRoles = this.allRoles.filter((item) =>
          this.selectedUserRoles.every((item2) => item2.name != item.name)
        );
      }
    },

    async getAllRoles() {
      const token = await this.$auth.getTokenSilently();

      await axios
        .get("http://localhost:3000/getallroles", {
          headers: {
            Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
          },
        })
        .then((res) => {
          this.allRoles = res.data;
        });
    },

    async getUserRoles() {
      this.loadingRoles = true;
      const token = await this.$auth.getTokenSilently();

      let roles;

      await axios
        .get(
          "http://localhost:3000/getuserroles/" + this.selectedUser.user_id,
          {
            headers: {
              Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
            },
          }
        )
        .then((res) => {
          roles = res.data;
        });

      return roles;
    },
  },
};
</script>