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
                Số dư hiện tại:
                {{
                  formatCurrency(
                    (userInfo.payment && userInfo.payment.amount) || 0
                  )
                }}
              </p>
              <form class="checkout-contact-form" @submit="handlePayment">
                <div class="form-group">
                  <input
                    v-model="amount"
                    type="number"
                    required
                    placeholder="Nhập số tiền muốn nạp"
                    min="10000"
                  />
                </div>
                <div class="form-group">
                  <input
                    type="submit"
                    class="custom-button"
                    value="Nạp tiền"
                  />
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </section>
    <section class="movie-details-section padding-top padding-bottom">
      <div class="container">
        <div class="row justify-content-center flex-wrap-reverse mb--50">
          <div class="col-lg-12 mb-50"></div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import { get } from 'lodash';
import moment from 'moment';
import querystring from 'qs';
import sha256 from 'sha256';
import { formatCurrency } from '~/utils/number-format';

export default {
  head() {
    return {
      title: 'Trang cá nhân'
    };
  },

  components: {
  },

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
      buttonLoading: false,
      amount: 10000,
      paypal: {
        sandbox: ''
      }
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
    },

    createOrder: function (data, actions) {
      return actions.order.create({
        purchase_units: [
          {
            amount: {
              value: "0.01",
            },
          },
        ],
      });
    },
    onAuthorize: function (data, actions) {
      return actions.order.capture();
    },
  },

  mounted() {
    if (this.$route.query.success == 0) {
        this.$swal("Giao dịch thanh toán không thành công!");
    }
  },

  methods: {
    moment,
    get,
    formatCurrency,

    async handlePayment(e) {
      e.preventDefault();

      if (!this.amount || this.amount < 10000) {
        this.$swal("Số tiền không hợp lệ!");
        return;
      }

      try {
        $('.preloader').fadeIn(50);

        const doPayment = await this.$store.dispatch(
          "auth/payWithVNPay",
          {
            amount: String(this.amount),
            orderInfo: "Nap tien vao he thong",
            orderType: "topup",
            bankCode: "",
            language: "vn"
          }
        );
        
        if (doPayment.errors || doPayment.message) {
          $('.preloader').fadeOut(100);
          setTimeout(() => {
            this.$swal(doPayment.errors && doPayment.errors.message || doPayment.message);
          }, 150);
        } else {
          $('.preloader').fadeOut(100);
          if (doPayment.payload) {
            window.location.href = doPayment.payload;
          }
        }
      } catch (error) {
        this.$swal("Đã có lỗi xảy ra!");
      }
    }
  }
};
</script>
