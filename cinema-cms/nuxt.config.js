require('dotenv').config()

export default {
  mode: 'spa',
  /*
  ** Headers of the page
  */
  head: {
    title: 'Dashboard',
    titleTemplate: '%s - ' + 'Cinema CMS',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: 'Cinema CMS' }
    ],
    link: [],
    htmlAttrs: {
      lang: 'vi',
    },
  },
  /**
   * env
   */
  env: {
    BASE_URL: process.env.BASE_URL,
    PORT: process.env.PORT,
  },
  /*
  ** Customize the progress-bar color
  */
  loading: {
    color: '#1d8dc6'
  },
  /*
  ** Customize the transition
  */
//  transition: {
//    enterActiveClass:"animated faster slideInLeft",
//    leaveActiveClass:"animated faster slideOutRight",
//  },
  /*
  ** Global CSS
  */
  css: [
    '~assets/scss/styles.scss'
  ],
  /*
  ** Plugins to load before mounting the App
  */
  plugins: [
    '@/plugins/antd-ui',
    '~/plugins/axios',
    '~/plugins/repositories',
    '~/plugins/moment',
    '~/plugins/number-format',
  ],
  /**
   * Global middleware
   */
  router: {
    middleware: ['check-auth', 'get-config']
  },
  /*
  ** Nuxt.js dev-modules
  */
  buildModules: [
  ],
  /*
  ** Nuxt.js modules
  */
  modules: [
    '@nuxtjs/pwa',
    // ['@nuxtjs/dotenv', { filename: '.env.prod' }],
    '@nuxtjs/axios',
    '@nuxtjs/style-resources',
  ],
  /**
   * Global scss
   */
  styleResources: {
    scss: [
      '~assets/scss/abstracts/_variables.scss',
      '~assets/scss/abstracts/_functions.scss',
      '~assets/scss/abstracts/_mixins.scss',
      '~assets/scss/abstracts/*.scss',
      '~assets/scss/vendors/*.scss',
    ],
  },
  /*
  ** Axios module configuration
  ** See https://axios.nuxtjs.org/options
  */
  axios: {
    baseURL: process.env.BASE_URL, // Default: http://[HOST]:[PORT][PREFIX]
    redirectError: {
      401: '/login',
      404: '/404'
    },
    credentials: false,
    retry: false, // interceptor retry time request
    debug: false
  },

  server: {
    port: process.env.PORT, // default: 3000
    host: '0.0.0.0' // default: localhost
  },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** You can extend webpack config here
    */
    extend (config, ctx) {
    },

    loaders: {
      less: { javascriptEnabled: true }
    },

    babel: {
      presets ({ isServer }, [ preset, options ]) {
        return [
          [
            preset, {
              ...options,
              targets: isServer ? { node: 'current' } : { android: '4', ie: '9' }
            }
          ]
        ]
      }
    }
  }
}
