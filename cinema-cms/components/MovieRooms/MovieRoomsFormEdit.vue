<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Phim">
          <a-select
            showSearch
            placeholder="Chọn phim"
            v-decorator="[
              'movie',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
                initialValue: initialValues.movie && initialValues.movie.id || initialValues.movie || null
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in movieOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Rạp">
          <a-select
            showSearch
            placeholder="Chọn rạp để lọc phòng chiếu"
            v-decorator="[
              'cinema',
              {
                initialValue: initialValues.room && initialValues.room.cinemaDetail && initialValues.room.cinemaDetail.id || initialValues.room.cinemaDetail || null
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
            @change="handleChangeCinema"
          >
            <a-select-option :value="item.id" v-for="item in cinemaOpts" :key="item.id">{{item.name}}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Phòng">
          <a-select
            showSearch
            placeholder="Chọn phòng"
            v-decorator="[
              'room',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
                initialValue: initialValues.room && initialValues.room.id || initialValues.room || null
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in roomOpts.filter(room => !currentCinemaId || currentCinemaId == room.cinema.id)" :key="item.id">{{item.name}} ({{item.cinema.name}})</a-select-option>
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
      currentCinemaId: this.initialValues.room && this.initialValues.room.cinemaDetail && this.initialValues.room.cinemaDetail.id || this.initialValues.room.cinemaDetail || null,
    };
  },

  computed: {
    ...mapState('cinemas', ['cinemaOpts']),
    ...mapState('rooms', ['roomOpts']),
    ...mapState('movies', ['movieOpts']),
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

          const doEdit = await this.$store.dispatch('movieRooms/edit', {
            ...values,
            id: this.initialValues.id,
          });

          if (doEdit.errors || doEdit.message) {
            this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
          } else {
            this.$message.success("Cập nhật thông tin phòng chiếu phim thành công!");
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

    handleChangeCinema(cinema) {
      this.currentCinemaId = cinema;
    }
  }
};
</script>
