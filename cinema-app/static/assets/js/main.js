(function(a) {
  a(window).on('load', function() {
    a('.preloader').fadeOut(1000);
    var d = a('.bg_img');
    d.css('background-image', function() {
      var e = 'url(' + a(this).data('background') + ')';
      return e;
    });
    var b = a('.grid-area');
    var c = {};
    b.isotope({ itemSelector: '.grid-item', masonry: { columnWidth: 0 } });
    a('ul.filter').on('click', 'li', function() {
      var e = a(this).attr('data-filter');
      e = c[e] || e;
      b.isotope({ filter: e });
    });
    a('ul.filter').each(function(g, f) {
      var e = a(f);
      e.on('click', 'li', function() {
        e.find('.active').removeClass('active');
        a(this).addClass('active');
      });
    });
  });
  a(document).ready(function() {
    a('.select-bar').niceSelect();
    a('.video-popup').magnificPopup({ type: 'iframe' });
    a('body').each(function() {
      a(this)
        .find('.img-pop')
        .magnificPopup({ type: 'image', gallery: { enabled: true } });
    });
    new WOW().init();
    a('.faq-wrapper .faq-title').on('click', function(l) {
      var m = a(this).parent('.faq-item');
      if (m.hasClass('open')) {
        m.removeClass('open');
        m.find('.faq-content').removeClass('open');
        m.find('.faq-content').slideUp(300, 'swing');
      } else {
        m.addClass('open');
        m.children('.faq-content').slideDown(300, 'swing');
        m.siblings('.faq-item')
          .children('.faq-content')
          .slideUp(300, 'swing');
        m.siblings('.faq-item').removeClass('open');
        m.siblings('.faq-item')
          .find('.faq-title, .faq-title-two')
          .removeClass('open');
        m.siblings('.faq-item')
          .find('.faq-content')
          .slideUp(300, 'swing');
      }
    });
    a('.header-bar').on('click', function() {
      a('.menu').toggleClass('active');
      a('.header-bar').toggleClass('active');
      a('.overlay').toggleClass('active');
    });
    a('.overlay').on('click', function() {
      a('.menu').removeClass('active');
      a('.header-bar').removeClass('active');
      a('.overlay').removeClass('active');
    });
    a('ul>li>.submenu')
      .parent('li')
      .addClass('menu-item-has-children');
    a('ul')
      .parent('li')
      .hover(function() {
        var l = a(this).find('ul');
        var m = a(l).offset();
        if (m.left + l.width() > a(window).width()) {
          var n = -a(l).width();
          l.css({ left: n });
        }
      });
    a('.menu li a').on('click', function(l) {
      var m = a(this).parent('li');
      if (m.hasClass('open')) {
        m.removeClass('open');
        m.find('li').removeClass('open');
        m.find('ul').slideUp(300, 'swing');
      } else {
        m.addClass('open');
        m.children('ul').slideDown(300, 'swing');
        m.siblings('li')
          .children('ul')
          .slideUp(300, 'swing');
        m.siblings('li').removeClass('open');
        m.siblings('li')
          .find('li')
          .removeClass('open');
        m.siblings('li')
          .find('ul')
          .slideUp(300, 'swing');
      }
    });
    var k = a('.scrollToTop');
    a(window).on('scroll', function() {
      if (a(this).scrollTop() < 500) {
        k.removeClass('active');
      } else {
        k.addClass('active');
      }
    });
    a('.scrollToTop').on('click', function() {
      a('html, body').animate({ scrollTop: 0 }, 500);
      return false;
    });
    var e = a('.header-section');
    a(window).on('scroll', function() {
      if (a(this).scrollTop() < 1) {
        e.removeClass('header-active');
      } else {
        e.addClass('header-active');
      }
    });
    a('.window-warning .lay').on('click', function() {
      a('.window-warning').addClass('inActive');
    });
    a('.seat-plan-wrapper li .movie-schedule .item').on('click', function() {
      a('.window-warning').removeClass('inActive');
    });
    a('.tab ul.tab-menu li').on('click', function(l) {
      var n = a(this).closest('.tab'),
        m = a(this)
          .closest('li')
          .index();
      n.find('li')
        .siblings('li')
        .removeClass('active');
      a(this)
        .closest('li')
        .addClass('active');
      n.find('.tab-area')
        .find('div.tab-item')
        .not('div.tab-item:eq(' + m + ')')
        .fadeOut(500);
      n.find('.tab-area')
        .find('div.tab-item:eq(' + m + ')')
        .fadeIn(500);
      l.preventDefault();
    });
    a('.search-tab ul.tab-menu li').on('click', function(l) {
      var m = a(this).closest('.search-tab'),
        n = a(this)
          .closest('li')
          .index();
      m.find('li')
        .siblings('li')
        .removeClass('active');
      a(this)
        .closest('li')
        .addClass('active');
      m.find('.tab-area')
        .find('div.tab-item')
        .not('div.tab-item:eq(' + n + ')')
        .hide(10);
      m.find('.tab-area')
        .find('div.tab-item:eq(' + n + ')')
        .show(10);
      l.preventDefault();
    });
    a('.tabTwo ul.tab-menu li').on('click', function(l) {
      var n = a(this).closest('.tabTwo'),
        m = a(this)
          .closest('li')
          .index();
      n.find('li')
        .siblings('li')
        .removeClass('active');
      a(this)
        .closest('li')
        .addClass('active');
      n.find('.tab-area')
        .find('div.tab-item')
        .not('div.tab-item:eq(' + m + ')')
        .fadeOut(10);
      n.find('.tab-area')
        .find('div.tab-item:eq(' + m + ')')
        .fadeIn(10);
      l.preventDefault();
    });
    a('.counter-item').each(function() {
      a(this).isInViewport(function(n) {
        if (n === 'entered') {
          for (
            var m = 0;
            m < document.querySelectorAll('.odometer').length;
            m++
          ) {
            var l = document.querySelectorAll('.odometer')[m];
            l.innerHTML = l.getAttribute('data-odometer-final');
          }
        }
      });
    });
    a('.social-icons li a').on('mouseover', function(l) {
      var m = a(this).parent('li');
      if (m.children('a').hasClass('active')) {
        m.siblings('li')
          .children('a')
          .removeClass('active');
        a(this).addClass('active');
      } else {
        m.siblings('li')
          .children('a')
          .removeClass('active');
        a(this).addClass('active');
      }
    });
    a('.tab-slider').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 30,
      autoplay: true,
      autoplayTimeout: 2000,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        576: { items: 2 },
        768: { items: 2 },
        992: { items: 3 },
        1200: { items: 4 }
      }
    });
    a('.sponsor-slider').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 30,
      autoplay: true,
      autoplayTimeout: 1500,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        500: { items: 2 },
        768: { items: 3 },
        992: { items: 4 },
        1200: { items: 5 }
      }
    });
    a('.casting-slider').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 100,
      autoplay: true,
      autoplayTimeout: 2000,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        450: { items: 2 },
        768: { items: 3 },
        992: { items: 3 },
        1200: { items: 4 }
      }
    });
    var f = a('.casting-slider');
    f.owlCarousel();
    a('.cast-next').on('click', function() {
      f.trigger('next.owl.carousel');
    });
    a('.cast-prev').on('click', function() {
      f.trigger('prev.owl.carousel', [300]);
    });
    a('.casting-slider-two').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 100,
      autoplay: true,
      autoplayTimeout: 2000,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        450: { items: 2 },
        768: { items: 3 },
        992: { items: 3 },
        1200: { items: 4 }
      }
    });
    var j = a('.casting-slider-two');
    j.owlCarousel();
    a('.cast-next-2').on('click', function() {
      j.trigger('next.owl.carousel');
    });
    a('.cast-prev-2').on('click', function() {
      j.trigger('prev.owl.carousel', [300]);
    });
    a('.details-photos').owlCarousel({
      dots: false,
      autoplay: true,
      autoplayTimeout: 5000,
      smartSpeed: 1000,
      margin: 30,
      nav: false,
      responsive: {
        0: { items: 1 },
        576: { items: 2 },
        768: { items: 3 },
        1024: { items: 3 },
        1200: { items: 3 }
      }
    });
    var b = 0;
    a('.seat-free img').on('click', function(l) {
      if (b == 0) {
        a(this).attr('src', 'assets/images/movie/seat01-free.png');
        b = 1;
      } else {
        if (b == 1) {
          a(this).attr('src', 'assets/images/movie/seat01-booked.html');
          b = 0;
        }
      }
    });
    var c = 1;
    a('.seat-free-two img').on('click', function(l) {
      if (c == 0) {
        a(this).attr('src', 'assets/images/movie/seat02-free.png');
        c = 1;
      } else {
        if (c == 1) {
          a(this).attr('src', 'assets/images/movie/seat02-booked.png');
          c = 0;
        }
      }
    });
    var d = a('.cart-plus-minus');
    d.prepend('<div class="dec qtybutton">-</div>');
    d.append('<div class="inc qtybutton">+</div>');
    a('.qtybutton').on('click', function() {
      var l = a(this);
      var n = l
        .parent()
        .find('input')
        .val();
      if (l.text() === '+') {
        var m = parseFloat(n) + 1;
      } else {
        if (n > 0) {
          var m = parseFloat(n) - 1;
        } else {
          m = 1;
        }
      }
      l.parent()
        .find('input')
        .val(m);
    });
    a('.speaker-slider').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 30,
      autoplay: true,
      autoplayTimeout: 2000,
      autoplayHoverPause: true,
      responsive: {
        0: { items: 1 },
        576: { items: 2 },
        768: { items: 3 },
        992: { items: 3 },
        1200: { items: 4 }
      }
    });
    var i = a('.speaker-slider');
    i.owlCarousel();
    a('.speaker-next').on('click', function() {
      i.trigger('next.owl.carousel');
    });
    a('.speaker-prev').on('click', function() {
      i.trigger('prev.owl.carousel', [300]);
    });
    a('.client-slider').owlCarousel({
      loop: true,
      nav: false,
      dots: true,
      items: 1,
      autoplay: true,
      autoplayTimeout: 2500,
      autoplayHoverPause: true
    });
    a('.countdown').countdown(
      { date: '10/15/2022 05:00:00', offset: +2, day: 'Day', days: 'Days' },
      function() {
        alert('Done!');
      }
    );
    a('.widget-slider').owlCarousel({
      loop: true,
      nav: false,
      dots: false,
      items: 1,
      autoplay: true,
      autoplayTimeout: 2500,
      autoplayHoverPause: true,
      margin: 30
    });
    var h = a('.widget-slider');
    h.owlCarousel();
    a('.widget-next').on('click', function() {
      h.trigger('next.owl.carousel');
    });
    a('.widget-prev').on('click', function() {
      h.trigger('prev.owl.carousel', [300]);
    });
    a('.blog-slider').owlCarousel({
      loop: true,
      nav: false,
      dots: false,
      items: 1,
      autoplay: true,
      autoplayTimeout: 2500,
      autoplayHoverPause: true
    });
    var g = a('.blog-slider');
    g.owlCarousel();
    a('.blog-next').on('click', function() {
      g.trigger('next.owl.carousel');
    });
    a('.blog-prev').on('click', function() {
      g.trigger('prev.owl.carousel', [300]);
    });
  });
})(jQuery);
