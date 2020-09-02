/**
 * 
 */

//var USERS_DATA_API_URL = 'https://randomuser.me/api/';

function findUserAvatars(userAvatarClasses) 
{
	for ( var i in userAvatarClasses )
	{
		$('img.' + userAvatarClasses[i]).each( function() 
		{
			var currentImg = $(this);
			$.ajax(
				{
					url : USERS_DATA_API_URL,
					crossDomain: true,
					dataType: 'json',
					success: function(data) 
					{
						var userData = data.results[0];
						currentImg.attr('src', userData.picture.medium);
					}
				});
		});
	}
}

$(document).ready( function()
	{
		findUserAvatars( ['avatar-img', 'profile-img'] );
	}
);