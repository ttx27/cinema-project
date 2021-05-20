<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Tên">
          <a-input
            placeholder="Nhập tên"
            v-decorator="['name', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { pattern: /^[^`~!@#$%^&*()_+={}\[\]|\\:;“’<,>.?๐฿]*$/g, message: 'Không được chứa ký tự đặc biệt'}
                ],
                initialValue: initialValues.name
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Số điện thoại">
          <a-input
            placeholder="Nhập số điện thoại"
            v-decorator="['phone', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { whitespace: true, message: 'Không được có ký tự trống ở đầu hoặc cuối input' },
                  { pattern: /^(84|0[3|5|7|8|9])+([0-9]{8})\b/g, message: 'Nhập sai định dạng số điện thoại'}
                ],
                initialValue: initialValues.phone
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Địa chỉ">
          <a-input
            placeholder="Nhập địa chỉ"
            v-decorator="['address', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ],
                initialValue: initialValues.address
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Thành phố">
          <a-select
            showSearch
            placeholder="Chọn thành phố"
            v-decorator="[
              'cityId',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
                initialValue: initialValues.cityId
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in cities" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Mô tả">
          <a-textarea
            placeholder="Nhập mô tả"
            v-decorator="['description', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ],
                initialValue: initialValues.description
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
          'name',
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
    ...mapState('config', ['cities'])
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

          const city = this.cities.filter(city => city.id == values.cityId)[0];

          const doEdit = await this.$store.dispatch('cinemas/edit', {
            ...values,
            city: city && city.name,
            id: this.initialValues.id,
          });

          if (doEdit.errors || doEdit.message) {
            this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
          } else {
            this.$message.success("Cập nhật thông tin rạp thành công!");
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
