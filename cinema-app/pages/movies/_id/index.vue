<template>
  <div v-if="movie">
    <section class="details-banner bg_img" data-background="">
      <div class="container">
        <div class="details-banner-wrapper">
          <div class="details-banner-thumb">
            <img :src="movie.image" alt="movie" />
            <!-- <a
              href="https://www.youtube.com/embed/KGeBMAgc46E"
              class="video-popup"
              ><img src="/assets/images/movie/video-button.png" alt="movie"
            /></a> -->
          </div>
          <div class="details-banner-content offset-lg-3">
            <h3 class="title">{{ movie.name }}</h3>
            <div class="tags">
              <a
                v-for="language in movie.languages || []"
                :key="`language_${language.id}`"
                href="#"
                >{{ language.language }}</a
              >
              <a
                v-for="subtitle in movie.subtitles || []"
                :key="`subtitle_${subtitle.id}`"
                href="#"
                >{{ subtitle.subtitle }}</a
              >
            </div>
            <div class="categories" v-if="movie.categories">
              <a
                v-for="category in movie.categories"
                :key="category.id"
                href="#"
                class="button mr-2"
                >{{ category.name }}</a
              >
            </div>
            <div class="social-and-duration">
              <div class="duration-area">
                <div class="item">
                  <i class="fas fa-calendar-alt"></i
                  ><span>{{ movie.releaseDate }}</span>
                </div>
                <div class="item">
                  <i class="far fa-clock"></i
                  ><span>{{ movie.duration }} phút</span>
                </div>
                <div class="item">
                  <i class="fa fa-exclamation-circle"></i
                  ><span>{{ movie.rating }}</span>
                </div>
              </div>
              <ul class="social-share">
                <li>
                  <a href="#"><i class="fab fa-facebook-f"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fab fa-twitter"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fab fa-pinterest-p"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fab fa-linkedin-in"></i></a>
                </li>
                <li>
                  <a href="#"><i class="fab fa-google-plus-g"></i></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="book-section bg-one">
      <div class="container">
        <div class="book-wrapper offset-lg-3">
          <div class="left-side"></div>
          <nuxt-link :to="isAuthenticated ? `/movies/${movie.id}/booking` : `/signin?redirect=/movies/${movie.id}/booking`" class="custom-button">Đặt vé</nuxt-link>
        </div>
      </div>
    </section>
    <section class="movie-details-section padding-top padding-bottom">
      <div class="container">
        <div class="row justify-content-center flex-wrap-reverse mb--50">
          <!-- <div class="col-lg-3 col-sm-10 col-md-6 mb-50">
            <div class="widget-1 widget-tags">
              <ul>
                <li><a href="#">2D</a></li>
                <li><a href="#">imax 2D</a></li>
                <li><a href="#">4DX</a></li>
              </ul>
            </div>
          </div> -->
          <div class="col-lg-12 mb-50">
            <div class="movie-details">
              <div class="tab summery-review">
                <div class="item">
                  <div class="header">
                    <h5 class="sub-title">Nội dung</h5>
                  </div>
                  <div class="casting-slider">
                    <div class="cast-item text-left">
                      {{ movie.description }}
                    </div>
                  </div>
                </div>
                <div class="item">
                  <div class="header">
                    <h5 class="sub-title">Đạo diễn</h5>
                  </div>
                  <div class="casting-slider">
                    <div class="cast-item text-left">
                      <span
                        v-for="(director, index) in movie.directors"
                        :key="director.id"
                        >{{ director.fullName
                        }}{{
                          `${index + 1 < movie.directors.length ? ', ' : ''}`
                        }}</span
                      >
                    </div>
                  </div>
                </div>
                <div class="item">
                  <div class="header">
                    <h5 class="sub-title">Diễn viên</h5>
                  </div>
                  <div class="casting-slider">
                    <div class="cast-item text-left">
                      <span
                        v-for="(actor, index) in movie.actors"
                        :key="actor.id"
                        >{{ actor.fullName
                        }}{{
                          `${index + 1 < movie.actors.length ? ', ' : ''}`
                        }}</span
                      >
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

export default {
  head() {
    return {
      title: (this.movie && this.movie.name) || ''
    };
  },

  components: {},

  async fetch({ store, params }) {
    try {
      await Promise.all([store.dispatch('movies/get')]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false,
      movie: null
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('auth', ['token']),

    userRole() {
      //   return this.$store.state.auth.token.userRole;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    }
  },

  mounted() {
    if (this.movies) {
      const currentMovie = this.movies.filter(
        movie => movie.id == this.$route.params.id
      )[0];
      if (currentMovie && currentMovie.id) {
        this.movie = currentMovie;
      }
    }
  },

  methods: {
  }
};
</script>
