<template>
  <a-layout id="components-layout-demo-custom-trigger">
    <TheSider collapsible :collapsed="collapsed" />
    <a-layout>
      <TheHeader>
        <a-icon
          class="trigger"
          :type="collapsed ? 'menu-unfold' : 'menu-fold'"
          @click="()=> collapsed = !collapsed"
        />
      </TheHeader>

      <TheBreadcrumb />

      <a-layout-content
        :style="{ margin: '0 16px 24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }"
      >
        <nuxt />
      </a-layout-content>
      <TheFooter />
    </a-layout>
  </a-layout>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import TheHeader from "~/components/TheHeader";
import TheSider from "~/components/TheSider";
import TheFooter from "~/components/TheFooter";
import TheBreadcrumb from "~/components/TheBreadcrumb";

export default {
  middleware: "auth",

  components: {
    TheHeader,
    TheSider,
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
@import "~/assets/scss/layouts/_default.scss";
</style>