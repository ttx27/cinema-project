import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  bills: [],
});

/**
 * initial getters
 */
const getters = {};

/**
 * initial actions
 */
const actions = {
  async get({ commit }, payload) {
    try {
      const { payload = [] } = await this.$repositories.bills.index(payload);
      commit(mutationTypes.BILL.SET_BILLS, payload);
      
      return data;
    } catch (error) {
      const { data="" } = error.response;
      return data || error;
    }
  },

  async getByCode({ commit }, payload) {
    try {
      const billData = await this.$repositories.bills.getByCode(payload.code);
      return billData;
    } catch (error) {
      const { data="" } = error.response;
      return data || error;
    }
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.bills.create(
        payload
      );
      
      return newData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async checkout({ state, commit }, id) {
    try {
      const newData = await this.$repositories.bills.checkout(
        id
      );
      
      return newData;
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
  [mutationTypes.BILL.SET_BILLS](state, bills) {
    state.bills = bills;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
