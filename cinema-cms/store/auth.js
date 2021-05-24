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
  async signin({ commit }, payload) {
    try {
      const data = await this.$repositories.login.create(payload);
      const { payload: loginData } = data;

      if (!loginData.admin) {
        return { success: false, message: 'Bạn không có quyền truy cập hệ thống!' };
      }
      
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

  async check({ commit }, payload) {
    try {
      const data = await this.$repositories.me.index(payload);
      commit(mutationTypes.AUTH.SET_USER_INFO, data && data.payload);
      return { success: true, ...data };
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async signout({ commit }) {
    try {
      commit(mutationTypes.AUTH.REMOVE_TOKEN);
      return { success: true };
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
    state.userInfo = null;
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
