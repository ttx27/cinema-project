<template>
  <a-layout-header class="the-header" style="background: #fff; padding: 0">
    <a-menu theme="light" mode="horizontal" :selectable="false" :style="{ lineHeight: '64px' }">
      <slot />
      
      <!-- User -->
      <a-menu-item v-if="isAuthenticated" class="the-header__user">
        <a-dropdown>
          <a class="ant-dropdown-link" href="#">
            <a-icon type="user" class="mr-2" />
            Hi, {{ username }}
            <!-- (<span style="text-transform: capitalize;">{{roles[0]}}</span>) -->
            <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href @click.prevent="logout">
                <a-icon type="logout" class="mr-2" />Đăng xuất
              </a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
</template>

<script>
export default {
  computed: {
    username() {
      return this.$store.state.auth.token.fullname;
    },

    roles() {
      return this.$store.state.auth.token.roles;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated']
    }
  },

  methods: {
    async logout() {
      this.$store.dispatch('auth/signout');
      window.location.href = "/login";
    }
  }
};
</script>

<style lang="scss">
@import "~/assets/scss/components/_the-header.scss";
</style>