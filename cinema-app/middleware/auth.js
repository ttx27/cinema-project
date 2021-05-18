/**
 * to check authentication to access pages
 * @param {Object}
 */
export default function({ store, route, redirect }) {
  if (!store.getters["auth/isAuthenticated"]) {
    if (route.path.includes("/signin")) {
    } else {
      // return redirect("/signin");
    }
  } else {
    if (route.path.includes("/signin") || route.path.includes("/signup") || route.path.includes("/forgot-password")) {
      return redirect("/");
    }
  }
}
