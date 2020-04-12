/**
 * 
 */

ITEM_WIDTH = 150;

function update_position(items, parent) {
	if ( items.length <= 1 )
		return;
	
	var last = items[items.length - 1];
	var to_update = null;
	
	for( var i=0; i<items.length; ++i )
		{
			new_left = (parseInt(items[i].style.left, 10) || ITEM_WIDTH*i) - 1;
			items[i].style.left = new_left + 'px';
			
			if ( to_update == null && new_left + ITEM_WIDTH < 0 )
				{
					to_update = items[i];
				}
		}
	
	if ( to_update )
		{
			to_update.appendTo(parent);
		}
//	val = (parseInt(last.style.left, 10) || parseInt(window.getComputedStyle(last).left, 10));
//	width = (parseInt(last.style.width, 10) || parseInt(window.getComputedStyle(last).width, 10));
//	val = val + width;
//	to_update.style.left = val + 'px';
}

$(document).ready(function() {
	top_rated_parent = $("#top-rated-showcase-row");
	most_popular_parent = $("#most-popular-showcase-row");
	most_favorites_parent = $("#most-favorites-showcase-row");
	
	top_rated = top_rated_parent.children();
	most_popular = most_popular_parent.children();
	most_favorites = most_favorites_parent.children();
	
	setInterval(() => {
		update_position(top_rated, top_rated_parent);
		update_position(most_popular, most_popular_parent);
		update_position(most_favorites, most_favorites_parent);
	}, 50);
});