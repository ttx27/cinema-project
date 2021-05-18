import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  movieRooms: [],
  movieRoomOpts: []
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
    const data = await this.$repositories.movieRooms.index(payload);
    commit(mutationTypes.MOVIE_ROOM.SET_MOVIE_ROOM, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.movieRooms.index(payload);
    commit(mutationTypes.MOVIE_ROOM.SET_MOVIE_ROOM_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.movieRooms.create(
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
      const data = await this.$repositories.movieRooms.update(
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
      const { data = {} } = await this.$repositories.movieRooms.delete(payload);
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
  [mutationTypes.MOVIE_ROOM.SET_MOVIE_ROOM](state, movieRooms) {
    state.movieRooms = movieRooms;
  },
  [mutationTypes.MOVIE_ROOM.SET_MOVIE_ROOM_OPTIONS](state, movieRooms) {
    state.movieRoomOpts = movieRooms;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
