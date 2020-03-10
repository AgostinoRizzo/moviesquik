/**
 * 
 */

var sendPostFeedbackUrl = "sendfeedback"

function sendPostFeedback(element, is_like) {
	
	element.unbind("click");
	media = element.closest(".media");
	
	var post_id = media.find("#post-id").val();
	
	$.ajax(
		{
			type: "POST",
			url: sendPostFeedbackUrl,
			data: { postid:post_id, islike: is_like ? 'true' : 'false' },
			success: function(data)
				{
					if ( data.added )
						element.closest(".media").find("#likes-loves-count")
							.html("&nbsp;&nbsp; " + data.nlikes + " likes, " + data.nloves + " loves");
					
					var popup = element.closest(".news-container").next("#post-feedback-popup");
					var popup_str = is_like ? 'Like feedback ' : 'Love feedback ';
					popup_str += data.added ? 'added.' : 'is already added.';
					
					popup.find(".modal-body").html("<p>" + popup_str + "</p>")
					popup.modal('show');
					
					element.bind("click");
					element.click(function() { sendPostFeedback(element, is_like); });
				}
		}
	);
	
}

$(document).ready( function()
	{	
		$(document).on("click", ".add-like", function() { sendPostFeedback($(this), true); });
		$(document).on("click", ".add-love", function() { sendPostFeedback($(this), false); });
	}
);