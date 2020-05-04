/**
 * 
 */

var PERSONAL_EMAIL_SWITCH = 
	{ 
		status:false,
		enabled:true,
		tag_id:"#personal-email-switch", 
		section_tag_id:"#personal-email-form" 
	};

var PROFILE_PASSWORD_SWITCH = 
	{ 
		status:false, 
		enabled:true,
		tag_id:"#profile-protection-switch", 
		section_tag_id:"#profile-password-form" 
	};

var KIDS_PROFILE_SWITCH = 
	{ 
		status:false, 
		enabled:true,
		tag_id:"#kids-profile-switch", 
	};

var ADD_PROFILE_FORM_TAG_ID = "#addprofileform";


function disable_section(tag) 
{
	$(tag + " input").each( function() {
		$(this).val("");
		$(this).attr("disabled", true);
		$(this).removeAttr("required");
	});
	
	$(tag).css("opacity", "0.3");
}

function enable_section(tag) 
{
	$(tag).find("input").each( function() {
		$(this).attr("disabled", false);
		$(this).attr("required", true);
	});
	
	$(tag).css("opacity", "1.0");
}


function clearAndDisableSwitch( sw )
{
	if ( sw.status )
		{
			$(sw.tag_id).trigger("click");
			manageSwitchToggle( sw, null );
		}
	
	$(sw.tag_id).css("pointer-events", "none");
	sw.enabled = false;
}

function enableSwitch( sw )
{
	$(sw.tag_id).css("pointer-events", "");
	sw.enabled = true;
}


function updateSwitchStatus( sw, mouseup_event ) 
{
	//if ( mouseup_event.which != 1 )
	//	return;
	sw.status = $(sw.tag_id).find("input").prop("checked");
}

function manageSwitchToggle( sw, mouseup_event ) 
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


$(document).ready( function()
	{		
		disable_section(PERSONAL_EMAIL_SWITCH.section_tag_id);
		disable_section(PROFILE_PASSWORD_SWITCH.section_tag_id);
		
		$(PERSONAL_EMAIL_SWITCH.tag_id).click(function(event) { manageSwitchToggle( PERSONAL_EMAIL_SWITCH, event ); });
		$(PROFILE_PASSWORD_SWITCH.tag_id).click(function(event) { manageSwitchToggle( PROFILE_PASSWORD_SWITCH, event ); });
		
		$(KIDS_PROFILE_SWITCH.tag_id).click(function(event) {
			updateSwitchStatus( KIDS_PROFILE_SWITCH, event );
			if ( KIDS_PROFILE_SWITCH.status )
			{
				clearAndDisableSwitch(PERSONAL_EMAIL_SWITCH);
				clearAndDisableSwitch(PROFILE_PASSWORD_SWITCH);
			}
			else
			{
				enableSwitch(PERSONAL_EMAIL_SWITCH);
				enableSwitch(PROFILE_PASSWORD_SWITCH);
			}
		});
		
		$(ADD_PROFILE_FORM_TAG_ID).submit( function() {
			updateSwitchStatus( PERSONAL_EMAIL_SWITCH, null );
			updateSwitchStatus( PROFILE_PASSWORD_SWITCH, null );
			updateSwitchStatus( KIDS_PROFILE_SWITCH, null );
			
			if ( !PERSONAL_EMAIL_SWITCH.status )
				disable_section(PERSONAL_EMAIL_SWITCH.section_tag_id);
			if ( !PROFILE_PASSWORD_SWITCH.status )
				disable_section(PROFILE_PASSWORD_SWITCH.section_tag_id);
			
			var is_kid = KIDS_PROFILE_SWITCH.status ? "true" : "false";
			
			$("<input />")
				.attr("type", "hidden")
				.attr("name", "kid")
				.attr("value", is_kid)
				.appendTo(ADD_PROFILE_FORM_TAG_ID);
		});
		
	});