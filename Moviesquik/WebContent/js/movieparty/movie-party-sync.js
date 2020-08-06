/**
 * 
 */

const WEB_APPLICATION_NAME = "Moviesquik";
const PAGE_UPDATE_BASE_URL = "movieparty";

var webSocket;
var mediaStarted;

window.setMediaStartedFlag = function(flag) 
{
	mediaStarted = flag;
}

window.onJoin = function() 
{
	var partyId = $("#party-id").val();
	if ( partyId != undefined )
		location.href = 'watch?key=' + partyId + '&party=true';
}

function onPageUpdate() 
{
	var prevChatContainerHtml = $("#movie-party-chat-container").html();
	
	$.ajax(
		{
			type: "GET",
			url: PAGE_UPDATE_BASE_URL + '?key=' + $("#party-id").val() + '&update=true',
			dataType: "html",
			success: function(data)
				{
					$("#movie-party-page-container").html(data);
					if ( prevChatContainerHtml != undefined )
						$("#movie-party-chat-container").html(prevChatContainerHtml);
					
					if ( !mediaStarted && $("#join-btn").length )
					{
						onJoin();
						mediaStarted = true;
					}
				}
		}
	);
}

function onOpen(event) 
{
	webSocket.send("3535 3569");
}
function onMessage(event) 
{
	var message = event.data.trim();
	console.log(event.data);
	
	if ( message == 'update' )
		onPageUpdate();
}
function onClose(event) 
{
	console.log("Connection closed by the server.");
}

window.openSocket = function()
{
	if ( webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED )
		return;
	
	webSocket = new WebSocket('ws://' + location.host + '/' + WEB_APPLICATION_NAME + '/movieparty/sync');
	
	webSocket.onopen = onOpen;
	webSocket.onmessage = onMessage;
	webSocket.onclose = onClose;
}