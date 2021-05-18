<template>
  <section
    class="account-section bg_img"
    style="background-image: url(/assets/images/account/account-bg.jpg)"
  >
    <div class="container">
      <div class="padding-top padding-bottom">
        <div class="account-area mt-4">
          <div class="section-header-3">
            <h2 class="title">Đăng nhập</h2>
          </div>
          <form class="account-form" @submit="submit">
            <div class="form-group">
              <label for="email2">Email<span>*</span></label
              ><input
                type="email"
                placeholder="Nhập địa chỉ Email"
                ref="email"
                id="email2"
                required
              />
            </div>

            <div class="form-group">
              <label for="pass3">Mật khẩu<span>*</span></label
              ><input
                type="password"
                placeholder="Nhập mật khẩu"
                ref="password"
                id="pass3"
                required
              />
            </div>

            <div class="form-group checkgroup">
              <nuxt-link to="/forgot-password" class="forget-pass ml-auto">Quên mật khẩu</nuxt-link>
            </div>
            <div class="form-group text-center">
              <input type="submit" value="Đăng nhập" />
            </div>
          </form>
          <div class="option">
            Chưa có tài khoản? <nuxt-link to="/signup">Đăng ký tài khoản</nuxt-link>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import form from '~/node_modules/ant-design-vue/lib/form';
export default {
  head() {
    return {
      title: 'Đăng nhập'
    };
  },

  data() {
    return {
      buttonLoading: false
    };
  },

  methods: {
    async submit(e) {
      e.preventDefault();
      $('.preloader').fadeIn(50);

      const doLogin = await this.$store.dispatch(
        "auth/login",
        {
          email: this.$refs.email.value,
          password: this.$refs.password.value,
        }
      );
      
      if (doLogin.errors || doLogin.message) {
        $('.preloader').fadeOut(100);
        setTimeout(() => {
          this.$swal(doLogin.errors && doLogin.errors.message || doLogin.message);
        }, 150);
      } else {
        $('.preloader').fadeOut(100);
        if (this.$route.query && this.$route.query.redirect) {
          this.$router.push(this.$route.query.redirect);
        } else {
          this.$router.push('/')
        }
      }
    },
  }
};
</script>

<style lang="scss" scoped>
@import '~/assets/scss/components/form/_form-auth.scss';
</style>
