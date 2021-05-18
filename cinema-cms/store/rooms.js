import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  rooms: [],
  roomOpts: []
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
    const data = await this.$repositories.rooms.index(payload);
    commit(mutationTypes.ROOM.SET_ROOM, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.rooms.index(payload);
    commit(mutationTypes.ROOM.SET_ROOM_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.rooms.create(
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
      const data = await this.$repositories.rooms.update(
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
      const { data = {} } = await this.$repositories.rooms.delete(payload);
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
  [mutationTypes.ROOM.SET_ROOM](state, rooms) {
    state.rooms = rooms;
  },
  [mutationTypes.ROOM.SET_ROOM_OPTIONS](state, rooms) {
    state.roomOpts = rooms.map(item => ({ id: item.id, name: item.name, cinema: item.cinema }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
