import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  actors: [],
  actorOpts: []
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
    const data = await this.$repositories.actors.index(payload);
    commit(mutationTypes.ACTOR.SET_ACTOR, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.actors.index(payload);
    commit(mutationTypes.ACTOR.SET_ACTOR_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.actors.create(
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
      const data = await this.$repositories.actors.update(
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
      const { data = {} } = await this.$repositories.actors.delete(payload);
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
  [mutationTypes.ACTOR.SET_ACTOR](state, actors) {
    state.actors = actors;
  },
  [mutationTypes.ACTOR.SET_ACTOR_OPTIONS](state, actors) {
    state.actorOpts = actors.map(item => ({ id: item.id, name: item.fullName }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
