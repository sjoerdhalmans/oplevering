<template>
  <div style="height: 100%">
    <v-navigation-drawer expand-on-hover app clipped>
      <v-list nav dense>
        <v-list-item @click="$router.push('/hr/users')" link>
          <v-list-item-icon>
            <v-icon>mdi-account-multiple</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Manage Users</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-content>
      <router-view></router-view>
    </v-content>
  </div>
</template>

<style scoped>
.v-content {
  height: 100%;
  padding: 2% !important;
}

.sidebar {
  height: 100%;
}
</style>

<script>
export default {
  name: "humanresources",
};
</script>