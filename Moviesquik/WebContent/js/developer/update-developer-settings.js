/**
 * 
 */

const DEVELOPER_SETTING_UPDATE_URL = 'developer/settings/edit'
const PAGE_CONTAINER_ID = '#developer-settings-container';
const PAGE_LOADER_HTML = '<div class="loader loader-sm"></div>';

function onSettingUpdate(action, subject) 
{
	$(PAGE_CONTAINER_ID).html(PAGE_LOADER_HTML);
	
	$.ajax(
		{
			type: 'GET',
			url: DEVELOPER_SETTING_UPDATE_URL + '?action=' + action + '&subject=' + subject,
			dataType: "html",
			success: function(data)
				{
					$(PAGE_CONTAINER_ID).html(data);
				}
		}
	);
}

$(document).ready(function() 
	{
		$(document).on('click', '#activate-api-key-btn',   function() { onSettingUpdate('activate', 'api_key');   });
		$(document).on('click', '#deactivate-api-key-btn', function() { onSettingUpdate('deactivate', 'api_key'); });
		
		$(document).on('click', '#activate-assistant-service-key-btn', function()   { onSettingUpdate('activate', 'assistant_service_key');   });
		$(document).on('click', '#deactivate-assistant-service-key-btn', function() { onSettingUpdate('deactivate', 'assistant_service_key'); });
		
		$(document).on('click', '#enable-play-action', function()  { onSettingUpdate('activate', 'play_action');   });
		$(document).on('click', '#disable-play-action', function() { onSettingUpdate('deactivate', 'play_action'); });
	}
);