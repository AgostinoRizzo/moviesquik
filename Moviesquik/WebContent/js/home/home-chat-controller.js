/**
 * 
 */

import '../chat/chat-controller.js';

var selectedUserListNameRow;
var chatStatus = new Map();
var friendId;

function openChatPopup(friendId, friendName, friendEmail) 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatPopup.find(".users-list-name .users-list-name-text").text(friendName);
	chatPopup.find(".users-list-name .note").text(friendEmail);
	selectedUserListNameRow.addClass('selected-user-list-row');
	
	if ( chatStatus.has(friendId) )
		chatPopup.find("#chat-content").html(chatStatus.get(friendId));
	
	chatPopup.removeClass('d-none');
}

function closeChatPopup() 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatStatus.set(friendId, chatPopup.find("#chat-content").html());
	chatPopup.addClass('d-none');
	selectedUserListNameRow.removeClass('selected-user-list-row');
}

$(document).ready(function() 
	{		
		$(document).on("click", ".user-chat-row", function() 
		{
			friendId = $(this).find("#friend-id").val();
			const friendName  = $(this).find(".users-list-name .users-list-name-text").text();
			const friendEmail = $(this).find(".users-list-name .note").text();
			
			selectedUserListNameRow = $(this);
			openChatPopup(friendId, friendName, friendEmail);
		});
		
		$(document).on("click", "#close-chat-popup-btn", function() {
			closeChatPopup();
		});
		
		$("#chat-send-btn").click( function() 
		{
			onChatSend(friendId);
		});
		
		const userId = $("#user-id").val();
		const userIconSrc = $("#user-icon").attr('src');
		var textArea = $("#new-msg-text");
		
		onChatInit( textArea, userId, userIconSrc );
	}
);