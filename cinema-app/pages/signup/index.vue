<template>
  <section
    class="account-section bg_img"
    style="background-image: url(/assets/images/account/account-bg.jpg)"
  >
    <div class="container">
      <div class="padding-top padding-bottom">
        <div class="account-area">
          <div class="section-header-3">
            <h2 class="title">Đăng ký tài khoản</h2>
          </div>
          <form class="account-form" @submit="submit">
            <div class="form-group">
              <label for="email1">Email<span>*</span></label
              ><input
                type="email"
                placeholder="Nhập địa chỉ Email"
                id="email1"
                ref="email"
                required
              />
            </div>

            <div class="form-group">
              <label for="pass2">Họ tên<span>*</span></label
              ><input
                type="text"
                placeholder="Nhập họ tên"
                id="pass2"
                ref="fullName"
                required
              />
            </div>

            <div class="form-group">
              <label for="pass1">Mật khẩu<span>*</span></label
              ><input
                type="password"
                placeholder="Nhập mật khẩu"
                id="pass1"
                ref="password"
                required
              />
            </div>

            <!-- <div class="form-group checkgroup">
              <input type="checkbox" id="bal" required checked /><label
                for="bal"
                >I agree to the <a href="#0">Terms, Privacy Policy</a>and
                <a href="#0">Fees</a></label
              >
            </div> -->
            <div class="form-group text-center">
              <input type="submit" value="Đăng ký tài khoản" />
            </div>
          </form>
          <div class="option">
            Bạn đã có tài khoản? <a href="/signin">Đăng nhập</a>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
};
const formTailLayout = {
  labelCol: { span: 7 },
  wrapperCol: { span: 24, offset: 0 }
};

export default {
  head() {
    return {
      title: 'Đăng ký tài khoản'
    };
  },

  components: {},

  data() {
    return {
      formItemLayout,
      formTailLayout
    };
  },

  methods: {
    async submit(e) {
      e.preventDefault();
      $('.preloader').fadeIn(50);

      console.log()

      const doRegister = await this.$store.dispatch(
        "auth/register",
        {
          email: this.$refs.email.value,
          fullName: this.$refs.fullName.value,
          password: this.$refs.password.value,
        }
      );
      
      if (doRegister.errors || doRegister.message || !doRegister.success) {
        $('.preloader').fadeOut(100);
        setTimeout(() => {
          this.$swal(doRegister.errors && doRegister.errors.message || doRegister.message || 'Đã có lỗi xảy ra, vui lòng thử lại!');
        }, 150);
      } else {
        $('.preloader').fadeOut(100);
        this.$swal.fire({
          'title': "Đăng ký tài khoản thành công, vui lòng kiểm tra email để xác thực tài khoản!",
          willClose: () => {
            this.$router.push('/signin')
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
