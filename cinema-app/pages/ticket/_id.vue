<template>
  <div v-if="bill">
    <div class="movie-facility padding-bottom padding-top mt-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="booking-summery bg-one">
              <h4 class="title">Thông tin vé</h4>
              <ul>
                <li>
                  <h6 class="subtitle">{{ (bill.showTime && bill.showTime.movieRoomDetail &&
                        bill.showTime.movieRoomDetail.movieDetail &&
                        bill.showTime.movieRoomDetail.movieDetail.name) ||
                        '' }}</h6>
                  <span class="info">
                    {{
                      [
                        ...(bill.showTime && bill.showTime.movieRoomDetail &&
                        bill.showTime.movieRoomDetail.movieDetail &&
                        bill.showTime.movieRoomDetail.movieDetail.languages || []).map(language => language.language),
                        ...(bill.showTime && bill.showTime.movieRoomDetail &&
                        bill.showTime.movieRoomDetail.movieDetail &&
                        bill.showTime.movieRoomDetail.movieDetail.subtitles || []).map(subtitle => subtitle.subtitle)
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
                      (bill.showTime && bill.showTime.movieRoomDetail &&
                        bill.showTime.movieRoomDetail.roomDetail &&
                        bill.showTime.movieRoomDetail.roomDetail.cinemaDetail &&
                        bill.showTime.movieRoomDetail.roomDetail.cinemaDetail.name) ||
                        ''
                    }}</span>
                  </h6>
                </li>
                <li>
                  <h6 class="subtitle">
                    <span>Mã hóa đơn</span>
                    <span>{{bill.code}}</span>
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
              <h6 class="subtitle">
                <span>Tổng cộng</span
                ><span>{{
                  formatCurrency(
                    totalTicketPrice + (totalTicketPrice * 10) / 100
                  )
                }}</span>
              </h6>
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
    ...mapState('billDetails', ['billDetails']),
    ...mapState('bills', ['bills']),

    userId() {
      return this.$store.state.auth.token.id;
    },

    isAuthenticated() {
      return this.$store.getters['auth/isAuthenticated'];
    },

    bill() {
      const currentBill = this.bills
        ? this.bills.filter(bill => bill.id == this.$route.params.id)[0]
        : null;

      // if (currentBill && currentBill.customer && currentBill.customer.id != this.userId) {
      //   return null;
      // }

      return currentBill;
    },

    billDetailList() {
      const billDetails = this.billDetails
        ? this.billDetails.filter(
            billDetail => billDetail.bill.id == this.$route.params.id
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
    formatCurrency
  }
};
</script>
