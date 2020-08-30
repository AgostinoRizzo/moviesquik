/**
 * 
 */

import './notifications-manager.js';
import '../popup-alert.js';

const MOVIE_PARTY_NOTIFICATION_ICON_SRC = 'res/drawable/movie_party_icon.png';

var newNotificationsCount = 0;

function read_notifications(navbar) {
	
	var ajax_url = "notification?action=readall";
	
	$.ajax(
			{
				type: "GET",
				url: ajax_url,
				success: function(data)
					{
						var badge = navbar.find(".badge");
						if ( !badge.hasClass('invisible') )
							badge.addClass('invisible');
						badge.css("visibility", "hidden");
						newNotificationsCount = 0;
					}
			}
		);
}

function manageTriggerNotification(trigger) 
{
	if ( trigger.triggerError && trigger.triggerError.length )
	{
		// show trigger error alert
		showMainAlert( trigger.triggerError );
		return;
	}
	
	if ( trigger.triggerUrl && trigger.triggerUrl.length )
		window.location.href = trigger.triggerUrl;
}

function onNewNotification(notification) 
{	
	if ( notification.trigger )
	{
		manageTriggerNotification(notification);
		return;
	}
	
	var partyIcon = false;
	if ( !notification.iconSrc.length && notification.moviePartyId )
	{
		notification.iconSrc = MOVIE_PARTY_NOTIFICATION_ICON_SRC;
		partyIcon = true;
	}
	
	newNotificationsCount++;
	
	$(".notifications-navbar-nav .badge").text(newNotificationsCount.toString());
	if ( $(".notifications-navbar-nav .badge").hasClass('invisible') )
		$(".notifications-navbar-nav .badge").removeClass('invisible');
	$(".notifications-navbar-nav .badge").css("visibility", "visible");
	
	$("#nav-user-notifications-dropdown-menu").prepend
	(
		'<div class="notification-box">' +
		'<div class="row notification-box-row">' +
			'<div class="col-lg-1 col-sm-1 col-1 text-center">' +
				'<img src="' + notification.iconSrc + '" class="notification-avatar-img' + (partyIcon ? '' : ' rounded-circle') + '">' +
			'</div>' +
			
			'<div class="col-lg-10 col-sm-10 col-10 notification-body">' +
			'<div class="row">' +
				'<div class="col">' +
					'<strong class="">' + notification.title + '</strong>' +
						'&nbsp;&nbsp;<small class="new-notification-marker">New</small>' +
				'</div>' +
			'</div>' +
			'<p>' + notification.description + '</p>' +
			'<small class="note" title="' + notification.dateTime + '"' + notification.humanReadableDateTime + '</small>' +
			'</div>' +
		'</div>' +
		'</div>' +
		'<div class="dropdown-divider"></div>'
	);
	
	// show notification alert
	showIconMainAlert( notification.iconSrc, notification.description );
}

$(document).ready( function()
	{
		var currentUserId = parseInt( $("#user-id").val() );
		$("#notifications-menu-btn").click( function() { read_notifications($(this).closest(".navbar-nav")); } );
		notificationsManagerInit( onNewNotification, currentUserId );
	}
);