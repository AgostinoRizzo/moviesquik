/**
 * 
 */

function onChatSearch(searchText) 
{
	$(".user-chat-row").each( function() 
	{
		const userFullName = $(this).find(".users-list-name-text").text().trim();
		if ( userFullName.search( new RegExp(searchText, "i") ) >= 0 )
			$(this).show();
		else
			$(this).hide();
	});
}

$(document).ready(function() 
	{
		$("#chats-sidenav-column #chat-search-input").on('input', function() 
		{
			const searchText = $(this).val();
			onChatSearch(searchText.trim());
		});
	}
);