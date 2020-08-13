/**
 * 
 */
$(document).ready(function() 
	{
		
		
		/* Browse sidenav link item */
		$("#browse-link-item").click(function() {
			window.location.replace("mcsearch?type=movie&view=group");
		});
		
		/* WatchList sidenav link item */
		$("#watchlist-link-item").click(function() {
			location.replace("watchlist");
		});
		
		/* RecentlyWatched sidenav link item */
		$("#recentlywatched-link-item").click(function() {
			alert("TODO");
		});
		
		/* CurrentUser sidenav link item */
		$("#current-user-link-item").click(function() {
			window.location.replace("user");
		});
		
		/* Create Movie Party sidenav link item */
		$("#create-party-link-item").click(function() {
			location.replace("movieparty?action=new");
		});
		
		/* Movie Party page sidenav link item */
		$(".open-party-link-item").click(function() {
			var key = $(this).find("#party-id").val();
			location.replace("movieparty?key=" + key);
		});
		
	});