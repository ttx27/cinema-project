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

  update(id, payload) {
    return $axios.$put(`${resource}/${id}`, payload)
  },

  accept(id, payload) {
    return $axios.$put(`${resource}/accept/${id}`, payload)
  },

  cancel(id, payload) {
    return $axios.$put(`${resource}/cancel/${id}`, payload)
  },

  delivery(id, payload) {
    return $axios.$put(`${resource}/delivery/${id}`, payload)
  },

  delete(id) {
    return $axios.$delete(`${resource}/${id}`)
  },
})
