<template>
  <header class="header-section header-active">
    <div class="container">
      <div class="header-wrapper">
        <div class="logo">
          <nuxt-link to="/"
            ><img width="100%" src="/assets/images/logo/logo.jpg" alt="logo"
          /></nuxt-link>
        </div>
        <ul class="menu">
          <li>
            <nuxt-link to="/" :class="{ active: currentPath == 'index' }">Trang chủ</nuxt-link>
          </li>
          <li v-if="isAuthenticated && isStaff">
            <nuxt-link to="/print-ticket" :class="{ active: currentPath == 'print-ticket' }">In vé</nuxt-link>
          </li>
          <li v-if="!isAuthenticated" class="header-button pr-0">
            <nuxt-link to="/signin">Đăng nhập/Đăng ký</nuxt-link>
          </li>
          <li v-else>
            <nuxt-link to="/profile" :class="{ active: currentPath == 'profile' }">Hi, {{fullname}}</nuxt-link>
          </li>
          <li v-if="isAuthenticated" class="header-button pr-0">
            <nuxt-link to="#" @click.native="logout">Đăng xuất</nuxt-link>
          </li>
        </ul>
        <div class="header-bar d-lg-none">
          <span></span><span></span><span></span>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  computed: {
    fullname() {
      return this.$store.state.auth.token.fullname;
    },

    roles() {
      return this.$store.state.auth.token.userRole;
    },

    isStaff() {
      return this.roles && this.roles.filter(role => role.code == "STAFF").length > 0 || false;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    },

    currentPath() {
      return this.$route.name;
    }
  },

  methods: {
    logout() {
      console.log("ASDasdsaddas");
      this.$store.dispatch('auth/logout');
      this.$router.push('/signin');
    },
  }
};
</script>

<style lang="scss">
@import '~/assets/scss/components/_the-header.scss';
</style>
