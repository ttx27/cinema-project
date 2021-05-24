import * as mutationTypes from "~/utils/mutation-types";
import { setToken, removeToken } from "~/utils/auth";

/**
 * initial state
 */
const state = () => ({
  token: null,
  userInfo: null,
});

/**
 * initial getters
 */
const getters = {
  isAuthenticated: (state) =>
    state.token && state.token.accessToken && state.token.accessToken.length > 0
};

/**
 * initial actions
 */
const actions = {
  async login({ commit }, payload) {
    try {
      const data = await this.$repositories.login.create(payload);
      const { payload: loginData } = data;
      
      commit(mutationTypes.AUTH.SET_TOKEN, {
        ...loginData,
        role: loginData.roles,
        fullname: loginData.fullName || loginData.email
      });
      this.$axios.setToken(loginData.token)
      return { success: true, ...loginData };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async me({ commit }, payload) {
    try {
      const data = await this.$repositories.me.index(payload);
      commit(mutationTypes.AUTH.SET_USER_INFO, data && data.payload);
      return { success: true, ...data };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async register({ commit }, payload) {
    try {
      const data = await this.$repositories.register.create(payload);
      return { success: true, ...data };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },
  
  async forgotPassword({ commit }, payload) {
    try {
      const data = await this.$repositories.forgotPwd.create(payload);
      return { success: true, ...data };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async verify({ commit }, payload) {
    try {
      const data = await this.$repositories.verify.create(payload);
      return { success: true, ...data };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  logout({ commit }) {
    commit(mutationTypes.AUTH.REMOVE_TOKEN);
  },

  async changePassword({ commit }, payload) {
    try {
      const changePasswordData = await this.$repositories.changePwd.create(payload)
      return changePasswordData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async forgotPassword({ commit }, payload) {
    try {
      const forgotPasswordData = await this.$repositories.forgotPwd.create(payload)
      return forgotPasswordData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },
  
  async addPayment({ commit }, payload) {
    try {
      const addPaymentData = await this.$repositories.payment.create(payload)
      return addPaymentData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },
  
  async payWithVNPay({ commit }, payload) {
    try {
      const payWithVNPayData = await this.$repositories.paymentVNPay.create(payload)
      return payWithVNPayData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },
};

/**
 * initial mutations
 */
const mutations = {
  [mutationTypes.AUTH.SET_TOKEN](state, token) {
    state.token = {
      tokenType: "Bearer",
      accessToken: token.token,
      userRole: token.role,
      fullname: token.fullname,
      email: token.email,
      id: token.id
    };
    setToken(token);
  },

  [mutationTypes.AUTH.SET_USER_INFO](state, userInfo) {
    state.userInfo = userInfo;
  },

  [mutationTypes.AUTH.REMOVE_TOKEN](state) {
    state.token = null;
    removeToken();
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
