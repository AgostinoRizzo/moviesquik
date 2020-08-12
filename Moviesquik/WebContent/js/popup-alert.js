/**
 * 
 */

window.showMainAlert = function(alertText) 
{
	$("#main-alert").html(alertText + '<button type="button" class="close">&times;</button>');
	$("#main-alert").hide();
	$("#main-alert").slideDown('slow');
}

window.showIconMainAlert = function(iconSrc, alertText) 
{
	showMainAlert( '<img src="' + iconSrc + '" class="avatar-img card-list-avatar-img rounded-circle">&nbsp;&nbsp;' + alertText );
}