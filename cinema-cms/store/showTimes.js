import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  showTimes: [],
  showTimeOpts: []
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
    const data = await this.$repositories.showTimes.index(payload);
    commit(mutationTypes.SHOW_TIME.SET_SHOW_TIME, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.showTimes.index(payload);
    commit(mutationTypes.SHOW_TIME.SET_SHOW_TIME_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.showTimes.create(
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
      const data = await this.$repositories.showTimes.update(
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
      const { data = {} } = await this.$repositories.showTimes.delete(payload);
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
  [mutationTypes.SHOW_TIME.SET_SHOW_TIME](state, showTimes) {
    state.showTimes = showTimes;
  },
  [mutationTypes.SHOW_TIME.SET_SHOW_TIME_OPTIONS](state, showTimes) {
    state.showTimeOpts = showTimes;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
