<template>
  <div v-if="movie">
    <section
      class="details-banner hero-area bg_img seat-plan-banner"
      :style="`background-image: url(${movie.image});`"
    >
      <div class="container">
        <div class="details-banner-wrapper">
          <div class="details-banner-content style-two">
            <h3 class="title">{{ movie.name }}</h3>
            <div class="tags">
              <a
                v-for="language in movie.languages || []"
                :key="`language_${language.id}`"
                href="#"
              >
                {{ language.language }}
              </a>
              <a
                v-for="subtitle in movie.subtitles || []"
                :key="`subtitle_${subtitle.id}`"
                href="#"
              >
                {{ subtitle.subtitle }}
              </a>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="page-title bg-one">
      <div class="container">
        <div class="page-title-area">
          <div class="item md-order-1">
            <nuxt-link
              :to="`/movies/${movie.id}/booking`"
              class="custom-button back-button"
            >
              <i class="flaticon-double-right-arrows-angles"></i>Quay lại
            </nuxt-link>
          </div>
          <div class="item date-item" v-if="showTime">
            <p class="cinema">Rạp: {{showTime.movieRoom && showTime.movieRoom.roomDetail && showTime.movieRoom.roomDetail.cinemaDetail && showTime.movieRoom.roomDetail.cinemaDetail.name || ''}}</p>
            <p class="date">Suất chiếu: {{showTime.movieDate && moment(showTime.movieDate).format('HH:mm, DD/MM/YYYY') || ''}}</p>
            <p class="room">Phòng chiếu: {{showTime.movieRoom && showTime.movieRoom.roomDetail && showTime.movieRoom.roomDetail.name || ''}}</p>
          </div>
        </div>
      </div>
    </section>
    <div class="seat-plan-section padding-bottom padding-top">
      <div class="container">
        <div class="screen-area">
          <h4 class="screen">Màn hình</h4>
          <div class="screen-thumb">
            <img src="/assets/images/movie/screen-thumb.png" alt="movie" />
          </div>
          <div v-for="(seatType, index) in seatTypes" :key="index">
            <h5 class="subtitle">{{seatType.name}}</h5>
            <div class="screen-wrapper">
              <ul class="seat-area">
                <li class="seat-line" v-for="(seatRow, index) in Object.keys(seatType.seatRows)" :key="index">
                  <span>{{seatRow}}</span>
                  <ul class="seat--area">
                    <li class="front-seat">
                      <ul>
                        <li @click="seat.status ? handlePickSeat(seat) : null" v-for="(seat, index) in seatType.seatRows[seatRow]" :key="index" :class="`single-seat ${seat.status ? 'seat-free' : ''}`">
                          <img :src="seatPickeds.filter(seatPicked => seatPicked.id == seat.id).length > 0 ? '/assets/images/movie/seat01-booked.png' : '/assets/images/movie/seat01.png'" alt="seat" />
                          <span v-if="seat.status" class="sit-num">{{seat.name}}{{seat.number}}</span>
                          <span v-else class="sit-num">x</span>
                        </li>
                      </ul>
                    </li>
                  </ul>
                  <span>{{seatRow}}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div
          class="proceed-book bg_img"
          style="background-image: url(/assets/images/movie/movie-bg-proceed.html)"
        >
          <div class="proceed-to-book">
            <div class="book-item">
              <span>Ghế bạn đã chọn</span>
              <h3 class="title">{{seatPickeds.map(seatPicked => `${seatPicked.name}${seatPicked.number}`).join(', ')}}</h3>
            </div>
            <div class="book-item">
              <span>Tổng cộng</span>
              <h3 class="title">{{formatCurrency(seatPickeds.map(seatPicked => seatPicked.seatType && seatPicked.seatType.price).reduce((total, seatPicked) => total + seatPicked, 0))}}</h3>
            </div>
            <div class="book-item">
              <nuxt-link disabled to="#" @click.native="handleBookTicket" class="custom-button">Đặt vé</nuxt-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import { sortBy, uniqBy } from 'lodash';
import moment from 'moment';
import { formatCurrency } from '~/utils/number-format';

