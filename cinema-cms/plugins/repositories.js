import createRepository from '~/apis/repository.js'
import createWSRepository from '~/apis/warehouseStore'
import createAppAccountsNotiRepository from '~/apis/appAccountsNotification'
import createOrderAgencyRepository from '~/apis/orderAgency'

export default (ctx, inject) => {
  const repositoryWithAxios = createRepository(ctx.$axios)
  const WSRepositoryWithAxios = createWSRepository(ctx.$axios)
  const appAccountsNotiWithAxios = createAppAccountsNotiRepository(ctx.$axios)
  const orderAgencyWithAxios = createOrderAgencyRepository(ctx.$axios)

  const repositories = {
    me: repositoryWithAxios('/api/v1/auth/me'),
    login: repositoryWithAxios('/api/v1/auth/signin'),
    register: repositoryWithAxios('/api/v1/auth/signup'),
    verify: repositoryWithAxios('/api/v1/auth/verify'),
    changePwd: repositoryWithAxios('/v1/auth/change-password'),
    forgotPwd: repositoryWithAxios('/v1/auth/reset-password'),
    payment: repositoryWithAxios('/api/v1/payments'),
    movies: repositoryWithAxios('/api/v1/movies'),
    cinemas: repositoryWithAxios('/api/v1/cinemas'),
    rooms: repositoryWithAxios('/api/v1/rooms'),
    showTimes: repositoryWithAxios('/api/v1/show-times'),
    seatTypes: repositoryWithAxios('/api/v1/seat-types'),
    seats: repositoryWithAxios('/api/v1/seats'),
    billDetails: repositoryWithAxios('/api/v1/bill-details'),
    actors: repositoryWithAxios('/api/v1/actors'),
    directors: repositoryWithAxios('/api/v1/directors'),
    languages: repositoryWithAxios('/api/v1/languages'),
    subtitles: repositoryWithAxios('/api/v1/subtitles'),
    categories: repositoryWithAxios('/api/v1/categories'),
    movieShifts: repositoryWithAxios('/api/v1/movie-shifts'),
    movieRooms: repositoryWithAxios('/api/v1/movie-rooms'),
    showTimes: repositoryWithAxios('/api/v1/show-times'),
    bills: repositoryWithAxios('/api/v1/bills'),
    users: repositoryWithAxios('/api/v1/users'),
    roles: repositoryWithAxios('/api/v1/roles'),
  }

  inject('repositories', repositories)
}
