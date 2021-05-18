import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  movieShifts: [],
  movieShiftOpts: []
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
    const data = await this.$repositories.movieShifts.index(payload);
    commit(mutationTypes.MOVIE_SHIFT.SET_MOVIE_SHIFT, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.movieShifts.index(payload);
    commit(mutationTypes.MOVIE_SHIFT.SET_MOVIE_SHIFT_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.movieShifts.create(
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
      const data = await this.$repositories.movieShifts.update(
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
      const { data = {} } = await this.$repositories.movieShifts.delete(payload);
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
  [mutationTypes.MOVIE_SHIFT.SET_MOVIE_SHIFT](state, movieShifts) {
    state.movieShifts = movieShifts;
  },
  [mutationTypes.MOVIE_SHIFT.SET_MOVIE_SHIFT_OPTIONS](state, movieShifts) {
    state.movieShiftOpts = movieShifts.map(item => ({ id: item.id, name: item.time }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
