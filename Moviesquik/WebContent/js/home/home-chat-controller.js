/**
 * 
 */

import '../chat/chat-controller.js';

var selectedUserListNameRow;
var chatStatus = new Map();
var currentUserId;
var friendId;

var lastChatTextAreaLength = 0;
var userEmailMap = new Map();

function appendChatStatus(newStatusHtml, id, fetching=false) 
{
	if ( chatStatus.has(id) )
		newStatusHtml = fetching ? chatStatus.get(id) + newStatusHtml : newStatusHtml + chatStatus.get(id);
	chatStatus.set( id, newStatusHtml );
}

function openChatPopup(friendId, friendName, friendEmail, friendStatus)
{
	var chatPopup = $("#chats-sidenav-column .chat-popup");
	chatPopup.find(".users-list-name .users-list-name-text").text(friendName);
	chatPopup.find(".users-list-name .note").text(friendEmail);
	updateLineStatus( chatPopup.find(".line-status"), friendStatus );
	
	selectedUserListNameRow.addClass('selected-user-list-row');
	
	if ( chatStatus.has(friendId) )
		chatPopup.find("#chat-content").html(chatStatus.get(friendId));
	
	chatPopup.show("slow");
	clearUserChatMessageNotification(friendId);
	
	onChatInfoSend('readall');
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

function updateUserEmailMap(id, email) 
{
	if ( !userEmailMap.has(id) )
		userEmailMap.set(id, email);
}

function onTypingInfoMessageReceived(id, start) 
{
	if ( id == friendId )
	{
		var chatPopup = $("#chats-sidenav-column .chat-popup");
		var noteTag = chatPopup.find(".users-list-name .note");
		updateUserEmailMap(id, noteTag.text());
		noteTag.text( start ? 'typing...' : userEmailMap.get(id) );
	}
	else
	{
		$(".chat-list").find(".user-chat-row").each(function() 
		{
			const chatId = parseInt( $(this).find("#friend-id").val() );
			if ( chatId == id )
			{
				var noteTag = $(this).find(".users-list-name .note");
				updateUserEmailMap(id, noteTag.text());
				noteTag.text( start ? 'typing...' : userEmailMap.get(id) );
				return;
			}
		});
	}
}

function updateLineStatus(lineStatusObj, isOnline) 
{
	if ( lineStatusObj.hasClass('online-status') )
		lineStatusObj.removeClass('online-status');
	if ( lineStatusObj.hasClass('offline-status') )
		lineStatusObj.removeClass('offline-status');
	
	if ( isOnline )
		lineStatusObj.addClass('online-status');
	else
		lineStatusObj.addClass('offline-status');
}

function showMainAlert(alertText) 
{
	$("#main-alert").html(alertText + '<button type="button" class="close">&times;</button>');
	$("#main-alert").hide();
	$("#main-alert").slideDown('slow');
}

function onOnlineOfflineInfoMessageReceived(id, isOnline) 
{
	if ( id == friendId )
	{
		var chatPopup = $("#chats-sidenav-column .chat-popup");
		var lineStatus = chatPopup.find(".line-status");
		updateLineStatus(lineStatus, isOnline);
	}
	
	var userFullName = '';
	var userIconSrc = 'res/drawable/user_avatar.jpg';
	
	$(".chat-list").find(".user-chat-row").each(function() 
	{
		const chatId = parseInt( $(this).find("#friend-id").val() );
		if ( chatId == id )
		{
			userFullName = $(this).find(".users-list-name .users-list-name-text").text().trim();
			userIconSrc  = $(this).find(".avatar-img").attr('src');
			
			var lineStatus = $(this).find(".user-icon-col .line-status");
			updateLineStatus(lineStatus, isOnline);
			return;
		}
	});
	
	// display user online/offline alert
	if ( userFullName.length )
		showMainAlert( '<img src="' + userIconSrc + '" class="avatar-img card-list-avatar-img rounded-circle">&nbsp;&nbsp;' + 
					   '<strong>' + userFullName + '</strong> is now ' + (isOnline ? 'online' : 'offline') );
}

function onInfoMessageReceived(messagePacket) 
{
	// start/stop typing message info
	var start = false;
	if ( messagePacket.text == 'starttyping' )
		start = true;
	if ( start || messagePacket.text == 'stoptyping' )
		onTypingInfoMessageReceived(messagePacket.senderId, start);
	
	// online/offline status message info
	var online = false;
	if ( messagePacket.text == 'online' )
		online = true;
	if ( online || messagePacket.text == 'offline' )
		onOnlineOfflineInfoMessageReceived(messagePacket.senderId, online);
}

function onMessageReceived(messagePacket, fetching=false) 
{
	if ( messagePacket.info && !fetching )
	{
		onInfoMessageReceived(messagePacket);
		return;
	}
	
	const newMessageCloudHtml = createNewMessageCloudHtml( messagePacket, messagePacket.senderId != currentUserId, true);
	const chatId = messagePacket.senderId == currentUserId ? messagePacket.receiverId : messagePacket.senderId;
	
	appendChatStatus(newMessageCloudHtml, chatId, fetching);
	
	if ( messagePacket.senderId == friendId )
		addNewMessageCloud( newMessageCloudHtml, false );
	else if ( !messagePacket.isRead && messagePacket.receiverId == currentUserId )
		increaseUserChatMessageNotification(chatId, messagePacket); // add new message notification
}

function onTyping(start) 
{
	if ( start )
		onChatInfoSend('starttyping');
	else
		onChatInfoSend('stoptyping');
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
				openChatPopup(friendId, friendName, friendEmail, $(this).find(".line-status").hasClass('online-status'));
		});
		
		$(document).on("click", "#close-chat-popup-btn", function() {
			closeChatPopup();
		});
		
		$("#chat-send-btn").click( function() 
		{
			onChatSend(friendId);
			lastChatTextAreaLength = 0;
			onTyping(false);
		});
		
		$("#new-msg-text").on('input', function() 
		{
			const messageText = $(this).val();
			const currLength = messageText.length;
			
			if ( lastChatTextAreaLength == 0 && currLength >= 1 )
				onTyping(true);
			else if ( lastChatTextAreaLength >= 1 && currLength == 0 )
				onTyping(false);
			
			lastChatTextAreaLength = currLength;
		});
		
		var chatPopup = $("#chats-sidenav-column .chat-popup");
		chatPopup.hide();
		chatPopup.removeClass('d-none');
		
		$(".chat-list").find(".new-message-badge").each(function() 
		{
			$(this).hide();
			$(this).removeClass('d-none');
		});
		
		$("#main-alert").hide();
		$("#main-alert").removeClass('d-none');
		
		$(document).on("click", "#main-alert button", function() 
		{
			$("#main-alert").slideUp('slow');
		});
		
		currentUserId = parseInt( $("#user-id").val() );
		const userIconSrc = $("#user-icon").attr('src');
		var textArea = $("#new-msg-text");
		
		setOnMessageReceivedCallback(onMessageReceived);
		onChatInit( textArea, currentUserId, userIconSrc );
	}
);