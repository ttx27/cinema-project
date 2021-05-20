import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  rooms: [],
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
      const { payload = [] } = await this.$repositories.rooms.index(payload);
      commit(mutationTypes.ROOM.SET_ROOMS, payload);
      
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
  [mutationTypes.ROOM.SET_ROOMS](state, rooms) {
    state.rooms = rooms;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
