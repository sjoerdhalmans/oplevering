<template>
  <div style="height: 100%">
    <v-navigation-drawer expand-on-hover app clipped>
      <v-list nav dense>
        <v-list-item @click="$router.push('/production/overview')" link>
          <v-list-item-icon>
            <i class="fas fa-clipboard-list"></i>
          </v-list-item-icon>
          <v-list-item-title>Production Overview</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/production/statistics')" link>
          <v-list-item-icon>
            <i class="fas fa-industry"></i>
          </v-list-item-icon>
          <v-list-item-title>Manage Production</v-list-item-title>
        </v-list-item>
        <v-list-item @click="$router.push('/production/materials')" link>
          <v-list-item-icon>
            <i class="fas fa-truck"></i>
          </v-list-item-icon>
          <v-list-item-title>Request Materials</v-list-item-title>
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
  name: "production",
};
</script>