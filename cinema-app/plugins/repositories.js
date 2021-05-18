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
    seats: repositoryWithAxios('/api/v1/seats'),
    billDetails: repositoryWithAxios('/api/v1/bill-details'),
    bills: repositoryWithAxios('/api/v1/bills'),
    paymentVNPay: repositoryWithAxios('/api/v1/payments/vnpay'),
  }

  inject('repositories', repositories)
}
