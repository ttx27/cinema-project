import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  cinemas: [],
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
      const { payload = [] } = await this.$repositories.cinemas.index(payload);
      commit(mutationTypes.CINEMA.SET_CINEMAS, payload);
      
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
  [mutationTypes.CINEMA.SET_CINEMAS](state, cinemas) {
    state.cinemas = cinemas;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
