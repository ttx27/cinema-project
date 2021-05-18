<template>
  <div class="viewapp">
    <nuxt />
  </div>
</template>

<script>
export default {
  head() {
    return {
      bodyAttrs: {
        class: 'viewapp-body'
      }
    }
  },
  
  mounted() {
  }
}
</script>

<style lang="scss">
@import "~/assets/scss/base/_view-app-base.scss";
@import "~/assets/scss/layouts/_viewapp.scss";
@import "~/assets/scss/components/viewapp/_app-tabs.scss";
@import "~/assets/scss/components/viewapp/_app-news-box.scss";
@import "~/assets/scss/components/viewapp/_app-buttons.scss";
</style>