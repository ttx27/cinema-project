import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  roles: [],
  roleOpts: []
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
    const data = await this.$repositories.roles.index(payload);
    commit(mutationTypes.ROLE.SET_ROLE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.roles.index(payload);
    commit(mutationTypes.ROLE.SET_ROLE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.roles.create(
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
      const data = await this.$repositories.roles.update(
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
      const { data = {} } = await this.$repositories.roles.delete(payload);
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
  [mutationTypes.ROLE.SET_ROLE](state, roles) {
    state.roles = roles;
  },
  [mutationTypes.ROLE.SET_ROLE_OPTIONS](state, roles) {
    state.roleOpts = roles;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
