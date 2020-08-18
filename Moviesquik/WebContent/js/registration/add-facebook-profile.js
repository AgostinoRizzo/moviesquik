/**
 * 
 */

const FACEBOOK_API_APP_ID = '325081788542228';
var addFacebookProfileBtnClicked = false;

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

function getValidDataString(facebookDataString) 
{
	var dataRegexp = /(\d{2})\/(\d{2})\/(\d{4})/;
	var fields = dataRegexp.exec(facebookDataString);
	return fields[3] + '-' + fields[2] + '-' + fields[1];
}

function getUserInfo() 
{
	FB.api('/me', {fields : 'first_name,last_name,email,birthday,gender,id'}, function(response) 
	{
		const validBirthday = getValidDataString(response.birthday);
		
		var newProfiledata =
			{
				fb_id      : response.id,
				first_name : response.first_name,
				last_name  : response.last_name,
				email      : response.email,
				birthday   : validBirthday,
				gender     : response.gender
			};
		
		$.ajax
		(
			{
				type: 'POST',
				url: 'addprofile?ext=true',
				data: newProfiledata,
				success: function(data) 
					{
						location.href = '.';
					}
			}
		);
	});
}

function addFacebookProfile() 
{
	if ( addFacebookProfileBtnClicked )
		return;
	
	$("#add-facebook-profile-btn").text('connecting...');
	addFacebookProfileBtnClicked = true;
	
	FB.login( function(response) 
	{
		if ( response.status == 'connected' )
		{
			console.log("logged in");
			getUserInfo();
		}
		
	});
}

$(document).ready( function()
	{
		$("#add-facebook-profile-btn").click( addFacebookProfile );
	}
);