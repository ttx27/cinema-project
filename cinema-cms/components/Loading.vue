<template lang="html">
  <div class="text-center" v-if="loading">
    <a-spin :spinning="loading"></a-spin>
  </div>
</template>

<script>
export default {
  data: () => ({
    loading: false
  }),
  methods: {
    start () {
      this.loading = true
    },
    finish () {
      this.loading = false
    }
  }
}
</script>