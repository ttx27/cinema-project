import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  categories: [],
  categoryOpts: []
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
    const data = await this.$repositories.categories.index(payload);
    commit(mutationTypes.CATEGORY.SET_CATEGORY, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.categories.index(payload);
    commit(mutationTypes.CATEGORY.SET_CATEGORY_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.categories.create(
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
      const data = await this.$repositories.categories.update(
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
      const { data = {} } = await this.$repositories.categories.delete(payload);
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
  [mutationTypes.CATEGORY.SET_CATEGORY](state, categories) {
    state.categories = categories;
  },
  [mutationTypes.CATEGORY.SET_CATEGORY_OPTIONS](state, categories) {
    state.categoryOpts = categories.map(item => ({ id: item.id, name: item.name }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
