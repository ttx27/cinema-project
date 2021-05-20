// Provide nuxt-axios instance to use same configuration across the whole project
// I've used typical CRUD method names and actions here
export default $axios => resource => ({
  index(payload) {
    return $axios.$get(`${resource}`, payload)
  },

  show(id, payload) {
    return $axios.$get(`${resource}/${id}`, payload)
  },

  create(payload) {
    return $axios.$post(`${resource}`, payload)
  },

  add(payload) {
    return $axios.$post(`${resource}/add`, payload)
  },

  update(id, payload) {
    return $axios.$put(`${resource}/${id}`, payload)
  },

  bulkDelete(payload) {
    return $axios.$patch(`${resource}/bulk-delete`, payload)
  },

  deleteAll(payload) {
    return $axios.$patch(`${resource}/delete-all`, payload)
  },

  bulkCancel(payload) {
    return $axios.$patch(`${resource}/bulk-cancel`, payload)
  },

  cancelAll(payload) {
    return $axios.$patch(`${resource}/cancel-all`, payload)
  },

  delete(id) {
    return $axios.$delete(`${resource}/${id}`)
  }
})
