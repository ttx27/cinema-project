import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  cinemas: [],
  cinemaOpts: []
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
    const data = await this.$repositories.cinemas.index(payload);
    commit(mutationTypes.COURSE.SET_COURSE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.cinemas.index(payload);
    commit(mutationTypes.COURSE.SET_COURSE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.cinemas.create(
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
      const data = await this.$repositories.cinemas.update(
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
      const { data = {} } = await this.$repositories.cinemas.delete(payload);
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
  [mutationTypes.COURSE.SET_COURSE](state, cinemas) {
    state.cinemas = cinemas;
  },
  [mutationTypes.COURSE.SET_COURSE_OPTIONS](state, cinemas) {
    state.cinemaOpts = cinemas.map(item => ({ id: item.id, name: item.name }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
