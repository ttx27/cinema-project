<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Họ tên">
          <a-input
            placeholder="Nhập họ tên"
            v-decorator="['fullName', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { pattern: /^[^`~!@#$%^&*()_+={}\[\]|\\:;“’<,>.?๐฿]*$/g, message: 'Không được chứa ký tự đặc biệt'}
                ],
                initialValue: initialValues.fullName
              }]"
          />
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
import { debounce } from 'lodash';
import { mapState, mapGetters } from 'vuex';

export default {
  props: {
    initialValues: {
      type: Object,
      default: () => {},
      validator: value =>
        [
          'id',
          'fullName',
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
  },

  methods: {
    cancel() {
      this.$emit("submitted");
    },

    submit(e) {
      e.preventDefault();
      this.form.validateFields(async (error, values) => {
        if (!error) {
          this.buttonLoading = true;

          const doEdit = await this.$store.dispatch('directors/edit', {
            ...values,
            id: this.initialValues.id,
          });

          if (doEdit.errors || doEdit.message) {
            this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
          } else {
            this.$message.success("Cập nhật thông tin đạo diễn thành công!");
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
