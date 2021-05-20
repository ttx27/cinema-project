<template>
  <a-form :form="form" class="form-auth d-box" @submit.prevent="submit">
    <a-row>
      <a-col :span="formTailLayout.wrapperCol.span" :push="formTailLayout.wrapperCol.offset">
        <h1 class="form-auth__title">
          <img class="form-auth__logo" src="~/assets/images/logo.jpg" alt="">
        </h1>
      </a-col>
    </a-row>

    <a-form-item
      :label-col="formItemLayout.labelCol"
      :wrapper-col="formItemLayout.wrapperCol"
      label="Tài khoản"
    >
      <a-input v-decorator="emailDecorator" placeholder="Tài khoản" />
    </a-form-item>
    <a-form-item
      :label-col="formItemLayout.labelCol"
      :wrapper-col="formItemLayout.wrapperCol"
      label="Mật khẩu"
    >
      <a-input type="password" v-decorator="passwordDecorator" placeholder="Mật khẩu" />
    </a-form-item>

    <!-- <a-form-item :label-col="formTailLayout.labelCol" :wrapper-col="formTailLayout.wrapperCol">
      <a-checkbox :checked="checkRemember" @change="handleChange">Ghi nhớ tôi</a-checkbox>
    </a-form-item> -->

    <a-form-item :label-col="formTailLayout.labelCol" :wrapper-col="formTailLayout.wrapperCol">
      <a-button class="mr-3" htmlType="submit" type="primary" :loading="buttonLoading">Đăng nhập</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 7 },
  wrapperCol: { span: 17 }
};
const formTailLayout = {
  labelCol: { span: 7 },
  wrapperCol: { span: 17, offset: 7 }
};

export default {
  middleware: "guest",

  layout: "auth",

  head() {
    return {
      title: "Đăng nhập"
    };
  },

  data() {
    return {
      checkRemember: false,
      formItemLayout,
      formTailLayout,
      buttonLoading: false,
      form: this.$form.createForm(this, { name: "login" }),
      emailDecorator: [
        "email",
        {
          rules: [
            { required: true, message: "Bắt buộc!" },
            { type: 'email', message: 'Hãy nhập đúng định dạng email.' },
            { whitespace: true, message: "Không được có khoảng trống!" }
          ]
        }
      ],
      passwordDecorator: [
        "password",
        {
          rules: [
            { required: true, message: "Bắt buộc!" },
            { whitespace: true, message: "Không được có khoảng trống!" },
            {
              min: 6,
              message: "Ít nhất 6 ký tự!"
            }
          ]
        }
      ]
    };
  },

  methods: {
    async submit() {
      this.buttonLoading = true;

      this.form.validateFields(async (err) => {
        if (!err) {
          try {
            const doLogin = await this.$store.dispatch(
              "auth/signin",
              this.form.getFieldsValue()
            );

            if (doLogin.success) {
              this.buttonLoading = false;
              this.$message.success("Logged in successfully!");
              this.$router.push("/");
            } else {
              this.buttonLoading = false;
              doLogin.message
                ? this.$message.error(doLogin.message)
                : this.$message.error("Đã có lỗi xảy ra vui lòng thử lại sau!");
            }
          } catch (error) {
            this.buttonLoading = false;
            this.$message.error("Đã có lỗi xảy ra vui lòng thử lại sau!");
          }
        }
      });

      this.buttonLoading = false;
    },

    handleChange(e) {
      this.checkRemember = e.target.checked;
    }
  }
};
</script>

<style lang="scss" scoped>
@import "~/assets/scss/components/form/_form-auth.scss";
</style>