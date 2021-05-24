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
                ],
                initialValue: initialValues.email || null
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
                ],
                initialValue: ''
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
                ],
                initialValue: initialValues.fullName || null
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
                initialValue: Number(initialValues.status)
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
                initialValue: initialValues.roles && initialValues.roles.map(item => item.id) || []
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
import { mapState, mapGetters } from 'vuex';

export default {
  props: {
    initialValues: {
      type: Object,
      default: () => {},
      validator: value =>
        [
          'id',
        ].every(key => key in value)
    }
  },

  data() {
    return {
      buttonLoading: false,
      searchFetching: false,
      form: this.$form.createForm(this, { name: 'edit' }),
    };
  },

  computed: {
    ...mapState('roles', ['roleOpts']),
  },

  methods: {
    moment,
    
    cancel() {
      this.$emit("submitted");
    },

    submit(e) {
      e.preventDefault();
      this.form.validateFields(async (error, values) => {
        if (!error) {
          this.buttonLoading = true;

          const doEdit = await this.$store.dispatch('users/edit', {
            ...values,
            id: this.initialValues.id,
          });

          if (doEdit.errors || doEdit.message) {
            this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
          } else {
            this.$message.success("Cập nhật thông tin người dùng thành công!");
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
