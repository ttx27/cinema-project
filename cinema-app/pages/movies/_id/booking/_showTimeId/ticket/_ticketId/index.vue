<template>
  <div v-if="bill">
    <div class="movie-facility padding-bottom padding-top mt-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="booking-summery bg-one">
              <h4 class="title">Thanh toán</h4>
              <ul>
                <li>
                  <h6 class="subtitle">{{ movie.name }}</h6>
                  <span class="info">
                    {{
                      [
                        ...movie.languages.map(language => language.language),
                        ...movie.subtitles.map(subtitle => subtitle.subtitle)
                      ].join(', ')
                    }}
                  </span>
                </li>
                <li>
                  <h6 class="subtitle">
                    <span>
                      Rạp
                    </span>
                    <span>{{
                      (showTime.movieRoom &&
                        showTime.movieRoom.roomDetail &&
                        showTime.movieRoom.roomDetail.cinemaDetail &&
                        showTime.movieRoom.roomDetail.cinemaDetail.name) ||
                        ''
                    }}</span>
                  </h6>
                </li>
                <li>
                  <h6 class="subtitle">
                    <span>Mã hóa đơn</span>
                    <span>{{ bill.code }}</span>
                  </h6>
                </li>
              </ul>
              <ul class="side-shape">
                <li v-for="(billDetail, index) in billDetailList" :key="index">
                  <h6 class="subtitle">
                    <span>Loại vé</span
                    ><span>{{
                      (billDetail.seat &&
                        billDetail.seat.seatTypeDetail &&
                        billDetail.seat.seatTypeDetail.name) ||
                        ''
                    }}</span>
                  </h6>
                  <span class="info"
                    ><span>Giá vé</span
                    ><span>{{
                      formatCurrency(
                        (billDetail.seat &&
                          billDetail.seat.seatTypeDetail &&
                          billDetail.seat.seatTypeDetail.price) ||
                          0
                      )
                    }}</span></span
                  >
                  <span class="info"
                    ><span>Mã vé</span
                    ><span>{{ billDetail.ticketNumber }}</span></span
                  >
                </li>
              </ul>
              <ul>
                <li>
                  <span class="info"
                    ><span>Tổng giá vé</span
                    ><span>{{ formatCurrency(totalTicketPrice) }}</span></span
                  ><span class="info"
                    ><span>vat (10%)</span
                    ><span>{{
                      formatCurrency((totalTicketPrice * 10) / 100)
                    }}</span></span
                  >
                </li>
              </ul>
            </div>
            <div class="proceed-area  text-center">
              <p class="subtitle">
                <span>Số tiền cần thanh toán</span
                >
                <span>{{
                  formatCurrency(
                    totalTicketPrice + (totalTicketPrice * 10) / 100
                  )
                }}</span>
              </p>
              <p class="subtitle">
                <span>Số tiền hiện có</span
                >
                <span>{{
                  formatCurrency(
                    (userInfo.payment && userInfo.payment.amount) || 0
                  )
                }}</span>
              </p>
              <h6 class="subtitle">
                <span>Số tiền còn lại</span><span>{{
                  formatCurrency(
                    ((userInfo.payment && userInfo.payment.amount) || 0) - (totalTicketPrice + (totalTicketPrice * 10) / 100)
                  )
                }}</span>
              </h6>
              <button @click="handleCheckout" style="width: auto;" class="custom-button back-button">Thanh toán</button>
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
        store.dispatch('auth/me'),
        store.dispatch('seats/get'),
        store.dispatch('showTimes/get'),
        store.dispatch('billDetails/get'),
        store.dispatch('bills/get')
      ]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false,
      seatPickeds: []
    };
  },

  computed: {
    ...mapState('movies', ['movies']),
    ...mapState('seats', ['seats']),
    ...mapState('showTimes', ['showTimes']),
    ...mapState('billDetails', ['billDetails']),
    ...mapState('bills', ['bills']),
    ...mapState('auth', ['token', 'userInfo']),

    userId() {
      return this.$store.state.auth.token.id;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    },

    movie() {
      const currentMovie = this.movies
        ? this.movies.filter(movie => movie.id == this.$route.params.id)[0]
        : null;
      return currentMovie;
    },

    showTime() {
      const currentShowTime = this.showTimes
        ? this.showTimes.filter(
            showTime => showTime.id == this.$route.params.showTimeId
          )[0]
        : null;
      return currentShowTime;
    },

    bill() {
      const currentBill = this.bills
        ? this.bills.filter(bill => bill.id == this.$route.params.ticketId)[0]
        : null;

      // if (currentBill && currentBill.customer && currentBill.customer.id != this.userId) {
      //   return null;
      // }

      return currentBill;
    },

    billDetailList() {
      const billDetails = this.billDetails
        ? this.billDetails.filter(
            billDetail => billDetail.bill.id == this.$route.params.ticketId
          )
        : [];
      return billDetails;
    },

    totalTicketPrice() {
      return this.billDetailList
        .map(
          billDetail =>
            (billDetail.seat &&
              billDetail.seat.seatTypeDetail &&
              billDetail.seat.seatTypeDetail.price) ||
            0
        )
        .reduce((total, item) => total + item, 0);
    }
  },

  mounted() {},

  updated() {},

  methods: {
    moment,
    formatCurrency,

    async handleCheckout() {
      if (((this.userInfo.payment && this.userInfo.payment.amount) || 0) < (this.totalTicketPrice + (this.totalTicketPrice * 10) / 100)) {
        this.$swal.fire({
          'title': "Số dư tài khoản không đủ, vui lòng nạp thêm!",
          willClose: () => {
            this.$router.push('/payment');
          }
        });
        return;
      }
      
      try {
        $('.preloader').fadeIn(50);

        const doCheckout = await this.$store.dispatch(
          "bills/checkout",
          this.$route.params.ticketId
        );

        console.log(doCheckout)
        
        if (doCheckout.errors || doCheckout.message) {
          $('.preloader').fadeOut(100);
          setTimeout(() => {
            this.$swal(doCheckout.errors && doCheckout.errors.message || doCheckout.message);
          }, 150);
        } else {
          $('.preloader').fadeOut(100);
          if (doCheckout.status == 'OK') {
            this.$router.push(`/movies/${this.movie.id}/booking/${this.showTime.id}/ticket/${this.$route.params.ticketId}/checkout-success`);
          }
        }
      } catch (error) {
        this.$swal("Đã có lỗi xảy ra!");
      }
    }
  }
};
</script>
