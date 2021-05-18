<template>
  <a-layout>
    <TheHeader />
    
    <a-layout-content class="page-content">
      <nuxt />
    </a-layout-content>
    
    <TheFooter />
  </a-layout>
</template>

<script>
import TheHeader from "~/components/TheHeader"
import TheFooter from "~/components/TheFooter"

export default {
  components: { TheHeader, TheFooter },
  
  mounted() {
  }
}
</script>

<style lang="scss" scoped>
@import "~/assets/scss/layouts/_auth.scss";
</style>