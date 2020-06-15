/**
 * 
 */

/* switch struct template
 
var SWITCH = 
	{ 
		status:false,   // default
		enabled:true,   // default
		tag_id:"#switch_id", 
		section_tag_id:"#section_id" 
	};

*/

window.disable_section = function(tag) 
{
	$(tag + " input").each( function() {
		$(this).val("");
		$(this).attr("disabled", true);
		$(this).removeAttr("required");
	});
	
	$(tag).css("opacity", "0.3");
}

window.enable_section = function(tag) 
{
	$(tag).find("input").each( function() {
		$(this).attr("disabled", false);
		$(this).attr("required", true);
	});
	
	$(tag).css("opacity", "1.0");
}


window.clearAndDisableSwitch = function( sw )
{
	if ( sw.status )
		{
			$(sw.tag_id).trigger("click");
			manageSwitchToggle( sw, null );
		}
	
	$(sw.tag_id).css("pointer-events", "none");
	sw.enabled = false;
}

window.enableSwitch = function( sw )
{
	$(sw.tag_id).css("pointer-events", "");
	sw.enabled = true;
}


window.updateSwitchStatus = function( sw, mouseup_event ) 
{
	//if ( mouseup_event.which != 1 )
	//	return;
	sw.status = $(sw.tag_id).find("input").prop("checked");
}

window.manageSwitchToggle = function( sw, mouseup_event ) 
{
	if ( !sw.enabled )
		{
			mouse_event.stopPropagation();
			return;
		}
	updateSwitchStatus( sw, mouseup_event );
	if ( sw.status )
		enable_section(sw.section_tag_id);
	else
		disable_section(sw.section_tag_id);
}