/**
 * 
 */

import '../chat/chat-controller.js';

var selectedUserListNameRow;
var chatStatus = new Map();
var currentUserId;
var friendId;

function appendChatStatus(newStatusHtml, id, fetching=false) 
{
	if ( chatStatus.has(id) )
		newStatusHtml = fetching ? chatStatus.get(id) + newStatusHtml : newStatusHtml + chatStatus.get(id);
	chatStatus.set( id, newStatusHtml );
}

function openChatPopup(friendId, friendName, friendEmail) 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatPopup.find(".users-list-name .users-list-name-text").text(friendName);
	chatPopup.find(".users-list-name .note").text(friendEmail);
	selectedUserListNameRow.addClass('selected-user-list-row');
	
	if ( chatStatus.has(friendId) )
		chatPopup.find("#chat-content").html(chatStatus.get(friendId));
	
	chatPopup.show("slow");
	clearUserChatMessageNotification(friendId);
}

function closeChatPopup() 
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatStatus.set(friendId, chatPopup.find("#chat-content").html());
	friendId = null;
	chatPopup.hide("slow");
	selectedUserListNameRow.removeClass('selected-user-list-row');
}

function increaseUserChatMessageNotification(chatUserId, newMessagePacket)
{
	$(".chat-list").find(".user-chat-row").each(function() 
	{
		const chatId = parseInt( $(this).find("#friend-id").val() );
		if ( chatId == chatUserId )
		{
			var notificationBadge = $(this).find(".new-message-badge");
			var notificationCount = 0;
			var notificationBadgeText = notificationBadge.text().trim();
			
			if ( notificationBadgeText.length && notificationBadge.is(':visible') )
				notificationCount = parseInt(notificationBadgeText);
			
			notificationCount++;
			notificationBadge.text(notificationCount.toString());
			notificationBadge.show();
			
			var notificationTime = $(this).find(".user-chat-last-time");
			notificationTime.text(newMessagePacket.time);
			
			return;
		}
	});
}

function clearUserChatMessageNotification(chatUserId) 
{
	$(".chat-list").find(".user-chat-row").each(function() 
	{
		const chatId = parseInt( $(this).find("#friend-id").val() );
		if ( chatId == chatUserId )
		{
			var notificationBadge = $(this).find(".new-message-badge");
			notificationBadge.text('0');
			notificationBadge.hide();
			
			return;
		}
	});
}

function onMessageReceived(messagePacket, fetching=false) 
{
	const newMessageCloudHtml = createNewMessageCloudHtml( messagePacket, messagePacket.senderId != currentUserId, true);
	const chatId = messagePacket.senderId == currentUserId ? messagePacket.receiverId : messagePacket.senderId;
	
	appendChatStatus(newMessageCloudHtml, chatId, fetching);
	
	if ( messagePacket.senderId == friendId )
		addNewMessageCloud( newMessageCloudHtml, false );
	else
		increaseUserChatMessageNotification(chatId, messagePacket); // add new message notification
}

$(document).ready(function() 
	{		
		$(document).on("click", ".user-chat-row", function() 
		{
			friendId = parseInt( $(this).find("#friend-id").val() );
			const friendName  = $(this).find(".users-list-name .users-list-name-text").text();
			const friendEmail = $(this).find(".users-list-name .note").text();
			
			var chatPopup = $("#chats-sidenav-column .chat-popup");
			selectedUserListNameRow = $(this);
			
			if ( !selectedUserListNameRow.hasClass('selected-user-list-row') && !chatPopup.is(':visible') )
				openChatPopup(friendId, friendName, friendEmail);
		});
		
		$(document).on("click", "#close-chat-popup-btn", function() {
			closeChatPopup();
		});
		
		$("#chat-send-btn").click( function() 
		{
			onChatSend(friendId);
		});
		
		var chatPopup = $("#chats-sidenav-column .chat-popup");
		chatPopup.hide();
		chatPopup.removeClass('d-none');
		
		$(".chat-list").find(".new-message-badge").each(function() 
		{
			$(this).hide();
			$(this).removeClass('d-none');
		});
		
		currentUserId = parseInt( $("#user-id").val() );
		const userIconSrc = $("#user-icon").attr('src');
		var textArea = $("#new-msg-text");
		
		setOnMessageReceivedCallback(onMessageReceived);
		onChatInit( textArea, currentUserId, userIconSrc );
	}
);