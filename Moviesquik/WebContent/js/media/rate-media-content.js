/**
 * 
 */

var rateStarValue = [];
var isRating = false;
var isLiking = false;

function initRateStarValues() 
{
	for ( var i=0; i<5; ++i )
		rateStarValue.push($("#my-rate-col .fa-star").eq(i).hasClass('checked-star'));
}

function clearRateStar(rateStar) 
{
	if ( rateStar.hasClass('checked-star') )
		rateStar.removeClass('checked-star');
}

function clearAllRateStars() 
{
	for ( var i=0; i<5; ++i )
		clearRateStar($("#my-rate-col .fa-star").eq(i));
}

function setRateStar(rateStar) 
{
	rateStar.addClass('checked-star');
}

function onSelectRate(targetStar) 
{
	var starIndex = $("#my-rate-col .fa-star").index(targetStar);
	clearAllRateStars();
	
	for( var i=0; i<=starIndex; ++i )
		setRateStar($("#my-rate-col .fa-star").eq(i));
	
}

function onReleaseRate() 
{
	clearAllRateStars();
	for ( var i=0; i<5; ++i )
		if ( rateStarValue[i] )
			setRateStar($("#my-rate-col .fa-star").eq(i));
		else
			return;
}

function onRate() 
{
	if ( isRating )
		return;
	
	var r=0;
	for ( ; r<5; ++r )
		if ( !$("#my-rate-col .fa-star").eq(r).hasClass('checked-star') )
			break;
	
	var mediaId = $("#media-content-page-container #media-id").val();
	isRating = true;
	
	$.ajax(
		{
			type: "GET",
			url: 'review?media=' + mediaId + '&rate=' + r,
			dataType: "json",
			success: function(data)
				{
					if ( data.status )
					{
						clearAllRateStars();
						for ( var i=0; i<5; ++i )
						{
							const toSet = ( i < r );
							if ( toSet )
								setRateStar($("#my-rate-col .fa-star").eq(i));
							rateStarValue[i] = toSet;
						}
						
						$("#my-rate-col #rate-string").html('<strong>' + r + '</strong> / 5');
						
						isRating = false;
					}
				}
		}
	);
	
}

function onLikeNotLikeResponse(currentThumbs, otherThumbs, currentCountTagId, otherCountTagId, currentMainCountTagId, otherMainCountTagId) 
{
	const currentAlreadySet = currentThumbs.hasClass('text-primary');
	const otherAlreadySet = otherThumbs.hasClass('text-primary');
	var countTag = currentThumbs.closest('.like-notlike-count').find(currentCountTagId);
	var countText = countTag.text();
	var countInt = parseInt( countText.trim() );
	
	if ( currentAlreadySet )
	{
		currentThumbs.removeClass('text-primary');
		countInt -= 1;
	}
	else
	{
		currentThumbs.addClass('text-primary');
		
		if ( otherAlreadySet )
		{
			var otherCountTag = otherThumbs.closest('.like-notlike-count').find(otherCountTagId);
			var otherCountText = otherCountTag.text();
			var otherCountInt = parseInt( otherCountText.trim() ) - 1;
			
			otherThumbs.removeClass('text-primary');
			otherCountTag.text(otherCountInt.toString());
			$("#media-content-page-container").find(otherMainCountTagId).text(otherCountInt.toString());
		}
		countInt += 1;
	}
	
	countTag.text(countInt.toString());
	$("#media-content-page-container").find(currentMainCountTagId).text(countInt.toString());
}

function onLikeNotLike(currentThumbs, otherThumbs, currentCountTagId, otherCountTagId, currentMainCountTagId, otherMainCountTagId, isLike) 
{
	if ( isLiking )
		return;
	
	const currentAlreadySet = currentThumbs.hasClass('text-primary');
	var value = '';
	
	if ( currentAlreadySet )
		value = 'null';
	else
		value = isLike ? 'true' : 'false';
	
	var mediaId = $("#media-content-page-container #media-id").val();
	isLiking = true;
	
	$.ajax(
		{
			type: "GET",
			url: 'review?media=' + mediaId + '&like=' + value,
			dataType: "json",
			success: function(data)
				{
					if ( data.status )
					{
						onLikeNotLikeResponse(currentThumbs, otherThumbs, currentCountTagId, otherCountTagId, currentMainCountTagId, otherMainCountTagId);
						isLiking = false;
					}
				}
		}
	);
}

$(document).ready(function()
{
	initRateStarValues();
	
	$("#my-rate-col .fa-star").hover( function() { onSelectRate($(this)); }, onReleaseRate );
	$("#my-rate-col .fa-star").click( onRate );
	
	$(".like-notlike-col .fa-thumbs-up").click( function() 
	{
		onLikeNotLike( $(".like-notlike-col .fa-thumbs-up"), $(".like-notlike-col .fa-thumbs-down"), '#likes-count', '#notlikes-count', '#main-likes-count', '#main-nolikes-count', true );
	});
	
	$(".like-notlike-col .fa-thumbs-down").click( function() 
	{
		onLikeNotLike( $(".like-notlike-col .fa-thumbs-down"), $(".like-notlike-col .fa-thumbs-up"), '#notlikes-count', '#likes-count', '#main-nolikes-count', '#main-likes-count', false );
	});
	
});