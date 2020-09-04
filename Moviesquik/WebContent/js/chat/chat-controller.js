/**
 * 
 */

import '../chat/chat-manager.js';

var currentUserId;
var currentUserIconSrc;
var messageTextArea;
var chatMessageCounter = 0;
var messageReceivedCallback;
var isGroupChat;

function getNextChatMessageId() 
{
	const id = chatMessageCounter;
	chatMessageCounter++;
	return id;
}

window.createNewMessageCloudHtml = function( messagePacket, received=false, append=false )
{
	var chatContent =$("#chat-content");
	const messageText = messagePacket.text.replace(/\n/g, '<br>');
	const html = received 
			?
			('<div class="chat-message-container other-chat-message-container">' +
				
				'<div class="chat-message-cloud">' + messageText + 
					'<div class="chat-message-cloud-info">' +
						'<small>' + (messagePacket.isRead ? '' : '<span class="badge badge-success">NEW</span>&nbsp;') + messagePacket.time + '</small>' +
					'</div>' +
				'</div>' +
				
				'<a href="user?id=' + messagePacket.senderId + '"><img src="' + messagePacket.senderIconSrc + '" class="avatar-img rounded-circle"></a>' +
				
			'</div>')
			:
			('<div class="chat-message-container my-chat-message-container" id="chat-message-' + messagePacket.id + '">' +
				
				'<div class="chat-message-cloud">' + messageText + 
					'<div class="chat-message-cloud-info">' +
						'<small>' + 
							(append ?
							messagePacket.time + ' <i class="fa fa-check"></i>':
							'sending... <i class="fa fa-cloud-upload"></i>')	+ 
						'</small>' +
					'</div>' +
				'</div>' +
				
				'<a href="user?id=' + currentUserId + '"><img src="' + currentUserIconSrc + '" class="avatar-img rounded-circle"></a>' +
				
			'</div>');
	
	return html;
}

window.addNewMessageCloud = function( messageHtml, append=false )
{
	var chatContent =$("#chat-content");
	
	if ( append )
		chatContent.append(messageHtml);
	else
		chatContent.prepend(messageHtml);
	
	chatContent.find("#empty-chat-content").hide();
}

function createNewMessagePacket( messageText, userId, userIconSrc, recvId=-1 ) 
{
	return { text : messageText, id : getNextChatMessageId(), ack : false, time : '', senderId : userId, senderIconSrc : userIconSrc, receiverId : recvId };
}

window.createNewInfoMessagePacket = function( infoText, userId ) 
{
	return { info : true, senderId : userId, text : infoText };
}

function onChatMessageReceived( messagePacket ) 
{
	if ( messagePacket.ack )
	{
		if ( messagePacket.id >= chatMessageCounter )
			return;
		$('#chat-content #chat-message-' + messagePacket.id).find('.chat-message-cloud-info').find('small')
			.html(messagePacket.time + ' <i class="fa fa-check"></i>');
	}
	else if ( messagePacket.senderId != currentUserId )
	{
		if ( isGroupChat )
			addNewMessageCloud( createNewMessageCloudHtml( messagePacket, true ) );
		else
			messageReceivedCallback(messagePacket, false);
	}
	
}

window.onChatInit = function( textArea, userId, userIconSrc, otherId=null, isGroup=false ) 
{
	currentUserId = userId;
	currentUserIconSrc = userIconSrc;
	messageTextArea = textArea;
	isGroupChat = isGroup;
	
	// request previous stored messages
	if ( isGroup )
	{
		$.ajax(
			{
				type: "GET",
				url: 'getchat?groupid=' + otherId,
				dataType: "json",
				success: function(data)
					{
						data.forEach( function( msgPacket ) {
							addNewMessageCloud( createNewMessageCloudHtml( msgPacket, msgPacket.senderId != currentUserId, true), true );
						});
					}
			}
		);
	}
	else
	{
		$.ajax(
			{
				type: "GET",
				url: 'getchat?userid=' + userId,
				dataType: "json",
				success: function(data)
					{
						data.forEach( function( msgPacket ) {
							messageReceivedCallback(msgPacket, true);
						});
					}
			}
		);
	}
	
	chatInit( onChatMessageReceived, userId, otherId, isGroup );
}

window.onChatSend = function(otherUserId=-1) 
{
	var messageText = messageTextArea.val();
	if ( messageText == undefined )
		return;
	
	messageText = messageText.trim();
	
	if ( messageText.length )
	{
		const messagePacket = createNewMessagePacket(messageText, currentUserId, currentUserIconSrc, otherUserId);
		
		addNewMessageCloud( createNewMessageCloudHtml(messagePacket) );
		messageTextArea.val('');
		chatSend( messagePacket );
	}
}

window.onChatInfoSend = function(infoText) 
{
	if ( infoText == undefined )
		return;
	
	infoText = infoText.trim();
	
	if ( infoText.length )
	{
		const messagePacket = createNewInfoMessagePacket(infoText, currentUserId);
		chatSend( messagePacket );
	}
}

window.setOnMessageReceivedCallback = function(callback) 
{
	messageReceivedCallback = callback;
}