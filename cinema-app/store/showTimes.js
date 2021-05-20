import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  showTimes: [],
  showTimeSeats: [],
});

/**
 * initial getters
 */
const getters = {};

/**
 * initial actions
 */
const actions = {
  async get({ commit }, _payload) {
    try {
      const { payload = [] } = await this.$repositories.showTimes.index(_payload);
      commit(mutationTypes.SHOWTIME.SET_SHOWTIMES, payload);
      
      return payload;
    } catch (error) {
      const { data="" } = error.response;
      return data || error;
    }
  },
  async getAvailableSeats({ commit }, _payload) {
    try {
      const { payload = [] } = await this.$repositories.showTimes.seats(_payload.id, _payload);
      commit(mutationTypes.SHOWTIME.SET_SHOWTIME_SEATS, payload);
      
      return payload;
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
  [mutationTypes.SHOWTIME.SET_SHOWTIMES](state, showTimes) {
    state.showTimes = showTimes;
  },
  [mutationTypes.SHOWTIME.SET_SHOWTIME_SEATS](state, showTimeSeats) {
    state.showTimeSeats = showTimeSeats;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
