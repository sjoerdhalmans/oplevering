<template>
  <div style="height: 100%">
    <v-navigation-drawer expand-on-hover app clipped>
      <v-list nav dense>
        <v-list-item @click="$router.push('/logistics/overview')" link>
          <v-list-item-icon>
            <i class="fas fa-eye"></i>
          </v-list-item-icon>
          <v-list-item-title>Logistics Overview</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/logistics/orders')" link>
          <v-list-item-icon>
            <i class="far fa-paper-plane"></i>
          </v-list-item-icon>
          <v-list-item-title>Manage Orders</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/logistics/materials')" link>
          <v-list-item-icon> <i class="fas fa-truck"></i> </v-list-item-icon>
          <v-list-item-title>Order Materials</v-list-item-title>
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

i {
  line-height: 1.5 !important;
}
</style>

<script>
export default {
  name: "logistics",
};
</script>