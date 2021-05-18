import { isEmpty } from 'lodash';

export default ({ store }) => {
  const isAuthenticated = store.getters['auth/isAuthenticated'];

  if (isAuthenticated) {
    // const config = store.state.config.config;
    // isEmpty(config) && store.dispatch('config/get')
  }
}