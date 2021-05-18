import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  seats: [],
  seatOpts: []
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
    const data = await this.$repositories.seats.index(payload);
    commit(mutationTypes.SEAT.SET_SEAT, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.seats.index(payload);
    commit(mutationTypes.SEAT.SET_SEAT_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.seats.create(
        payload
      );
      
      return newData;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async edit({ state, commit }, payload) {
    try {
      const data = await this.$repositories.seats.update(
        payload.id,
        payload
      );

      return data;
    } catch (error) {
      const { data = {} } = error.response;
      return data;
    }
  },

  async delete({ state, commit }, payload) {
    try {
      const { data = {} } = await this.$repositories.seats.delete(payload);
      return data;
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
  [mutationTypes.SEAT.SET_SEAT](state, seats) {
    state.seats = seats;
  },
  [mutationTypes.SEAT.SET_SEAT_OPTIONS](state, seats) {
    state.seatOpts = seats.map(item => ({ id: item.id, name: item.name }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
