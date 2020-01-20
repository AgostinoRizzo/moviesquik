/**
 * 
 */

function follow(otherUserId) {
	$.ajax(
			{
				type: "GET",
				url: "friendship?action=follow&user_id=" + otherUserId.toString(),
				success: function(data)
					{					
						$("#follow_btn").addClass("d-none");
						$("#request-sent-btn").removeClass("d-none");
					}
			}
		);
}

$(document).ready( function()
	{
		var otherUserId = $("#friendship-other-user-id").val();
		//$("#follow_btn").onclick(follow(otherUserId));
	}
);