import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  subtitles: [],
  subtitleOpts: []
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
    const data = await this.$repositories.subtitles.index(payload);
    commit(mutationTypes.SUBTITLE.SET_SUBTITLE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.subtitles.index(payload);
    commit(mutationTypes.SUBTITLE.SET_SUBTITLE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.subtitles.create(
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
      const data = await this.$repositories.subtitles.update(
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
      const { data = {} } = await this.$repositories.subtitles.delete(payload);
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
  [mutationTypes.SUBTITLE.SET_SUBTITLE](state, subtitles) {
    state.subtitles = subtitles;
  },
  [mutationTypes.SUBTITLE.SET_SUBTITLE_OPTIONS](state, subtitles) {
    state.subtitleOpts = subtitles.map(item => ({ id: item.id, name: item.subtitle }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
