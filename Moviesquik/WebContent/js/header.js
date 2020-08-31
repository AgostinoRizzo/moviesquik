/**
 * 
 */

var SCROLL_TOP_MARGIN = 10;

function request_search_query_items(url_str)
{
	$.ajax(
		{
			type: "GET",
			url: url_str,
			dataType: "json",
			success: function(data)
				{
					var allSearchItems = [];
					$.each(data, function(index, item) {
						allSearchItems.push(item.type + "#" + item.title + "#" + item.subtitle);
					});
					
					$("#search-textbox").typeahead({
					  	  source: allSearchItems,
						  highlighter: function(item) {
							  var parts = item.split('#');
							  alert(parts);
							  html = '<a>' + parts[0] + '</a>';
				              return html;
				            }
					});
				}
		}
	);
};

jQuery(document).ready(function($) {

	if ( !$("#header .navbar-collapse").is(':visible') )
		$('#header').addClass('header-fixed');
	
	$(window).resize(function() {
		if ( !$("#header .navbar-collapse").is(':visible') )
			$('#header').addClass('header-fixed');
	});
	
  // Header fixed and Back to top button
  $(window).scroll(function() {
	  
		  if ( !$("#header .navbar-collapse").is(':visible') )
		  {
			  $('#header').addClass('header-fixed');
			  return;
		  }
		  
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
  
  
  /* search textbox behavior */
  
  $("#search-icon").click(function() {
	  $("#navbar-nav-links").addClass("d-none");
	  $("#search-icon").addClass("d-none");
	  $("#searching-icon").removeClass("d-none");
	  $("#search-textbox").css('width', '70%');
	  $("#search-textbox").parent().css('width', '70%');
	  $("#search-textbox").removeClass("d-none");
	  $("#search-textbox").focus();
  });
  
  $("#search-textbox").focusin(function() {
	  $("#navbar-nav-links").addClass("d-none");
	  $(this).css('width', '70%');
	  $(this).parent().css('width', '70%');
  });
  
  $("#search-textbox").focusout(function() {
	  $("#navbar-nav-links").removeClass("d-none");
	  $("#search-icon").removeClass("d-none");
	  $("#searching-icon").addClass("d-none");
	  $(this).css('width', '');
	  $(this).parent().css('width', '');
	  $(this).addClass("d-none");
  });
  
  $('#search-textbox').typeahead( {
	  	updater: function (item) {
	  		if ( item.type == "search_query" )
	  			return item.query;
	  		if ( item.type == "user")
	  			window.location.href = "user?id=" + item.id;
	  		if ( item.type == "media_content")
	  			window.location.href = "search?query=" + item.title;
	  		return item.title + ", " + item.subtitle;
  		},
		source: function (query, response) {
	        return $.get('textsearch?query='+query, { query: query }, function (data) {
	            return response(data);
	        });
	    },
	    displayText: function(item) {
	    	return JSON.stringify(item);
		},
		highlighter: function(item) {
			var item_obj = JSON.parse(item);
			var html  = '<div>';
			
			if ( item_obj.type == "media_content")
				html += "<a>\n<span class=\"fa fa-film\"></span>&nbsp;&nbsp;&nbsp;"
			else if ( item_obj.type == "user")
				html += "<a>\n<span class=\"fa fa-user\"></span>&nbsp;&nbsp;&nbsp;&nbsp;"
			else if ( item_obj.type == "search_query")
				{
					html += "<a>\n<span class=\"fa fa-search\"></span>&nbsp;&nbsp;&nbsp;&nbsp;"
					html     += item_obj.query;
					return html;
				}
			
			html     += item_obj.title + ", " + item_obj.subtitle;
			html     += '</a></div>';
            return html;
          },
        matcher: function(item) {
			return true;
		},
		sorter: function(items) {
			var found = false;
			var search_query_item = null;
			for ( i=0; i<items.length && !found; ++i )
				if ( items[i].type == "search_query" )
					{
						search_query_item = items[i];
						items.splice(i, 1);
						found = true;
					}
			if ( found )
				items.unshift(search_query_item);
			return items;
		},
		changeInputOnSelect: false,
		changeInputOnMove: false,
		fitToElement: true
	});
  
});