/**
 * 
 */

var SCROLL_TOP_MARGIN = 10;

jQuery(document).ready(function($) {

  // Header fixed and Back to top button
  $(window).scroll(function() {
    if ($(this).scrollTop() > SCROLL_TOP_MARGIN) {
      $('.back-to-top').fadeIn('slow');
      $('#header').addClass('header-fixed');
    } else {
      $('.back-to-top').fadeOut('slow');
      $('#header').removeClass('header-fixed');
    }
  });

  if ($(this).scrollTop() > SCROLL_TOP_MARGIN) {
    $('.back-to-top').fadeIn('slow');
    $('#header').addClass('header-fixed');
  }
});