<template>
  <section
    class="account-section bg_img"
    style="background-image: url(/assets/images/account/account-bg.jpg)"
  >
    <div class="container">
      <div class="padding-top padding-bottom">
        <div class="padding-top padding-bottom">
          <div class="padding-top padding-bottom">
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  head() {
    return {
      title: 'Xác thực tài khoản'
    };
  },

  components: {},

  data() {
    return {
    };
  },

  async mounted() {
    $('.preloader').fadeIn(1);

    const {code} = this.$route.query;
    
    if (!code) {
      this.$swal.fire({
        'title': "Mã xác nhận không chính xác vui lòng thử lại!!",
        willClose: () => {
          this.$router.push('/signin')
        }
      });
    } else {
      const doVerify = await this.$store.dispatch(
        "auth/verify",
        {
          code,
        }
      );
      
      if (doVerify.errors || doVerify.message || !doVerify.success) {
        $('.preloader').fadeOut(100);
        setTimeout(() => {
          this.$swal.fire({
            'title': doVerify.errors && doVerify.errors.message || doVerify.message || 'Đã có lỗi xảy ra, vui lòng thử lại!',
            willClose: () => {
              this.$router.push('/signin')
            }
          });
        }, 150);
      } else {
        $('.preloader').fadeOut(100);
        this.$swal.fire({
          'title': "Xác thực tài khoản thành công, vui lòng đăng nhập!",
          willClose: () => {
            this.$router.push('/signin')
          }
        });
      }
    }
  },

  methods: {
  }
};
</script>

<style lang="scss" scoped>
@import '~/assets/scss/components/form/_form-auth.scss';
</style>
