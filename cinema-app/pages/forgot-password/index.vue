<template>
  <section
    class="account-section bg_img"
    style="background-image: url(/assets/images/account/account-bg.jpg)"
  >
    <div class="container">
      <div class="padding-top padding-bottom">
        <div class="account-area mt-4">
          <div class="section-header-3">
            <h2 class="title">Quên mật khẩu</h2>
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

            <div class="form-group text-center">
              <input type="submit" value="Gửi" />
            </div>
          </form>
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
      title: 'Quên mật khẩu'
    };
  },

  data() {
    return {
      buttonLoading: false,
    };
  },

  methods: {
    async submit(e) {
      e.preventDefault();
      $('.preloader').fadeIn(50);

      const doForgotPassword = await this.$store.dispatch(
        "auth/forgotPassword",
        {
          email: this.$refs.email.value,
        }
      );
      
      if (doForgotPassword.errors || doForgotPassword.message) {
        $('.preloader').fadeOut(100);
        setTimeout(() => {
          this.$swal(doForgotPassword.errors && doForgotPassword.errors.message || doForgotPassword.message);
        }, 150);
      } else {
        $('.preloader').fadeOut(100);
        this.$swal.fire({
          'title': "Gửi yêu cầu reset mật khẩu thành công, vui lòng kiểm tra email!",
          willClose: () => {
            this.$router.push('/signin');
          }
        });
      }
    },
  }
};
</script>

<style lang="scss" scoped>
@import '~/assets/scss/components/form/_form-auth.scss';
</style>
