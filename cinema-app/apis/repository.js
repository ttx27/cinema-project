// Provide nuxt-axios instance to use same configuration across the whole project
// I've used typical CRUD method names and actions here
export default $axios => resource => ({
  index(payload) {
    return $axios.$get(`${resource}`, payload)
  },

  show(id, payload) {
    return $axios.$get(`${resource}/${id}`, payload)
  },

  seats(id, payload) {
    return $axios.$get(`${resource}/${id}/seats`, payload)
  },

  getListStudent(id, payload) {
    return $axios.$get(`${resource}/${id}/students`, payload)
  },

  create(payload) {
    return $axios.$post(`${resource}`, payload)
  },

  add(payload) {
    return $axios.$post(`${resource}/add`, payload)
  },

  getByCode(code) {
    return $axios.$get(`${resource}/${code}/bycode`, {})
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
  },

  findByCode(code, payload) {
    return $axios.$get(`${resource}/${code}/find-by-code`, payload)
  },

  join(payload) {
    return $axios.$post(`${resource}/join`, payload)
  },

  accept(payload) {
    return $axios.$post(`${resource}/accept`, payload)
  },

  reject(payload) {
    return $axios.$post(`${resource}/reject`, payload)
  },

  deleteResource(id, payload) {
    return $axios.$patch(`${resource}/${id}/delete`, payload)
  },

  extractResource(payload) {
    return $axios.$post(`${resource}/extract-question`, payload)
  },

  checkout(id) {
    return $axios.$post(`${resource}/${id}/checkout`)
  }
})
