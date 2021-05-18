import Vue from 'vue'
import { formatCurrency, numberWithCommas } from '~/utils/number-format'

Vue.filter('formatCurrency', number => formatCurrency(number))
Vue.filter('numberWithCommas', numberWithCommas)
