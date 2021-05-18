<template>
  <a-form :form="form">
    <a-row :gutter="24">
      <!-- <a-col :span="24">
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
      </a-col> -->

      <a-col :span="24">
        <a-form-item label="Phim">
          <a-select
            showSearch
            placeholder="Chọn phim để lọc phòng chiếu"
            v-decorator="[
              'movie',
              {
                initialValue: initialValues.movieRoom && initialValues.movieRoom.roomDetail && initialValues.movieRoom.roomDetail.cinemaDetail && initialValues.movieRoom.roomDetail.cinemaDetail.id || initialValues.movieRoom.roomDetail.cinemaDetail || null,
              }
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
              {
                initialValue: initialValues.movieRoom && initialValues.movieRoom.movieDetail && initialValues.movieRoom.movieDetail.id || initialValues.movieRoom.movieDetail || null,
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
        <a-form-item label="Phòng chiếu">
          <a-select
            showSearch
            placeholder="Chọn phòng chiếu"
            v-decorator="[
              'movieRoom',
              {
                rules: [{ required: true, message: 'Bắt buộc!' }],
                initialValue: initialValues.movieRoom && initialValues.movieRoom.id || initialValues.movieRoom || null,
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
                initialValue: initialValues.shifts && initialValues.shifts.map(item => item.id) || [],
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
                ],
                initialValue: initialValues.movieDate && moment(initialValues.movieDate) || null,
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
      currentCinemaId: this.initialValues.movieRoom && this.initialValues.movieRoom.roomDetail && this.initialValues.movieRoom.roomDetail.cinemaDetail && this.initialValues.movieRoom.roomDetail.cinemaDetail.id || this.initialValues.movieRoom.roomDetail.cinemaDetail || null,
      currentMovieId: this.initialValues.movieRoom && this.initialValues.movieRoom.movieDetail && this.initialValues.movieRoom.movieDetail.id || this.initialValues.movieRoom.movieDetail || null,
    };
  },

  computed: {
    ...mapState('cinemas', ['cinemaOpts']),
    ...mapState('movieRooms', ['movieRoomOpts']),
    ...mapState('movies', ['movieOpts']),
    ...mapState('movieShifts', ['movieShiftOpts']),
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

          const doEdit = await this.$store.dispatch('showTimes/edit', {
            ...values,
            movieDate: moment(values.movieDate).toISOString(),
            movieTime: moment(values.movieDate).format("HH:mm"),
            id: this.initialValues.id,
          });

          if (doEdit.errors || doEdit.message) {
            this.$message.error((doEdit.errors && doEdit.errors.message) || doEdit.message);
          } else {
            this.$message.success("Cập nhật thông tin lịch chiếu phim thành công!");
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
