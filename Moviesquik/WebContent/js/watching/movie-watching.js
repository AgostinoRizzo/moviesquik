/**
 * 
 */

const MOVIE_PARTY_CONTAINER = "#movie-party-chat-container";
const SHOW_CHAT_BTN = "#watching-chat-btn";
const HIDE_CHAT_BTN = "#hide-chat-btn";

function onShowChat() 
{
	if ( !$(MOVIE_PARTY_CONTAINER).is(':visible') )
		{
			$(SHOW_CHAT_BTN).hide();
			$(MOVIE_PARTY_CONTAINER).show("slow", function() {
				$(HIDE_CHAT_BTN).show();
			});
		}
}
function onHideChat() 
{
	$(HIDE_CHAT_BTN).hide();
	if ( $(MOVIE_PARTY_CONTAINER).is(':visible') )
		{
			$(MOVIE_PARTY_CONTAINER).hide("slow", function() {
				$(SHOW_CHAT_BTN).show();
			});
		}
}

$(document).ready( function()
	{
		$(MOVIE_PARTY_CONTAINER).hide();
		$(HIDE_CHAT_BTN).hide();
		$(document).on("click", SHOW_CHAT_BTN, onShowChat);
		$(document).on("click", HIDE_CHAT_BTN, onHideChat);
	});