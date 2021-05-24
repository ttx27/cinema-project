<template>
  <div>
    <section class="mt-5 movie-section padding-top padding-bottom bg-two">
      <div class="container">
        <div class="row flex-wrap-reverse justify-content-center">
          <div class="col-lg-12">
            <div class="article-section padding-bottom">
              <div class="section-header-1">
                <h2 class="title">Phim đang chiếu</h2>
              </div>
              <div class="row mb-30-none justify-content-start">
                <div
                  v-for="(movie, index) in movies"
                  :key="index"
                  class="col-sm-6 col-lg-3"
                >
                  <div class="movie-grid">
                    <div class="movie-thumb c-thumb">
                      <nuxt-link :to="`/movies/${movie.id}`"><img :src="movie.image" alt="movie"/></nuxt-link>
                    </div>
                    <div class="movie-content bg-one">
                      <h5 class="title m-0"><nuxt-link :to="`/movies/${movie.id}`">{{movie.name}}</nuxt-link></h5>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import { mapKeys } from 'lodash';
import moment from 'moment';

import { DATE_DISPLAY_FORMAT } from '~/utils/config';

export default {
  head() {
    return {
      title: 'Trang chủ'
    };
  },

  components: {},

  async fetch({ store }) {
    try {
      await Promise.all([store.dispatch('movies/get')]);
    } catch (error) {}
  },

  data() {
    return {
      DATE_DISPLAY_FORMAT,
      baseUrl: process.env.BASE_URL
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('auth', ['token']),

    userRole() {
      // return this.$store.state.auth.token.userRole;
    }
  },

  mounted() {},

  methods: {
    moment
  }
};
</script>

<style></style>
