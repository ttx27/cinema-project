<template>
  <div v-if="userInfo">
    <section class="details-banner bg_img" data-background="">
      <div class="container">
        <div class="details-banner-wrapper">
          <div class="details-banner-thumb">
            <img
              src="/assets/images/profile_avatar_placeholder.png"
              alt="profile"
            />
          </div>
          <div class="details-banner-content offset-lg-3">
            <h3 class="title">{{ userInfo.fullName }}</h3>
          </div>
        </div>
      </div>
    </section>
    <section class="book-section bg-one">
      <div class="container">
        <div class="book-wrapper offset-lg-3">
          <div class="left-side">
            <div class="item text-left">
              <p>
                Số dư:
                {{
                  formatCurrency(
                    (userInfo.payment && userInfo.payment.amount) || 0
                  )
                }}
              </p>
              <nuxt-link to="/payment" class="custom-button"
                >Nạp tiền</nuxt-link
              >
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="movie-details-section padding-top padding-bottom">
      <div class="container">
        <div class="row justify-content-center flex-wrap-reverse mb--50">
          <div class="col-lg-12 mb-50">
            <div class="movie-details">
              <div class="tab summery-review">
                <div class="item">
                  <div class="header">
                    <h5 class="sub-title">Danh sách vé đã đặt</h5>
                  </div>
                  <div class="casting-slider">
                    <div
                      class="movie-review-item"
                      v-for="(bill, index) in [...bills].sort((a, b) => new Date(b.createdDate).valueOf() - new Date(a.createdDate).valueOf())"
                      :key="index"
                    >
                      <div class="movie-review-content">
                        <div class="movie-review-info">
                          <h6 class="subtitle">
                            <a href="#0"
                              >Phim:
                              {{
                                get(
                                  bill,
                                  'showTime.movieRoomDetail.movieDetail.name'
                                )
                              }}</a
                            >
                          </h6>
                          <p class="reply-date my-1">
                            Ngày:
                            {{
                              moment(get(bill, 'showTime.movieDate')).format(
                                'HH:mm DD/MM/YYYY'
                              )
                            }}
                          </p>
                          <p class="reply-date my-1">
                            Mã hóa đơn:
                            {{bill.code}}
                          </p>
                          <p class="reply-date my-1">
                            Trạng thái:
                            <span :style="{color: bill.status == 0 ? 'red' : 'lime'}">
                              {{bill.status == 0 ? "Chưa thanh toán" : "Đã thanh toán"}}
                            </span>
                          </p>
                          <span> <i class="fas fa-check"></i>Rạp: {{
                                get(
                                  bill,
                                  'showTime.movieRoomDetail.roomDetail.cinemaDetail.name'
                                )
                              }}</span> | 
                          <span> <i class="fas fa-check"></i>Số vé: {{billDetails.filter(billDetail => billDetail.bill.id == bill.id).length}}</span> | 
                          <span> <i class="fas fa-check"></i>Tổng tiền: {{formatCurrency(bill.total || 0)}}</span> | 
                          <nuxt-link v-if="bill.status == 1" :to="`/ticket/${bill.id}`">Chi tiết</nuxt-link>
                          <nuxt-link v-else :to="`/movies/${bill.showTime.movieRoomDetail.movieDetail.id}/booking/${bill.showTime.id}/ticket/${bill.id}`">Thanh toán</nuxt-link>
                        </div>
                      </div>
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
import { get } from 'lodash';
import moment from 'moment';
import { formatCurrency } from '~/utils/number-format';

export default {
  head() {
    return {
      title: 'Trang cá nhân'
    };
  },

  components: {},

  async fetch({ store, params }) {
    try {
      await Promise.all([
        store.dispatch('auth/me'),
        store.dispatch('bills/get'),
        store.dispatch('billDetails/get')
      ]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false
    };
  },

  computed: {
    ...mapState('auth', ['token', 'userInfo']),
    ...mapState('bills', ['bills']),
    ...mapState('billDetails', ['billDetails']),

    // userInfo() {
    //   return this.$store.state.auth.token;
    // },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    }
  },

  mounted() {},

  methods: {
    moment,
    get,
    formatCurrency
  }
};
</script>
