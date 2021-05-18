import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  users: [],
  userOpts: [],
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
    const data = await this.$repositories.users.index(payload);
    commit(mutationTypes.USERS.SET_USERS, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.users.index(payload);
    commit(mutationTypes.USERS.SET_USER_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.users.create(
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
      const data = await this.$repositories.users.update(
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
      const { data = {} } = await this.$repositories.users.delete(payload);
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
  [mutationTypes.USERS.SET_USERS](state, users) {
    state.users = users;
  },
  [mutationTypes.USERS.SET_USER_OPTIONS](state, users) {
    state.userOpts = users;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
