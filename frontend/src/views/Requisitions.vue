<template>
  <div style="height: 100%">
    <v-navigation-drawer expand-on-hover app clipped>
      <v-list nav dense>
        <v-list-item @click="$router.push('/requisitions/inventory')" link>
          <v-list-item-icon>
            <i class="fas fa-boxes"></i>
          </v-list-item-icon>
          <v-list-item-title>View Inventory</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/requisitions/createorder')" link>
          <v-list-item-icon>
            <i class="far fa-paper-plane"></i>
          </v-list-item-icon>
          <v-list-item-title>Create Order</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/requisitions/overview')" link>
          <v-list-item-icon>
            <i class="fas fa-folder"></i>
          </v-list-item-icon>
          <v-list-item-title>View Requisitions</v-list-item-title>
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
  name: "requisitions",
};
</script>