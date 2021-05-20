// Provide nuxt-axios instance to use same configuration across the whole project
// I've used typical CRUD method names and actions here
export default $axios => resource => ({
  update(payload) {
    return $axios.$put(`${resource}`, payload)
  },
})
