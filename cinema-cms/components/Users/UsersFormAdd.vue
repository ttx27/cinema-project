<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Email">
          <a-input
            placeholder="Nhập email"
            v-decorator="['email', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { type: 'email', message: 'Hãy nhập đúng định dạng email.' },
                  { whitespace: true, message: 'Không được có khoảng trống!' }
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Mật khẩu">
          <a-input-password
            placeholder="Nhập mật khẩu"
            v-decorator="['password', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Họ tên">
          <a-input
            placeholder="Nhập họ tên"
            v-decorator="['fullName', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { pattern: /^[^`~!@#$%^&*()_+={}\[\]|\\:;“’<,>.?๐฿]*$/g, message: 'Không được chứa ký tự đặc biệt'}
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Trạng thái">
          <a-select
            showSearch
            placeholder="Chọn trạng thái"
            v-decorator="[
              'status',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
                initialValue: 1
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="1">Kích hoạt</a-select-option>
            <a-select-option :value="0">Không kích hoạt</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Phân quyền">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn quyền"
            v-decorator="[
              'roles',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option v-for="(item, index) in roleOpts" :key="index" :value="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item>
          <a-button type="primary" @click="submit" :loading="buttonLoading">Lưu</a-button>
          <a-button class="ml-3" type="" @click="cancel" :loading="buttonLoading">Hủy</a-button>
        </a-form-item>
      </a-col>
    </a-row>
  </a-form>
</template>

<script>
import moment from "moment";
import { mapGetters, mapState } from "vuex";

export default {
  data() {
    return {
      buttonLoading: false,
      searchFetching: false,
      form: this.$form.createForm(this, { name: "add" }),
    };
  },

  computed: {
    ...mapState('roles', ['roleOpts']),
  },

  methods: {
    cancel() {
      this.$emit("submitted");
    },

    submit(e) {
      e.preventDefault();
      this.form.validateFields(async (error, values) => {
        console.log("submit", values);
        if (!error) {
          this.buttonLoading = true;

          const doAdd = await this.$store.dispatch("users/add", {
            ...values,
          });

          if (doAdd.errors || doAdd.message) {
            this.$message.error((doAdd.errors && doAdd.errors.message) || doAdd.message);
          } else {
            this.$message.success("Thêm người dùng thành công!");
            this.$emit("submitted");
          }
        } else {
          this.$message.error("Xin hãy điền đầy đủ thông tin!");
        }
        this.buttonLoading = false;
      });
    },

    filterOption(input, option) {
      return (
        option.componentOptions.children[0].text
          .toUpperCase()
          .indexOf(input.toUpperCase()) >= 0
      );
    },
  }
};
</script>
