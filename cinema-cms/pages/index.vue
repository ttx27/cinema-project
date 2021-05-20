<template></template>

<script>
export default {
  middleware({ redirect }) {
    return redirect('/dashboard');
  }
};
</script>

