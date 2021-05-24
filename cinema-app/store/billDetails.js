import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  billDetails: [],
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
      const { payload = [] } = await this.$repositories.billDetails.index(payload);
      commit(mutationTypes.BILLDETAIL.SET_BILLDETAILS, payload);
      
      return data;
    } catch (error) {
      const { data="" } = error.response;
      return data || error;
    }
  },
};

/**
 * initial mutations
 */
const mutations = {
  [mutationTypes.BILLDETAIL.SET_BILLDETAILS](state, billDetails) {
    state.billDetails = billDetails;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
