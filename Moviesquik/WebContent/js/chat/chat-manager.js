/**
 * 
 */

const WEB_APPLICATION_NAME = "Moviesquik";

var chatWebSocket;
var onMessageRecvCallback;

function onOpen(event) 
{
	
}
function onMessage(event) 
{
	var jsonMessageData = event.data.trim();
	var messagePacket = JSON.parse(jsonMessageData);
	onMessageRecvCallback( messagePacket );
}
function onClose(event) 
{
	
}

function isChatWebSocketOn() 
{
	return chatWebSocket !== undefined && chatWebSocket.readyState !== WebSocket.CLOSED;
}

window.chatInit = function(recvCallback, userId, otherId, isGroup=false) 
{
	onMessageRecvCallback = recvCallback;
	
	if ( isChatWebSocketOn() )
		return;
	
	var wsUrl = 'ws://' + location.host + '/' + WEB_APPLICATION_NAME;
	if ( isGroup ) wsUrl += '/groupchat/' + otherId + '/' + userId;
	else           wsUrl += '/userchat/' + userId;
		
	chatWebSocket = new WebSocket( wsUrl );
	
	chatWebSocket.onopen = onOpen;
	chatWebSocket.onmessage = onMessage;
	chatWebSocket.onclose = onClose;
}

window.chatSend = function(messagePacket) 
{
	if ( !isChatWebSocketOn() )
		return;
	
	const jsonMessagePacket = JSON.stringify( messagePacket );
	chatWebSocket.send(jsonMessagePacket);
}