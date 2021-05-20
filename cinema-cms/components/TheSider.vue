<template>
  <a-layout-sider :trigger="null" collapsible :collapsed="collapsed" :width="250">
    <a href="/" class="the-sider__logo">
      <img src="~/assets/images/logo.jpg" alt="">
    </a>
    <a-menu
      mode="inline"
      theme="dark"
      :defaultOpenKeys="openKeys"
      :defaultSelectedKeys="[selectedKeys]"
      @select="handleSelectItem"
      style="height: 100%"
    >
      <template v-for="item in privateMenu">
        <a-menu-item v-if="!item.children" :key="item.key">
          <a-icon v-if="item.icon" :type="item.icon" />
          <span>{{item.title}}</span>
        </a-menu-item>
        <sub-menu v-else :menu-info="item" :key="item.key" />
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import { isEmpty, intersection } from 'lodash';
import { mapState, mapGetters } from 'vuex';
import TheSiderSubMenu from './TheSiderSubMenu';

const initialMenu = [
  {
    key: '/',
    title: 'Dashboard',
    icon: 'dashboard'
  },
  {
    key: '/cinema',
    configKey: 'cinema_management',
    title: 'Quản lý rạp phim',
    icon: 'dashboard',
  },
  {
    key: '/room',
    configKey: 'room_management',
    title: 'Quản lý phòng',
    icon: 'dashboard',
  },
  {
    key: '/seat',
    configKey: 'seat_management',
    title: 'Quản lý ghế',
    icon: 'dashboard',
  },
  {
    key: '/seat-type',
    configKey: 'seat_type_management',
    title: 'Quản lý loại ghế',
    icon: 'dashboard',
  },
  {
    key: '/movie',
    configKey: 'movie_management',
    title: 'Quản lý phim',
    icon: 'dashboard',
  },
  {
    key: '/actor',
    configKey: 'actor_management',
    title: 'Quản lý diễn viên',
    icon: 'dashboard',
  },
  {
    key: '/director',
    configKey: 'director_management',
    title: 'Quản lý đạo diễn',
    icon: 'dashboard',
  },
  {
    key: '/language',
    configKey: 'language_management',
    title: 'Quản lý ngôn ngữ phim',
    icon: 'dashboard',
  },
  {
    key: '/subtitle',
    configKey: 'subtitle_management',
    title: 'Quản lý phụ đề',
    icon: 'dashboard',
  },
  {
    key: '/category',
    configKey: 'category_management',
    title: 'Quản lý thể loại',
    icon: 'dashboard',
  },
  {
    key: '/movie-shift',
    configKey: 'movie_shift_management',
    title: 'Quản lý ca chiếu',
    icon: 'dashboard',
  },
  {
    key: '/movie-room',
    configKey: 'movie_room_management',
    title: 'Quản lý phòng chiếu phim',
    icon: 'dashboard',
  },
  {
    key: '/show-time',
    configKey: 'show_time_management',
    title: 'Quản lý lịch chiếu phim',
    icon: 'dashboard',
  },
  {
    key: '/user',
    configKey: 'user_management',
    title: 'Quản lý người dùng',
    icon: 'user',
  }
];

export default {
  components: {
    'sub-menu': TheSiderSubMenu
  },

  props: {
    collapsed: Boolean
  },

  data() {
    return {
      menu: Object.freeze(initialMenu)
    };
  },

  computed: {
    ...mapState('auth', ['userInfo']),

    selectedKeys() {
      return this.$route.path;
    },

    openKeys() {
      const splitedPath = this.$route.path.split('/');
      if (splitedPath.length <= 1) {
        return splitedPath[0];
      } else {
        const tmp = splitedPath.map(item => '/' + item);
        return tmp.slice(1, tmp.length);
      }
    },

    privateMenu() {
      return this.menu.filter(menu => !menu.roles || (this.userInfo && this.userInfo.roles && intersection(menu.roles, this.userInfo.roles).length > 0));
    }
  },

  methods: {
    handleSelectItem({ item, key, selectedKeys }) {
      const route = selectedKeys.reverse().join('');
      this.$router.push(route);
    }
  }
};
</script>

<style lang="scss">
@import '~/assets/scss/components/_the-sider.scss';
</style>