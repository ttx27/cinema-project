<template>
  <div>
    <section class="details-banner bg_img" data-background="">
      <div class="container">
        <form class="ticket-search-form" @submit="handleSearchBill">
          <div class="form-group large">
            <input
              v-model="billCode"
              required
              type="text"
              placeholder="Nhập mã hóa đơn"
            />
            <button type="submit">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </form>
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
                    <h5 class="sub-title">Danh sách vé</h5>
                  </div>
                  <div class="casting-slider" v-if="bill && bill.billDetails">
                    <div class="movie-review-item">
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
                            {{ bill.code }}
                          </p>
                          <p class="reply-date my-1">
                            Trạng thái:
                            <span
                              :style="{
                                color: bill.status == 0 ? 'red' : 'lime'
                              }"
                            >
                              {{
                                bill.status == 0
                                  ? 'Chưa thanh toán'
                                  : 'Đã thanh toán'
                              }}
                            </span>
                          </p>
                          <span>
                            <i class="fas fa-check"></i>Rạp:
                            {{
                              get(
                                bill,
                                'showTime.movieRoomDetail.roomDetail.cinemaDetail.name'
                              )
                            }}</span
                          >
                          |
                          <span>
                            <i class="fas fa-check"></i>Số vé:
                            {{ bill.billDetails.length }}</span
                          >
                          |
                          <span>
                            <i class="fas fa-check"></i>Tổng tiền:
                            {{ formatCurrency(bill.total || 0) }}</span
                          >
                          |
                          <nuxt-link
                            v-if="bill.status == 1"
                            to="#"
                            @click.native="handlePrintTicket"
                            >In vé</nuxt-link
                          >
                          <nuxt-link
                            v-else
                            :to="
                              `/movies/${bill.showTime.movieRoomDetail.movieDetail.id}/booking/${bill.showTime.id}/ticket/${bill.id}`
                            "
                            >Thanh toán</nuxt-link
                          >
                        </div>
                      </div>
                    </div>

                    <client-only>
                      <vue-html2pdf
                        ref="html2Pdf"
                        :paginate-elements-by-height="1400"
                        @progress="onProgress($event)"
                      >
                        <section class="pdf-content" slot="pdf-content">
                          <div
                            class="cardWrap"
                            v-for="(billDetail, index) in bill.billDetails"
                            :key="index"
                          >
                            <div class="ticketCard cardLeft">
                              <h1>
                                {{
                                  get(
                                    bill,
                                    'showTime.movieRoomDetail.roomDetail.cinemaDetail.name'
                                  )
                                }}
                              </h1>
                              <div class="title">
                                <h2>
                                  {{
                                    get(
                                      bill,
                                      'showTime.movieRoomDetail.movieDetail.name'
                                    )
                                  }}
                                </h2>
                                <span>Phim</span>
                              </div>
                              <div class="seat">
                                <h2>
                                  {{
                                    get(
                                      bill,
                                      'showTime.movieRoomDetail.roomDetail.name'
                                    )
                                  }}
                                </h2>
                                <span>Phòng chiếu</span>
                              </div>
                              <div class="time">
                                <h2>
                                  {{
                                    moment(
                                      get(bill, 'showTime.movieDate')
                                    ).format('HH:mm DD/MM/YYYY')
                                  }}
                                </h2>
                                <span>Giờ chiếu</span>
                              </div>
                            </div>
                            <div class="ticketCard cardRight">
                              <div class="eye"></div>
                              <div class="number">
                                <h3>
                                  {{ billDetail.seat.name }} -
                                  {{ billDetail.seat.number }}
                                </h3>
                                <span>Ghế</span>
                              </div>
                              <div class="barcode">
                                <barcode background="transparent" :value="billDetail.ticketNumber">
                                </barcode>
                              </div>
                            </div>
                          </div>
                        </section>
                      </vue-html2pdf>
                    </client-only>
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
import VueBarcode from 'vue-barcode';
import { formatCurrency } from '~/utils/number-format';

