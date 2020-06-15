/**
 * 
 */

import '../widgets/switch.js';

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