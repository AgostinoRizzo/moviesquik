/**
 * 
 */

import '../streaming/stream-player.js';

const WEB_APPLICATION_NAME = "Moviesquik";

var partyIntervalWebSocket;
var userId;
var partyId;
var intervalBtnEnabled = true;

function addClassToTagObject( classString, tagId ) 
{
	if ( !$(tagId).hasClass(classString) )
		$(tagId).addClass(classString);
}
function removeClassToTagObject( classString, tagId ) 
{
	if ( $(tagId).hasClass(classString) )
		$(tagId).removeClass(classString);
}

function onPartyIntervalPacketReceived(intervalPacket) 
{
	if ( intervalPacket.isRequest )
		return;
	
	if ( intervalPacket.interval )
	{
		addClassToTagObject('d-none', '#player');
		removeClassToTagObject('d-none', '#player-interval');
		onIntervalStart($('#interval-btn'), false);
	}
	else
	{
		addClassToTagObject('d-none', '#player-interval');
		removeClassToTagObject('d-none', '#player');
		onIntervalEnd($('#interval-btn'), false);
	}
	
	intervalBtnEnabled = true;
	onPlayerIntervalStateChange(intervalPacket.interval);
}

function onOpen(event) 
{
	
}
function onMessage(event) 
{
	var jsonMessageData = event.data.trim();
	var messagePacket = JSON.parse(jsonMessageData);
	onPartyIntervalPacketReceived( messagePacket );
}
function onClose(event) 
{
	
}

function isPartyIntervalWebSocketOn() 
{
	return partyIntervalWebSocket !== undefined && partyIntervalWebSocket.readyState !== WebSocket.CLOSED;
}

function initPartyIntervalWebSocket(partyId) 
{	
	if ( isPartyIntervalWebSocketOn() )
		return;
	
	var wsUrl = 'ws://' + location.host + '/' + WEB_APPLICATION_NAME;
	wsUrl += '/movieparty/interval/' + partyId;
		
	partyIntervalWebSocket = new WebSocket( wsUrl );
	
	partyIntervalWebSocket.onopen = onOpen;
	partyIntervalWebSocket.onmessage = onMessage;
	partyIntervalWebSocket.onclose = onClose;
}

function sendPartyIntervalState(senderId, state) 
{
	if ( !isPartyIntervalWebSocketOn() )
		return;
	
	var intervalPacket = { "senderId": senderId, "isRequest": true, "interval": state };
	const jsonIntervalPacket = JSON.stringify( intervalPacket );
	partyIntervalWebSocket.send(jsonIntervalPacket);
}

function onIntervalStart(intervalBtn, sendRequest=true) 
{
	intervalBtn.removeClass('btn-secondary');
	intervalBtn.addClass('btn-warning');
	intervalBtnEnabled = false;
	
	if ( sendRequest )
		sendPartyIntervalState(userId, true);
}

function onIntervalEnd(intervalBtn, sendRequest=true) 
{
	intervalBtn.removeClass('btn-warning');
	intervalBtn.addClass('btn-secondary');
	intervalBtnEnabled = false;
	
	if ( sendRequest )
		sendPartyIntervalState(userId, false);
}

$(document).ready( function()
	{
		userId = $('#user-id').val();
		partyId = $('#party-id').val();
		
		initPartyIntervalWebSocket(partyId);
		
		$('#interval-btn').click( function() 
		{
			if ( intervalBtnEnabled )
				$(this).hasClass('btn-secondary') ? onIntervalStart($(this)) : onIntervalEnd($(this));
		});
	}
);