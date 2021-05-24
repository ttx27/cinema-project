<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item label="Phim">
          <a-select
            showSearch
            placeholder="Chọn phim để lọc phòng chiếu"
            v-decorator="[
              'movie',
            ]"
            style="width: 100%"
            :filter-option="filterOption"
            @change="handleChangeMovie"
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
        <a-form-item label="Phòng chiếu">
          <a-select
            showSearch
            placeholder="Chọn phòng chiếu"
            v-decorator="[
              'movieRoom',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in movieRoomOpts.filter(movieRoom => (!currentCinemaId || currentCinemaId == movieRoom.room.cinemaDetail.id) && (!currentMovieId || currentMovieId == movieRoom.movie.id))" :key="item.id">{{item.movie.name}} - {{item.room.name}} ({{item.room.cinemaDetail.name}})</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Ca chiếu">
          <a-select
            showSearch
            mode="multiple"
            placeholder="Chọn ca chiếu"
            v-decorator="[
              'shifts',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
              }
            ]"
            style="width: 100%"
            :filter-option="filterOption"
          >
            <a-select-option :value="item.id" v-for="item in movieShiftOpts" :key="item.id">{{item.name}} tiếng</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>

      <a-col :span="24">
        <a-form-item label="Thời gian chiếu">
          <a-date-picker
            style="width: 100%"
            :show-time="{ format: 'HH:mm' }"
            format="HH:mm DD/MM/YYYY"
            placeholder="Chọn thời gian chiếu"
            v-decorator="['movieDate', {
                rules: [
                  { required: true, message: 'Bắt buộc!' },
                ]
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
import { debounce } from "lodash";
import moment from "moment";
import { mapGetters, mapState } from "vuex";

export default {
  data() {
    return {
      buttonLoading: false,
      searchFetching: false,
      form: this.$form.createForm(this, { name: "add" }),
      currentCinemaId: null,
      currentMovieId: null,
    };
  },

  computed: {
    ...mapState('cinemas', ['cinemaOpts']),
    ...mapState('movieRooms', ['movieRoomOpts']),
    ...mapState('movies', ['movieOpts']),
    ...mapState('movieShifts', ['movieShiftOpts']),
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

          const doAdd = await this.$store.dispatch("showTimes/add", {
            ...values,
            movieDate: moment(values.movieDate).toISOString(),
            movieTime: moment(values.movieDate).format("HH:mm")
          });

          if (doAdd.errors || doAdd.message) {
            this.$message.error((doAdd.errors && doAdd.errors.message) || doAdd.message);
          } else {
            this.$message.success("Thêm lịch chiếu phim thành công!");
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
    },

    handleChangeMovie(movie) {
      this.currentMovieId = movie;
    }
  }
};
</script>
