import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  seats: [],
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
      const { payload = [] } = await this.$repositories.seats.index(payload);
      commit(mutationTypes.SEAT.SET_SEATS, payload);
      
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
  [mutationTypes.SEAT.SET_SEATS](state, seats) {
    state.seats = seats;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
