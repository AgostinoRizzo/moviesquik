/**
 * 
 */

var selectedUserListNameRow;

function openChatPopup(friendId, friendName, friendEmail) 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatPopup.find(".users-list-name .users-list-name-text").text(friendName);
	chatPopup.find(".users-list-name .note").text(friendEmail);
	selectedUserListNameRow.addClass('selected-user-list-row');
	chatPopup.removeClass('d-none');
}

function closeChatPopup() 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatPopup.addClass('d-none');
	selectedUserListNameRow.removeClass('selected-user-list-row');
}

$(document).ready(function() 
	{
		$(document).on("click", ".users-list-name", function() 
		{
			var friendRow = $(this).closest(".user-list-row");
			const friendId    = friendRow.find("#friend-id").val();
			const friendName  = friendRow.find(".users-list-name .users-list-name-text").text();
			const friendEmail = friendRow.find(".users-list-name .note").text();
			
			selectedUserListNameRow = friendRow;
			openChatPopup(friendId, friendName, friendEmail);
		});
		
		$(document).on("click", "#close-chat-popup-btn", function() {
			closeChatPopup();
		});
	}
);