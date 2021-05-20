import { TOKEN_KEY, ACCESS_TOKEN } from "./config";

/**
 * Set token to localStorage
 * @param {Object}
 */
export const setToken = (token) => {
  if (!token) return;
  window.localStorage.setItem(TOKEN_KEY, JSON.stringify(token));
  window.localStorage.setItem(ACCESS_TOKEN, token.token);
}

/**
 * Remove token to localStorage
 */
export const removeToken = () => {
  window.localStorage.removeItem(TOKEN_KEY);
  window.localStorage.removeItem(ACCESS_TOKEN);
}

/**
 * Get token from localStorage
 */
export const getToken = () => window.localStorage.getItem(TOKEN_KEY)

/**
 * Get accessToken from localStorage
 */
export const getAccessToken = () => window.localStorage.getItem(ACCESS_TOKEN)