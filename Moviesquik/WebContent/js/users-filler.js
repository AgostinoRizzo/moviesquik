/**
 * 
 */

function fill_family_users(contents_items_tag_id, family)
{
	$(contents_items_tag_id).empty();
	
	$(contents_items_tag_id).parent().parent().find(".card-header").text(family.name);
	
	for ( var i in family.members )
	{
		var member = family.members[i];
		var item_html = 
			"<div class=\"col-3 user-col-item\">" +
					"<a href=\"#\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"" + member.first_name + " " + member.last_name + "\">" +
						"<img src=\"" + "res/drawable/user_avatar.jpg" + "\" class=\"avatar-img card-list-avatar-img rounded-circle\">" +
					"</a>" +
					"<p>" + member.first_name + "</p>" +
			"</div>";
		$(contents_items_tag_id).append(item_html);
		
		$(contents_items_tag_id).parent().find(".view-all-btn").removeClass("invisible");
	}
};

function request_users(contents_items_tag_id, url_str, fill_callback)
{
	if ( $(contents_items_tag_id).length )
	{
		$.ajax(
			{
				type: "GET",
				url: url_str,
				dataType: "json",
				success: function(data)
					{					
						fill_callback(contents_items_tag_id, data);
					}
			}
		);
	}
};

$(document).ready( function()
	{
		/* users requests */
		
		request_users("#family-box", "getfamily", fill_family_users);
	}
);