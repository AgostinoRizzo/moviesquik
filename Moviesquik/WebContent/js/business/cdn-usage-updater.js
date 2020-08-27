/**
 * 
 */

const WEB_APPLICATION_NAME = "Moviesquik";
const CDN_USAGE_UPDATER_WEBSOCKET_URL = '/business/cdn/usage/update';

var cdnUsageWebSocket;
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

function isCDNUsageWebSocketOn() 
{
	return cdnUsageWebSocket !== undefined && cdnUsageWebSocket.readyState !== WebSocket.CLOSED;
}

window.cdnUsageUpdaterInit = function(recvCallback) 
{
	onMessageRecvCallback = recvCallback;
	
	if ( isCDNUsageWebSocketOn() )
		return;
	
	var wsUrl = 'ws://' + location.host + '/' + WEB_APPLICATION_NAME + CDN_USAGE_UPDATER_WEBSOCKET_URL;
	
	cdnUsageWebSocket = new WebSocket( wsUrl );
	
	cdnUsageWebSocket.onopen = onOpen;
	cdnUsageWebSocket.onmessage = onMessage;
	cdnUsageWebSocket.onclose = onClose;
}

window.cdnUsageUpdaterFinalize = function() 
{
	if ( !isCDNUsageWebSocketOn() )
		return;
	cdnUsageWebSocket.close();
}