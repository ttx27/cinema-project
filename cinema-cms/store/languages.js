import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  languages: [],
  languageOpts: []
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
    const data = await this.$repositories.languages.index(payload);
    commit(mutationTypes.LANGUAGE.SET_LANGUAGE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.languages.index(payload);
    commit(mutationTypes.LANGUAGE.SET_LANGUAGE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.languages.create(
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
      const data = await this.$repositories.languages.update(
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
      const { data = {} } = await this.$repositories.languages.delete(payload);
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
  [mutationTypes.LANGUAGE.SET_LANGUAGE](state, languages) {
    state.languages = languages;
  },
  [mutationTypes.LANGUAGE.SET_LANGUAGE_OPTIONS](state, languages) {
    state.languageOpts = languages.map(item => ({ id: item.id, name: item.language }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
