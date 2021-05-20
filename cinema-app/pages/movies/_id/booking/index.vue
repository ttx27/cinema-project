<template>
  <div v-if="movie">
    <section
      class="details-banner hero-area bg_img"
      :style="`background-image: url(${movie.image});`"
    >
      <div class="container">
        <div class="details-banner-wrapper">
          <div class="details-banner-content">
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
    <section class="book-section bg-one">
      <div class="container">
        <form class="ticket-search-form two">
          <div class="form-group">
            <div class="thumb">
              <img src="/assets/images/ticket/city.png" alt="ticket" />
            </div>
            <span class="type">Thành phố</span>
            <select
              class="select-bar"
              name="currentCitySelected"
              @change="handleSelectedChange"
            >
              <option
                v-for="(city, index) in cities"
                :key="index"
                :value="city.id"
                >{{ city.city }}</option
              >
            </select>
          </div>
          <div class="form-group ml-5">
            <div class="thumb">
              <img src="/assets/images/ticket/date.png" alt="ticket" />
            </div>
            <span class="type">Ngày</span>
            <select
              class="select-bar"
              name="currentDateSelected"
              @change="handleSelectedChange"
            >
              <option
                v-for="(date, index) in dates"
                :key="index"
                :value="moment(date).toISOString()"
                >{{ moment(date).format('DD/MM/YYYY') }}</option
              >
            </select>
          </div>
        </form>
      </div>
    </section>
    <div class="ticket-plan-section padding-bottom padding-top">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-12 mb-5 mb-lg-0">
            <ul class="seat-plan-wrapper bg-five">
              <li v-for="(cinema, index) in cinemaList" :key="index">
                <div class="movie-name">
                  <a href="#0" class="name">{{ cinema.name }}</a>
                </div>
                <div class="movie-schedule" v-if="cinema.showTimes">
                  <nuxt-link :to="`/movies/${movie.id}/booking/${showTime.id}/ticket`" v-for="(showTime, index) in cinema.showTimes" :key="index" class="item">
                    {{moment(showTime.movieDate).format('HH:mm A')}}
                  </nuxt-link>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import { uniqBy } from 'lodash';
import moment from 'moment';

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
        store.dispatch('cinemas/get'),
        store.dispatch('rooms/get'),
        store.dispatch('showTimes/get')
      ]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false,
      movie: null,
      dates: [],
      currentCitySelected: null,
      currentDateSelected: null
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('cinemas', ['cinemas']),
    ...mapState('rooms', ['rooms']),
    ...mapState('showTimes', ['showTimes']),
    ...mapState('auth', ['token']),

    userRole() {
      //   return this.$store.state.auth.token.userRole;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    },

    cities() {
      const cities = this.cinemas
        ? this.cinemas.map(item => ({ id: item.cityId, city: item.city }))
        : [];

      return uniqBy(cities, 'id');
    },

    showTimeLists() {
      const currentDateSelected =
        this.currentDateSelected || moment().toISOString();

      const showTimes = this.showTimes
        ? this.showTimes.filter(
            item =>
              item.movieRoom &&
              item.movieRoom.movieDetail &&
              item.movieRoom.movieDetail.id == this.movie.id &&
              moment(item.movieDate).isSame(currentDateSelected, 'day') &&
              moment(item.movieDate).isAfter(moment())
          )
        : [];

      return showTimes;
    },

    cinemaList() {
      const currentCitySelected =
        this.currentCitySelected ||
        (this.cities && this.cities[0] ? this.cities[0].id : 0);

      const cinemas = this.cinemas
        ? this.cinemas.filter(
            item =>
              !currentCitySelected ||
              (item.cityId && item.cityId == currentCitySelected)
          )
        : [];

      return cinemas.map(cinema => {
        cinema.showTimes = this.showTimeLists.filter(
          showTime =>
            showTime.movieRoom &&
            showTime.movieRoom.roomDetail &&
            showTime.movieRoom.roomDetail.cinemaDetail &&
            showTime.movieRoom.roomDetail.cinemaDetail.id == cinema.id
        );

        return cinema;
      });
    }
  },

  watch: {
    movies: function() {
      if (this.movies) {
        const currentMovie = this.movies.filter(
          movie => movie.id == this.$route.params.id
        )[0];
        if (currentMovie && currentMovie.id) {
          this.movie = currentMovie;
        }
      }
    }
  },

  mounted() {
    const dates = [];
    let date = moment();
    const lastOfDate = moment(date).add(15, 'days');
    while (date <= lastOfDate) {
      dates.push(date.toDate());
      date = date.clone().add(1, 'days');
    }
    this.dates = dates;

    if (this.movies) {
      const currentMovie = this.movies.filter(
        movie => movie.id == this.$route.params.id
      )[0];
      if (currentMovie && currentMovie.id) {
        this.movie = currentMovie;
      }
    }
  },

  updated() {},

  methods: {
    moment,

    handleSelectedChange(e) {
      const { name, value } = e.target;

      if (name == 'currentCitySelected') {
        this.currentCitySelected = value;
      } else if (name == 'currentDateSelected') {
        this.currentDateSelected = value;
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
