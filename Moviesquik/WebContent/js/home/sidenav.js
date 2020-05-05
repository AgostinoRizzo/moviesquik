/**
 * 
 */
$(document).ready(function() 
	{
		$("#media-contents-column").hide();
		$("#news-link-item").addClass("selected");
		
		var curr_item = $("#news-link-item");
		
		/* News sidenav link item */
		$("#news-link-item").click(function() {
			curr_item.removeClass("selected");
			var home_wrapper = $(this).closest("#home-wrapper");
			home_wrapper.find("#media-contents-column").hide();
			home_wrapper.find("#posts-column").show();
			home_wrapper.find("#movie-parties-column").show();
			home_wrapper.find("#chats-sidenav-column").show();
			curr_item = $(this);
			curr_item.addClass("selected");
		});
		
		/* ForYou sidenav link item */
		$("#foryou-link-item").click(function() {
			curr_item.removeClass("selected");
			var home_wrapper = $(this).closest("#home-wrapper");
			home_wrapper.find("#posts-column").hide();
			home_wrapper.find("#movie-parties-column").hide();
			home_wrapper.find("#chats-sidenav-column").hide();
			home_wrapper.find("#media-contents-column").show();
			home_wrapper.find("#media-contents-column").removeClass("d-none");
			curr_item = $(this);
			curr_item.addClass("selected");
		});
		
		/* Browse sidenav link item */
		$("#browse-link-item").click(function() {
			window.location.replace("mcsearch?type=movie&view=group");
		});
		
		/* WatchList sidenav link item */
		$("#watchlist-link-item").click(function() {
			alert("TODO");
		});
		
		/* RecentlyWatched sidenav link item */
		$("#recentlywatched-link-item").click(function() {
			alert("TODO");
		});
		
		/* CurrentUser sidenav link item */
		$("#current-user-link-item").click(function() {
			window.location.replace("user");
		});
		
	});