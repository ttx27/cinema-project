<template>
  <div id="components-layout-demo-custom-trigger">
    <TheHeader />

    <nuxt />

    <TheFooter />
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import TheHeader from "~/components/TheHeader";
import TheFooter from "~/components/TheFooter";
import TheBreadcrumb from "~/components/TheBreadcrumb";

export default {
  middleware: "auth",

  components: {
    TheHeader,
    TheFooter,
    TheBreadcrumb
  },

  computed: {
    ...mapState("notifications", ["isSetToken"]),
  },

  data: () => ({
    collapsed: false
  }),
  
  mounted() {
  }
};
</script>

<style lang="scss">
// @import "~/assets/scss/layouts/_default.scss";
</style>