export default {
  head() {
    return {
      title: 'In vé'
    };
  },

  components: {
    'barcode': VueBarcode,
  },

  async fetch({ store, params }) {
    try {
      await Promise.all([
        store.dispatch('bills/get'),
        store.dispatch('billDetails/get')
      ]);
    } catch (error) {}
  },

  data() {
    return {
      baseUrl: process.env.BASE_URL,
      buttonLoading: false,
      billCode: '',
      bill: null
    };
  },

  computed: {
    ...mapState('auth', ['token']),
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
    formatCurrency,

    async handlePrintTicket(e) {
      e.preventDefault();

      $('.preloader').fadeIn(50);

      this.$refs.html2Pdf.generatePdf();
    },

    onProgress(progress) {
      if (progress == 100) {
        $('.preloader').fadeOut(100);
        this.$swal("In vé thành công!");
      }
    },

    async handleSearchBill(e) {
      e.preventDefault();

      if (!this.billCode) {
        this.$swal('Bạn cần nhập mã hóa đơn!');
        return;
      }

      if (!this.bill) {
        this.bill = null;
      }

      try {
        $('.preloader').fadeIn(50);

        const doGetBill = await this.$store.dispatch('bills/getByCode', {
          code: this.billCode
        });

        if (doGetBill.errors || doGetBill.message) {
          $('.preloader').fadeOut(100);
          setTimeout(() => {
            this.$swal(
              (doGetBill.errors && doGetBill.errors.message) ||
                doGetBill.message
            );
          }, 150);
        } else {
          $('.preloader').fadeOut(100);
          if (doGetBill.payload && doGetBill.payload.id) {
            this.bill = doGetBill.payload;
          }
        }
      } catch (error) {
        this.$swal('Đã có lỗi xảy ra!');
      }
    }
  }
};
</script>

<style lang="scss">
.pdf-content {
  $red: #e84c3d;
  $grey: #ecedef;
  $black: #343434;

  * {
    line-height: 135% !important;
  }

  .cardWrap {
    width: 30em;
    margin: 3em auto;
    color: #fff;
    font-family: sans-serif;
  }

  .ticketCard {
    background: linear-gradient(
      to bottom,
      $red 0%,
      $red 26%,
      $grey 26%,
      $grey 100%
    );
    height: 11em;
    float: left;
    position: relative;
    padding: 1em;
    margin-top: 100px;
  }

  .cardLeft {
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
    width: 16em;
  }

  .cardRight {
    width: 8.5em;
    border-left: 0.18em dashed #fff;
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
    padding: 1em 0.5em;
    &:before,
    &:after {
      content: '';
      position: absolute;
      display: block;
      width: 0.9em;
      height: 0.9em;
      background: #fff;
      border-radius: 50%;
      left: -0.5em;
    }
    &:before {
      top: -0.4em;
    }
    &:after {
      bottom: -0.4em;
    }
  }

  h1 {
    font-size: 1em;
    margin-top: 0;
    span {
      font-weight: normal;
    }
  }

  .title,
  .name,
  .seat,
  .time {
    text-transform: uppercase;
    font-weight: normal;
    font-size: 16px;
    h2 {
      font-size: 0.9em;
      color: #525252;
      margin: 0;
    }
    span {
      font-size: 0.7em;
      line-height: 135%;
      color: #a2aeae;
    }
  }

  .title {
    margin: 1em 0 0 0;
  }

  .name,
  .seat {
    margin: 0.7em 0 0 0;
  }

  .time {
    margin: 0.7em 0 0 1em;
  }

  .seat,
  .time {
    float: left;
  }

  .eye {
    // position: relative;
    width: 2em;
    height: 0.5em;
    // background: #fff;
    // margin: 0 auto;
    // border-radius: 1em/0.6em;
    // z-index: 1;
  }

  .number {
    text-align: center;
    text-transform: uppercase;
    h3 {
      color: $red;
      margin: 0.9em 0 0 0;
      font-size: 1.5em;
    }
    span {
      display: block;
      color: #a2aeae;
    }
  }

  .barcode {
    height: 4em;
    width: 100%;
    margin: 0.4em 0 0;

    & > div {
      width: 100%;
      height: 100%;

      & > svg {
        max-height: 100%;
        max-width: 100%;
      }
    }
  }
}
</style>
