/**
 * 
 */

const WEB_APPLICATION_NAME = "Moviesquik";

var notificationsWebSocket;
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

function isNotificationsWebSocketOn() 
{
	return notificationsWebSocket !== undefined && notificationsWebSocket.readyState !== WebSocket.CLOSED;
}

window.notificationsManagerInit = function(recvCallback, userId) 
{
	onMessageRecvCallback = recvCallback;
	
	if ( isNotificationsWebSocketOn() )
		return;
	
	var wsUrl = 'ws://' + location.host + '/' + WEB_APPLICATION_NAME + '/notifsocket/' + userId;
		
	notificationsWebSocket = new WebSocket( wsUrl );
	
	notificationsWebSocket.onopen = onOpen;
	notificationsWebSocket.onmessage = onMessage;
	notificationsWebSocket.onclose = onClose;
}
