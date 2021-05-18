<template>
  <div class="agency-table-groups">
    <h2>Đổi mật khẩu</h2>
    <a-form :form="form">
      <a-row v-for="(value, key) in data" :key="key" :gutter="24">
        <a-col :span="12">
          <a-form-item v-if="key === 'password_confirmation'" :label="value.title">
            <a-input
              type="password"
              v-decorator="[
                value.decorator[0],
                {
                  rules: [
                    ...value.decorator[1].rules,
                    {
                      validator: rePasswordValidator,
                      message: 'Mật khẩu xác nhận phải trùng với mật khẩu mới bạn đã điền'
                    }
                  ]
                }
              ]"
              :placeholder="`Nhập ${value.title}`"
            />
          </a-form-item>

          <a-form-item v-else :label="value.title">
            <a-input
              :type="key === 'password' || key === 'old_password' ? 'password' : 'text'"
              v-decorator="value.decorator"
              :placeholder="`Nhập ${value.title}`"
            />
          </a-form-item>
        </a-col>
      </a-row>

      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item>
            <a-button type="primary" @click="submit" :loading="buttonLoading">Lưu</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </div>
</template>

<script>
import { change_password as changePasswordValidation } from '~/utils/validations';

const data = {
  old_password: {
    title: 'Mật khẩu hiện tại',
    decorator: changePasswordValidation.old_password
  },
  password: {
    title: 'Mật khẩu mới',
    decorator: changePasswordValidation.password
  },
  password_confirmation: {
    title: 'Nhập lại mật khẩu mới',
    decorator: changePasswordValidation.password_confirmation
  }
};

export default {
  head() {
    return {
      title: 'Đặt lại mật khẩu'
    };
  },

  data() {
    return {
      data,
      buttonLoading: false,
      form: this.$form.createForm(this, { name: 'change-password' })
    };
  },

  created() {},

  methods: {
    submit(e) {
      e.preventDefault();
      this.form.validateFields(async (error, values) => {
        if (!error) {
          this.buttonLoading = true;
          const doChangePassword = await this.$store.dispatch('auth/changePassword', {
            old_password: values.old_password,
            new_password: values.password
          });

          if (doChangePassword.err_msg) {
            this.$message.error(doChangePassword.err_msg);
          } else {
            this.$message.success('Đổi mật khẩu thành công');
            this.form.resetFields();
          }
          this.buttonLoading = false;
        }
      });
    },

    rePasswordValidator(rule, value, cb) {
      const password = this.form.getFieldValue('password');
      if (password && password === value) return cb();
      return false;
    }
  }
};
</script>

<style lang="scss" scoped>
@import '~/assets/scss/components/form/_form-auth.scss';
</style>