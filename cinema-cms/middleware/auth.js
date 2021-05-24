/**
 * to check authentication to access pages
 * @param {Object}
 */
export default function({ store, route, redirect }) {
  if (!store.getters["auth/isAuthenticated"]) {
    if (route.path.includes("/login")) {
    } else {
      return redirect("/login");
    }
  } else {
    if (route.path.includes("/login")) {
      return redirect("/");
    }
  }
}
