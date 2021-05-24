<template>
  <footer class="footer-section">
    <div class="container">
      <div class="footer-top">
        <div class="logo">
          <nuxt-link to="/"
            ><img width="100px" src="/assets/images/footer/footer-logo.jpg" alt="footer"
          /></nuxt-link>
        </div>
        <ul class="social-icons">
          <li>
            <a href="#0"><i class="fab fa-facebook-f"></i></a>
          </li>
          <li>
            <a href="#0"><i class="fab fa-twitter"></i></a>
          </li>
          <li>
            <a href="#0"><i class="fab fa-pinterest-p"></i></a>
          </li>
          <li>
            <a href="#0"><i class="fab fa-google"></i></a>
          </li>
          <li>
            <a href="#0"><i class="fab fa-instagram"></i></a>
          </li>
        </ul>
      </div>
      <div class="footer-bottom">
        <div class="footer-bottom-area">
          <div class="left">
            <p>
              Copyright © 2021.All Rights Reserved By
              <nuxt-link to="/">CGV </nuxt-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import '~/assets/scss/components/_the-footer.scss';
</style>
