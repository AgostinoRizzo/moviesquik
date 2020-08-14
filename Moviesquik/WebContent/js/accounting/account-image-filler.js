/**
 * 
 */

const DEFAULT_AVATAR_IMAGE_SRC = 'res/drawable/user_avatar.jpg';

var imageHrefRegex = /user\?id=(\d+)/;
var accountImageMap = new Map();
var accountImages = [];
var currAccountImageIndex = 0;
const FETCH_ACCOUNT_IMAGE_EVENT_NAME = 'fetchAccountImage';

function fillAccountImage() 
{
	if ( currAccountImageIndex >= accountImages.length )
		return;
	
	var imageTag = accountImages[currAccountImageIndex];
	currAccountImageIndex += 1;
	
	if ( imageTag.attr('src') != DEFAULT_AVATAR_IMAGE_SRC )
		return;
	
	const imgHref = imageTag.closest('a').attr('href');
	var accountId = imageHrefRegex.exec(imgHref);
	var gotAccountId = false;
	if ( accountId && accountId.length >= 2 )
	{
		accountId = accountId[1];
		gotAccountId = true;
		
		if ( accountImageMap.has(accountId) )
		{
			console.log(accountImageMap.get(accountId));
			imageTag.attr('src', accountImageMap.get(accountId) );
			document.dispatchEvent( new Event(FETCH_ACCOUNT_IMAGE_EVENT_NAME) );
			return;
		}
	}
	
	$.ajax(
	{
		url : 'https://randomuser.me/api/',
		crossDomain: true,
		dataType: 'json',
		success: function(data) 
		{
			const imgSrc = data.results[0].picture.medium
			imageTag.attr('src', imgSrc );
			
			if ( gotAccountId )
				accountImageMap.set( accountId, imgSrc );
			document.dispatchEvent( new Event(FETCH_ACCOUNT_IMAGE_EVENT_NAME) );
		}
	});
}

$(document).ready( function()
	{
		$(".avatar-img").each( function() { accountImages.push( $(this) ); });
		if ( accountImages.length )
		{
			currAccountImageIndex = 0;
			document.addEventListener('fetchAccountImage', fillAccountImage);
			document.dispatchEvent( new Event(FETCH_ACCOUNT_IMAGE_EVENT_NAME) );
		}
	}
);
