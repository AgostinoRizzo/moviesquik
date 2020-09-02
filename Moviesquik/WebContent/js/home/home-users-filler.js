/**
 * 
 */

const CHAT_INSERTED_USERS_COUNT = 10;
const FETCH_NEW_USER_EVENT_NAME = 'fetchUser';
const USERS_DATA_API_URL = 'https://randomuser.me/api/';

var userFillCounter = 0;

function fillChatUsers(chatContainer) 
{
	if ( userFillCounter >= CHAT_INSERTED_USERS_COUNT )
		return;
	
	userFillCounter++;
	
	$.ajax(
	{
		url : USERS_DATA_API_URL,
		crossDomain: true,
		dataType: 'json',
		success: function(data) 
		{
			var userData = data.results[0];
			var chatContainer = $("#chats-sidenav-column");
			chatContainer.append
			(
				'<div class="row user-list-row clickable-light user-chat-row">' +
					
					'<div class="col-auto col-light-left user-icon-col">' +
						'<img src="' + userData.picture.medium + '" class="avatar-img card-list-avatar-img rounded-circle">' +
						'<i class="fa fa-times-circle fa-xs line-status offline-status"></i>' +
					'</div>' +
			 
					'<div class="col users-list-name col-light-right">' +
						'<p class="users-list-name-text">' + userData.name.first + ' ' + userData.name.last + '</p>' +
						'<p class="note">' + userData.email + '</p>' +
					'</div>' +
					
					'<div class="col-auto col-light-right col-light-left col-right">' +
						'<small class="chat-note user-chat-last-time">00:00</small><br>' +
						'<div class="new-message-badge d-none">1</div>' +
					'</div>' +
				  
				'</div>'
			);
			
			document.dispatchEvent( new Event(FETCH_NEW_USER_EVENT_NAME) );
		}
	});
}

$(document).ready( function()
	{
		userFillCounter = 0;
		document.addEventListener(FETCH_NEW_USER_EVENT_NAME, fillChatUsers);
		document.dispatchEvent( new Event(FETCH_NEW_USER_EVENT_NAME) );
	}
);