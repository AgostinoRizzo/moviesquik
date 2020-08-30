/**
 * 
 */

const FACEBOOK_API_APP_ID = '';

window.fbAsyncInit = function() 
{
	FB.init({
		appId 	: FACEBOOK_API_APP_ID,
		status	: true,
		xfbml	: true,
		version	: 'v8.0'
	});
	
	FB.getLoginStatus( function(response) 
	{
		if ( response.status == 'connected' )
			console.log("connected");
	});
}

function validatePostText(postText) 
{
	if ( !postText ) return null;
	postText = postText.trim();
	if ( !postText.length ) return null;
	return postText;
}

function postOnFacebook(postText) 
{
	FB.api('/me/feed', 'POST', {message : postText, access_token: ''}, function(response) 
	{
		window.location.href = 'createpost?post-text=' + postText;
	});
}

function loginOnFacebook(postText) 
{
	postText = validatePostText(postText);
	if ( !postText )
	{
		alert('Invalid post text.');
		return;
	}
	
	FB.login( function(response) 
	{
		if ( response.status == 'connected' )
		{
			console.log("logged in");
			postOnFacebook(postText);
		}
	});
}

$(document).ready( function()
	{
		$(document).on("click", "#publish-on-facebook-btn", function() 
		{
			var postText = $(this).closest('form').find('#new-post-text').val();
			loginOnFacebook(postText);
		});
	}
);

