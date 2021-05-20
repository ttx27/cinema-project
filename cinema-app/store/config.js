import * as mutationTypes from "~/utils/mutation-types";

/**
 * initial state
 */
const state = () => ({
  config: {}
});

/**
 * initial getters
 */
const getters = {
  product: (state) => state.config && state.config.product || {},
  clientType: state => state.config && state.config.client.type || {},
  permissionList: state => state.config && state.config.permission.list || {},
  permissionAssignList: state => state.config && state.config.permission.assignList || {},
  roleList: state => state.config && state.config.role.list || {},
  roleAssignList: state => state.config && state.config.role.assignList || {},
  notificationType: state => state.config && state.config.notification_type || {},
  menu: state => state.config && state.config.menu || {}
};

/**
 * initial actions
 */
const actions = {
  async get({ commit }) {
    const { data = [] } = await this.$repositories.config.index();
    commit(mutationTypes.CONFIG.SET_CONFIG, data);
    return data;
  }
};

/**
 * initial mutations
 */
const mutations = {
  [mutationTypes.CONFIG.SET_CONFIG](state, config) {
    state.config = config;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
};
