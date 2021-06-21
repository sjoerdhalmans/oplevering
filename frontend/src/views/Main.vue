<template>
  <div>
    <h1>test</h1>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component({
  components: {
  }
})
export default class Main extends Vue {
  login() {
    this.$auth.loginWithRedirect({});
  }

  // Log the user out
  logout() {
    this.$auth.logout({
      returnTo: window.location.origin
    });
  }
}
</script>
