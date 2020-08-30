/**
 * 
 */

import './popup-alert.js';

var FACEBOOK_API_APP_ID = '';

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

function onLogin(event, profileImgBtn) 
{
	if ( !FACEBOOK_API_APP_ID.length )
	{
		event.preventDefault();
		return;
	}
	
	if ( !profileImgBtn || !profileImgBtn.length )
		return;
	
	var externalProfileIcon = profileImgBtn.closest('.profile-col').find('.external-profile-icon');
	
	if ( !externalProfileIcon || !externalProfileIcon.length )
		return;
	
	event.preventDefault();
	
	var facebookUserId = profileImgBtn.closest('.profile-col').find('#facebook-id').val();
	var profileId = profileImgBtn.closest('.profile-col').find('#profile-id').val();
	
	FB.login( function(response) 
	{
		var login = true;
		
		if ( response.authResponse && response.authResponse.userID && response.authResponse.userID != facebookUserId )
		{
			showMainAlert('Invalid user.');
			login = false;
		}
		else if ( response.status != 'connected' )
		{
			showMainAlert('Login error.');
			login = false;
		}
		
		if ( login )
			window.location.href = 'whoiswatching?action=login&userid=' + profileId;
		else
			FB.logout(function(response) {});
	});
}

function onLogout() 
{
	event.preventDefault();
	
	FB.getLoginStatus( function(response) 
	{
		if ( response.status == 'connected' )
			FB.logout(function(response) { 
				window.location.href = 'login?logout=true&subject=user'; });
	});
	
}

$(document).ready( function()
	{
		$("#main-alert").hide();
		$("#main-alert").removeClass('d-none');
		
		$('.profile-img-button').click( function(event) { onLogin(event, $(this)); } );
		
		$.ajax(
			{
				type: 'GET',
				url: 'api/fbapp',
				dataType: "json",
				success: function(data)
					{
						if ( data.app_id )
							FACEBOOK_API_APP_ID = data.app_id;
					}
			}
		);
		
		//FB.logout(function(response) {});
		/*
		$(document).on("click", '#fb-profile-sign-out-btn', function(event) {
			onLogout(event);
		} );
		*/
	}
);