export default {
  head() {
    return {
      title: (this.movie && this.movie.name) || ''
    };
  },

  components: {},

  async fetch({ store, params }) {
    try {
      await Promise.all([
        store.dispatch('movies/get'),
        store.dispatch('seats/get'),
        store.dispatch('showTimes/get'),
        store.dispatch('showTimes/getAvailableSeats', {
          id: params.showTimeId
        }),
        store.dispatch('billDetails/get')
      ]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false,
      seatPickeds: [],
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('seats', ['seats']),
    ...mapState('showTimes', ['showTimes', 'showTimeSeats']),
    ...mapState('billDetails', ['billDetails']),
    ...mapState('auth', ['token']),

    userId() {
      return this.$store.state.auth.token.id;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    },

    movie() {
      const currentMovie = this.movies ? this.movies.filter(
        movie => movie.id == this.$route.params.id
      )[0] : null;
      return currentMovie
    },

    showTime() {
      const currentShowTime = this.showTimes ? this.showTimes.filter(
        showTime => showTime.id == this.$route.params.showTimeId
      )[0] : null;
      return currentShowTime
    },

    seatList() {
      const currentRoom = this.showTime && this.showTime.movieRoom && this.showTime.movieRoom.roomDetail || null;
      return this.seats ? sortBy(sortBy(this.seats, 'name'), 'number')
        .filter(seat => currentRoom && seat.room && currentRoom.id == seat.room.id)
        .map(seat => {
          const isEmpty = this.showTimeSeats.filter(item => item.id == seat.id).length > 0;
          
          if (!isEmpty) {
            seat.status = 0;
          } else {
            seat.status = 1;
          }

          return seat;
        }) : [];
    },

    seatTypes() {
      let seatTypes = this.seatList
        ? this.seatList.map(item => item.seatType)
        : [];

      seatTypes = uniqBy(seatTypes, 'id');

      for (let i = 0; i < seatTypes.length; i++) {
        const seatRows = {};

        for (let j = 0; j < this.seatList.length; j++) {
          const seat = { ...this.seatList[j] };

          if (seat.seatType && seat.seatType.id == seatTypes[i].id) {
            if (!seatRows[seat.name]) {
              seatRows[seat.name] = [seat];
            } else {
              seatRows[seat.name].push(seat);
            }
          }
        }

        seatTypes[i].seatRows = seatRows;
      }

      return seatTypes;
    },
  },

  mounted() {
  },

  updated() {},

  methods: {
    moment,
    formatCurrency,

    handlePickSeat(seat) {
      const seatPickeds = this.seatPickeds;
      const isPicked = seatPickeds.filter(seatPicked => seatPicked.id == seat.id).length > 0;

      if (isPicked) {
        this.seatPickeds = seatPickeds.filter(seatPicked => seatPicked.id != seat.id);
      } else {
        seatPickeds.push(seat);
        this.seatPickeds = seatPickeds;
      }
    },

    async handleBookTicket() {
      if (this.seatPickeds.length == 0) {
        this.$swal("Bạn cần chọn ít nhất một ghế!");
        return;
      }

      const bill = {
        total: this.seatPickeds.map(seatPicked => seatPicked.seatType && seatPicked.seatType.price).reduce((total, seatPicked) => total + seatPicked, 0),
        seats: this.seatPickeds.map(seat => seat.id),
        showTime: this.showTime.id,
        customer: this.userId
      }
      
      try {
        $('.preloader').fadeIn(50);

        const doAdd = await this.$store.dispatch(
          "bills/add",
          bill
        );
        
        if (doAdd.errors || doAdd.message) {
          $('.preloader').fadeOut(100);
          setTimeout(() => {
            this.$swal(doAdd.errors && doAdd.errors.message || doAdd.message);
          }, 150);
        } else {
          $('.preloader').fadeOut(100);
          if (doAdd.payload && doAdd.payload.id) {
            this.$router.push(`/movies/${this.movie.id}/booking/${this.showTime.id}/ticket/${doAdd.payload.id}`);
          }
        }
      } catch (error) {
        this.$swal("Đã có lỗi xảy ra!");
      }
    }
  }
};
</script>

<style>
.select-bar {
  background: transparent;
  margin-top: 15px;
}
.select-bar option {
  color: #000000;
}
</style>
