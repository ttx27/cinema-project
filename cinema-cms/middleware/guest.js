/**
 * to prevent loggedInUser from accessing to signin/signup page
 */
export default function({ store, route, redirect }) {
  if (store.getters["auth/isAuthenticated"]) {
    return redirect(`/`);
  }
}
