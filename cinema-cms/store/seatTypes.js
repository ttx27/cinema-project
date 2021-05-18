import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  seatTypes: [],
  seatTypeOpts: []
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
    const data = await this.$repositories.seatTypes.index(payload);
    commit(mutationTypes.SEAT_TYPE.SET_SEAT_TYPE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.seatTypes.index(payload);
    commit(mutationTypes.SEAT_TYPE.SET_SEAT_TYPE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.seatTypes.create(
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
      const data = await this.$repositories.seatTypes.update(
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
      const { data = {} } = await this.$repositories.seatTypes.delete(payload);
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
  [mutationTypes.SEAT_TYPE.SET_SEAT_TYPE](state, seatTypes) {
    state.seatTypes = seatTypes;
  },
  [mutationTypes.SEAT_TYPE.SET_SEAT_TYPE_OPTIONS](state, seatTypes) {
    state.seatTypeOpts = seatTypes.map(item => ({ id: item.id, name: item.name }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
