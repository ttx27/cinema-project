import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  movies: [],
  movieOpts: []
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
    const data = await this.$repositories.movies.index(payload);
    commit(mutationTypes.MOVIE.SET_MOVIE, data && data.payload || []);
  },

  async getOptions({ commit }, payload) {
    const data = await this.$repositories.movies.index(payload);
    commit(mutationTypes.MOVIE.SET_MOVIE_OPTIONS, data && data.payload || []);
  },

  async add({ state, commit }, payload) {
    try {
      const newData = await this.$repositories.movies.create(
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
      const data = await this.$repositories.movies.update(
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
      const { data = {} } = await this.$repositories.movies.delete(payload);
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
  [mutationTypes.MOVIE.SET_MOVIE](state, movies) {
    state.movies = movies;
  },
  [mutationTypes.MOVIE.SET_MOVIE_OPTIONS](state, movies) {
    state.movieOpts = movies.map(item => ({ id: item.id, name: item.name }));
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
