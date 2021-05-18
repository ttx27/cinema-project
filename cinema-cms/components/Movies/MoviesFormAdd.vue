<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Poster phim">
          <a-upload-dragger
            name="file"
            class="thumb-uploader"
            listType="picture-card"
            :showUploadList="false"
            :action="`${baseURL}/api/v1/upload`"
            :headers="{
              Authorization: `${$store.state.auth.token.tokenType} ${$store.state.auth.token.accessToken}`
            }"
            accept=".jpg, .jpeg, .png"
            v-decorator="[
              'image',
              {
                initialValue: '',
                valuePropName: 'file',
                getValueFromEvent: normFile,
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ]
              }
            ]"
            @change="info => handleUploadChange(info)"
          >
            <template>
              <img v-if="thumbPreviewUrl" :src="thumbPreviewUrl" alt />
              <div v-else>
                <a-icon :type="thumbUploadLoading ? 'loading' : 'plus'" />
                <div class="ant-upload-text">
                  Tải lên (Chấp nhận file .jpg, .jpeg, .png)
                </div>
              </div>
            </template>
          </a-upload-dragger>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Tên phim">
          <a-input
            placeholder="Nhập tên phim"
            v-decorator="['name', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                  { pattern: /^[^`~!@#$%^&*()_+={}\[\]|\\:;“’<,>.?๐฿]*$/g, message: 'Không được chứa ký tự đặc biệt'}
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="12">
        <a-form-item label="Ngày khởi chiếu">
          <a-date-picker
            style="width: 100%"
            format="DD/MM/YYYY"
            placeholder="Chọn ngày khởi chiếu"
            v-decorator="['releaseDate', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="12">
        <a-form-item label="Độ dài (phút)">
          <a-input-number
            style="width: 100%"
            placeholder="Nhập độ dài"
            v-decorator="['duration', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Độ tuổi">
          <a-select
            showSearch
            placeholder="Chọn độ tuổi"
            v-decorator="[
              'rating',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item" v-for="(item, index) in ratings" :key="index">{{item}}</a-select-option>
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
                ]
              }]"
          />
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Thể loại">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn thể loại"
            v-decorator="[
              'categories',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in categoryOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Đạo diễn">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn đạo diễn"
            v-decorator="[
              'directors',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in directorOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Diễn viên">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn diễn viên"
            v-decorator="[
              'actors',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in actorOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Ngôn ngữ">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn ngôn ngữ"
            v-decorator="[
              'languages',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in languageOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Phụ đề">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn phụ đề"
            v-decorator="[
              'subtitles',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in subtitleOpts" :key="item.id">{{item.name}}</a-select-option>
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
import { getBase64 } from "~/utils/file-reader";
import { debounce } from "lodash";
import moment from "moment";
import { mapGetters, mapState } from "vuex";

export default {
  data() {
    return {
      baseURL: process.env.BASE_URL,
      thumbPreviewUrl: "",
      thumbUploadLoading: false,
      uploading: false,
      buttonLoading: false,
      searchFetching: false,
      form: this.$form.createForm(this, { name: "add" }),
    };
  },

  computed: {
    ...mapState('actors', ['actorOpts']),
    ...mapState('directors', ['directorOpts']),
    ...mapState('languages', ['languageOpts']),
    ...mapState('subtitles', ['subtitleOpts']),
    ...mapState('categories', ['categoryOpts']),
    ...mapState('config', ['ratings']),
  },

  methods: {
    handleUploadChange(info) {
      if (info.file.status === "uploading") {
        this.thumbUploadLoading = true
        return;
      }
      if (info.file.status === "done") {
        // Get this url from response in real world.
          getBase64(info.file.originFileObj, (imageUrl) => {
            this.thumbPreviewUrl = imageUrl;
            this.thumbUploadLoading = false;
          });
      }

      if (info.file.status === "error") {
        this.$message.error(info.file.error.message);
        this.thumbUploadLoading = false;
      }
    },

    normFile(e) {
      console.log("Upload event:", e.file.response);
      return e.file.response && e.file.response.payload ? `${this.baseURL}/${e.file.response.payload}` : "";
    },

    cancel() {
      this.$emit("submitted");
    },

    submit(e) {
      e.preventDefault();
      this.form.validateFields(async (error, values) => {
        console.log("submit", values);
        if (!error) {
          this.buttonLoading = true;

          const doAdd = await this.$store.dispatch("movies/add", {
            ...values,
            status: 1,
            releaseDate: moment(values.releaseDate).format('DD/MM/YYYY'),
          });

          if (doAdd.errors || doAdd.message) {
            this.$message.error((doAdd.errors && doAdd.errors.message) || doAdd.message);
          } else {
            this.$message.success("Thêm phim thành công!");
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
