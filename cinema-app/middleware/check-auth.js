import { AUTH } from "~/utils/mutation-types";
import { getToken, getAccessToken } from "~/utils/auth";

export default function ({
  store,
  req
}) {
  const token = getToken();
  const accessToken = getAccessToken();
  const isAuthenticated = store.getters['auth/isAuthenticated'];

  if (isAuthenticated) return;

  if (token && accessToken) {
    store.commit(`auth/${AUTH.SET_TOKEN}`, JSON.parse(token))
  } else {
    // logout/invalid token case
    store.commit(`auth/${AUTH.REMOVE_TOKEN}`)
  }
}
