/**
 * 
 */

import '../chat/chat-manager.js';

var currentUserId;
var currentUserIconSrc;
var messageTextArea;
var chatMessageCounter = 0;

function getNextChatMessageId() 
{
	const id = chatMessageCounter;
	chatMessageCounter++;
	return id;
}

function addNewMessageCloud( messagePacket, received=false ) 
{
	var chatContent =$("#chat-content");
	const messageText = messagePacket.text.replace(/\n/g, '<br>');
	const html = received 
			?
			'<div class="chat-message-container other-chat-message-container">' +
				
				'<div class="chat-message-cloud">' + messageText + 
					'<div class="chat-message-cloud-info">' +
						'<small>' + messagePacket.time + '</small>' +
					'</div>' +
				'</div>' +
				
				'<img src="' + messagePacket.senderIconSrc + '" class="avatar-img rounded-circle">' +
				
			'</div>' 
			:
			'<div class="chat-message-container my-chat-message-container" id="chat-message-' + messagePacket.id + '">' +
				
				'<div class="chat-message-cloud">' + messageText + 
					'<div class="chat-message-cloud-info">' +
						'<small>sending... <i class="fa fa-cloud-upload"></i></small>' +
					'</div>' +
				'</div>' +
				
				'<img src="' + currentUserIconSrc + '" class="avatar-img rounded-circle">' +
				
			'</div>';
	
	chatContent.prepend(html);
}

function createNewMessagePacket( messageText, userId, userIconSrc ) 
{
	return { text : messageText, id : getNextChatMessageId(), ack : false, time : '', senderId : userId, senderIconSrc : userIconSrc };
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
		addNewMessageCloud(messagePacket, true);
	}
	
}

window.onChatInit = function( textArea, userId, userIconSrc, otherId, isGroup=false ) 
{
	currentUserId = userId;
	currentUserIconSrc = userIconSrc;
	messageTextArea = textArea;
	chatInit( onChatMessageReceived, userId, otherId, isGroup );
}

window.onChatSend = function() 
{
	var messageText = messageTextArea.val();
	if ( messageText == undefined )
		return;
	
	messageText = messageText.trim();
	
	if ( messageText.length )
	{
		const messagePacket = createNewMessagePacket(messageText, currentUserId, currentUserIconSrc);
		
		addNewMessageCloud(messagePacket);
		messageTextArea.val('');
		chatSend( messagePacket );
	}
}