import * as mutationTypes from '~/utils/mutation-types';

/**
 * initial state
 */
const state = () => ({
  movies: [],
});

/**
 * initial getters
 */
const getters = {};

/**
 * initial actions
 */
const actions = {
  async clearMovie({ commit }, payload) {
    commit(mutationTypes.MOVIE.SET_MOVIES, null);
  },

  async get({ commit }, payload) {
    try {
      const { payload = [] } = await this.$repositories.movies.index(payload);
      commit(mutationTypes.MOVIE.SET_MOVIES, payload);
      
      return data;
    } catch (error) {
      const { data="" } = error.response;
      return data || error;
    }
  },

  // async getListStudent({ commit }, payload) {
  //   try {
  //     const { data = {} } = await this.$repositories.classrooms.getListStudent(payload.code);
  //     commit(mutationTypes.CLASSROOM.SET_LIST_STUDENT, data);
      
  //     return data;
  //   } catch (error) {
  //     const { data="" } = error.response;
  //     return data || error;
  //   }
  // },
};

/**
 * initial mutations
 */
const mutations = {
  [mutationTypes.MOVIE.SET_MOVIES](state, movies) {
    state.movies = movies;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
