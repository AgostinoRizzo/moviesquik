/**
 * 
 */

import '../widgets/switch.js';

var PUBLIC_PARTY_SWITCH = 
	{ 
		status:false,
		enabled:true,
		tag_id:"#public-party-switch", 
		section_tag_id:"#guests-selection-form" 
	};

function updateSwitchStatus( sw, mouseup_event ) 
{
	sw.status = $(sw.tag_id).find("input").prop("checked");
}


var selectedMediaContentId = null;
var searchedUsers = new Map();
var invitedUsers = new Map();

function onUserGuestSearch(searchQuery) 
{
	$("#guests-selection-form .loader").removeClass("d-none");
	
	$.ajax(
		{
			type: "GET",
			url: "lookup?type=user&friends=true&query=" + encodeURI(searchQuery),
			dataType: "json",
			success: function(data)
				{
					searchedUsers.clear();
					
					$("#user-guests-search-result").html('');
					
					data.forEach( function(user, index) 
					{
						if ( user.profileimg == null || !user.profileimg.length )
							user.profileimg = "res/drawable/user_avatar.jpg";
						else
							user.profileimg = user.profileimg;
						
						searchedUsers.set(user.id.toString(), user);
						
						var html = "<div class=\"col-auto col-light-left\">" + 
										"<img src=\"" + user.profileimg + "\" class=\"avatar-img card-list-avatar-img rounded-circle\">" +
									"</div>" + 
									"<div class=\"col users-list-name col-light-right\">" +
										"<p class=\"users-list-name-text\">" + user.fullname + "</p>" +
										"<p class=\"note\">" + user.email + "</p>" +
									"</div>";
						
						html = "<input type=\"hidden\" class=\"user-id\" value=\"" + user.id + "\">" + html;
						html = "<div class=\"col-auto\"><div class=\"row clickable user-guest-item\">" + html + "</div></div>";
						
						$("#user-guests-search-result").html($("#user-guests-search-result").html() + html);
					});
					
					$("#guests-selection-form .loader").addClass("d-none");
				}
		}
	);
}

function onUserGuestAdd(userId) 
{	
	if ( invitedUsers.has(userId) )
	{
		alert("User already invited!");
		return;
	}
	
	var user = searchedUsers.get(userId);
	invitedUsers.set(userId, user);
	
	$("#invited-users-list").html('');
	
	for( let user of invitedUsers.values() )
	{
		var html = "<div class=\"col-auto col-light-left\">" + 
						"<img src=\"" + user.profileimg + "\" class=\"avatar-img card-list-avatar-img rounded-circle\">" +
					"</div>" + 
					"<div class=\"col users-list-name col-light-right\">" +
						"<p class=\"users-list-name-text\">" + user.fullname + "</p>" +
						"<p class=\"note\">" + user.email + "</p>" +
					"</div>";
		
		html = "<input type=\"hidden\" class=\"user-id\" value=\"" + user.id + "\">" + html;
		html = "<div class=\"col-auto\"><div class=\"row clickable added-user-guest-item\">" + html + "</div></div>";
		
		$("#invited-users-list").html($("#invited-users-list").html() + html);
	}
	
	$("#invited-firends-note").text("Select a guest to remove.");
}

function onUserGuestRemove(userId, colTag) 
{
	invitedUsers.delete(userId);
	colTag.remove();
	
	if ( invitedUsers.size == 0 )
		$("#invited-firends-note").text("No invited friends for this event. Please select at least one guest.");
}


function onCreatePartySubmit() 
{
	if ( selectedMediaContentId == null )
	{
		alert("Please select a Movie or TV Show to watch during the event!");
		return false;
	}
	var isPrivateEvent = !PUBLIC_PARTY_SWITCH.status;
	if ( isPrivateEvent && invitedUsers.size == 0 )
	{
		alert("Please invite at least one friend to share watching during the event!");
		return false;
	}
	
	var guestsIds = [];
	if ( isPrivateEvent )
		for ( let guest of invitedUsers.values() )
			guestsIds.push(guest.id);
	
	$(this).append('<input type="hidden" name="choosen-media-content" value="' + selectedMediaContentId + '"/>');
	$(this).append('<input type="hidden" name="invited-users" value="' + JSON.stringify(guestsIds) + '"/>');
	
	return true;
}

$(document).ready( function() 
{
	$(PUBLIC_PARTY_SWITCH.tag_id).click(function(event) {
		updateSwitchStatus( PUBLIC_PARTY_SWITCH, event );
		if ( PUBLIC_PARTY_SWITCH.status )
		{
			$(PUBLIC_PARTY_SWITCH.section_tag_id).addClass("d-none");
			$("#public-event-switch-note").html
				("This event will be <span class=\"badge badge-info\">public</span><br>Anyone can participate.");
		}
		else
		{
			$(PUBLIC_PARTY_SWITCH.section_tag_id).removeClass("d-none");
			$("#public-event-switch-note").html
				("This event will be <span class=\"badge badge-info\">private</span><br>Only invited friends can participate.");
		}
	});
	
	
	$(".card-img-top").click( function() 
	{
		var mediaContentId = $(this).closest(".media-content-card").find("#media-id").val();
		var listItems = $(this).closest(".media-contents-list-items");
		
		listItems.find(".card-img-top").css("opacity", "0.3");
		$(this).css("opacity", "1.0");
		
		selectedMediaContentId = mediaContentId;
	});
	
	
	document.getElementById("user-name-query").addEventListener("keyup", function(event) 
	{
		if ( event.keyCode === 13 )
		{
			event.preventDefault();
			$("#user-guests-search-btn").click();
		}
	});
	
	$("#user-guests-search-btn").click( function() 
	{
		var searchQuery = $("#user-name-query").val().trim();
		if ( !searchQuery.length )
		{
			alert("Please enter a friend name to search!");
			return;
		}
		onUserGuestSearch(searchQuery);
	});
	
	$(document).on("click", ".user-guest-item", function() 
	{
		onUserGuestAdd($(this).find(".user-id").val());
	});
	
	$(document).on("click", ".added-user-guest-item", function() 
	{
		onUserGuestRemove($(this).find(".user-id").val(), $(this).closest(".col-auto"));
	});
	
	
	$("#create-party-form").submit( onCreatePartySubmit );
	
	
	onUserGuestSearch('');
	
});
