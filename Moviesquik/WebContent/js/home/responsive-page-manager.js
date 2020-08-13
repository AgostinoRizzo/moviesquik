/**
 * 
 */

const MEDIUM_MAX_WIDTH = 1400;
const SMALL_MAX_WIDTH  = 1000;

var sidenavCol;
var postsCol;
var partiesCol;
var chatCol;

var swiper;
var swipeIndex = 0;

var curr_item;

function removeColClass(obj, colClass) 
{
	if ( obj.hasClass(colClass) )
		obj.removeClass(colClass);
}

function removeColClasses(obj) 
{
	removeColClass(obj, 'col');
	for ( var c=1; c<=12; c++ )
		removeColClass(obj, 'col-' + c);
}

function getSwapVisibleCol(cols) 
{
	return cols[ swipeIndex % cols.length ];
}

function hideAll() 
{
	sidenavCol.hide();
	postsCol.hide();
	partiesCol.hide();
	chatCol.hide();
}

function removeColClassesAll() 
{
	removeColClasses(sidenavCol);
	removeColClasses(postsCol);
	removeColClasses(partiesCol);
	removeColClasses(chatCol);
}

function onWindowLarge(onSwipe=false) 
{
	hideAll();
	removeColClassesAll();
	
	sidenavCol.addClass('col-2');
	postsCol.addClass('col-4');
	partiesCol.addClass('col-4');
	chatCol.addClass('col-2');
	
	sidenavCol.show();
	postsCol.show();
	partiesCol.show();
	chatCol.show();
	
	swiper.hide();
}

function onWindowMedium(onSwipe=false) 
{	
	hideAll();
	removeColClassesAll();
	
	sidenavCol.addClass('col-2');
	postsCol.addClass('col-5');
	
	var lastCol = getSwapVisibleCol( [partiesCol, chatCol] );
	lastCol.addClass('col-5');
	
	sidenavCol.show();
	postsCol.show();
	onSwipe ? lastCol.show('slow') : lastCol.show();
	
	swiper.show();
}

function onWindowSmall(onSwipe=false) 
{	
	hideAll();
	removeColClassesAll();
	
	sidenavCol.addClass('col-4');
	
	var lastCol = getSwapVisibleCol( [postsCol, partiesCol, chatCol] );
	lastCol.addClass('col-8');
	
	sidenavCol.show();
	onSwipe ? lastCol.show('slow') : lastCol.show();
	
	swiper.show();
}

function onMediaShowWindowSmall() 
{
	var mediaContentsCol = $("#home-wrapper #media-contents-column");
	removeColClasses(mediaContentsCol);
	
	const width = $(window).width();
	removeColClassesAll();
	
	if ( width <= SMALL_MAX_WIDTH )
	{
		sidenavCol.addClass('col-4');
		mediaContentsCol.addClass('col-8');
	}
	else
	{
		sidenavCol.addClass('col-2');
		mediaContentsCol.addClass('col-10');
	}
	
}

function onWindowResize(onSwipe=false) 
{
	if ( !$("#news-link-item").hasClass('selected') )
	{
		if ( $("#foryou-link-item").hasClass('selected') )
			onMediaShowWindowSmall();
		return;
	}
	
	const width = $(window).width();
	if ( width <= SMALL_MAX_WIDTH )
		onWindowSmall(onSwipe);
	else if ( width <= MEDIUM_MAX_WIDTH )
		onWindowMedium(onSwipe);
	else
		onWindowLarge(onSwipe);
	
	console.log(width);
}

function onSwipe() 
{
	swipeIndex++;
	onWindowResize(true);
}

$(document).ready(function() 
	{
		sidenavCol = $("#home-wrapper #home-sidenav-col");
		postsCol =   $("#home-wrapper #posts-column");
		partiesCol = $("#home-wrapper #movie-parties-column");
		chatCol =    $("#home-wrapper #chats-sidenav-column");
		
		swiper = $("#home-swiper");
		swiper.removeClass('d-none');
		
		swiper.click( onSwipe );
		
		
		$("#media-contents-column").hide();
		$("#news-link-item").addClass("selected");
		
		curr_item = $("#news-link-item");
		
		/* News sidenav link item */
		$("#news-link-item").click(function() {
			curr_item.removeClass("selected");
			curr_item = $(this);
			curr_item.addClass("selected");
			var home_wrapper = $(this).closest("#home-wrapper");
			home_wrapper.find("#media-contents-column").hide();
			swiper.show();
			onWindowResize(true);
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
			swiper.hide();
			curr_item = $(this);
			curr_item.addClass("selected");
			onWindowResize(true);
		});
		
		$(window).resize( onWindowResize );
		onWindowResize();
	}
);