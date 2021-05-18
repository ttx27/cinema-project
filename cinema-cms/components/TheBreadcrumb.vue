<template>
  <a-breadcrumb style="margin: 16px" :routes="routes">
    <template slot="itemRender" slot-scope="{route, params, routes, paths}">
      <span v-if="routes.indexOf(route) === routes.length - 1">{{route.breadcrumbName}}</span>
      <n-link v-else :to="`${basePath}/${paths.join('/')}`">{{route.breadcrumbName}}</n-link>
    </template>
  </a-breadcrumb>
</template>

<script>
export default {
  data() {
    return {
      basePath: ``,
      routesData: []
    };
  },

  computed: {
    routes() {
      const currentPath = this.$route.path;
      const currentPathData = this.routesData.reduce((acc, cur) => {
        const index = currentPath.split('/').indexOf(cur.path);
        if (index > 0) {
          acc[index - 1] = cur;
        }
        return acc;
      }, []);

      return currentPathData
    }
  }
};
</script>