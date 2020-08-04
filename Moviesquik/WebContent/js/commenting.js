/**
 * 
 */

function submit_new_comment(post_id, comment_text, comment_form_tag)
{
	var ajax_url = "createcomment";
	
	var loader = comment_form_tag.prev(".media-post-box").find(".comments-list").find(".loader");
	loader.removeClass("d-none");
	
	comment_form_tag.find("#new-comment-text").val('');
	comment_form_tag.hide();
	
	comment_form_tag.prev(".media").find(".comment-btn").html("<i class=\"fa fa-comment\"></i>&nbsp;&nbsp;Comment");
	
	$.ajax(
		{
			type: "POST",
			url: ajax_url,
			data: { postid:post_id, text:comment_text },
			dataType: "html",
			success: function(data)
				{
					var media = comment_form_tag.prev(".media");
					var num_all_comments = $($.parseHTML(data)).filter("#num-comments-hidden").val();
					
					loader.addClass("d-none");
					media.find(".comments-list").html(data);
					media.find(".show-comments-list-btn").text(num_all_comments + ' comments');					
				}
		}
	);
};

//function get_comments_list(url_str, post_tag)
//{
//	var loader = post_tag.prev(".media-post-box").find(".loader");
//	var comments_list = post_tag.closest("media").find(".comments-list");
//	
//	loader.removeClass("d-none");
//	comments_list.html("");
//	
//	$.ajax(
//		{
//			type: "GET",
//			url: url_str,
//			dataType: "html",
//			success: function(data)
//				{
//					loader.addClass("d-none");
//					comments_list.html(data);
//				}
//		}
//	);
//};

function encodeToHtmlDecimal(str) {
	var encoded_str = '';
	for ( var i=0; i<str.length; ++i )
		{
			var code_point = str.codePointAt(i);
			if ( code_point <= 255 )
				encoded_str += str.charAt(i);
			else
				{
					encoded_str += '&#' + code_point + ';';
					++i;
				}
		}
	return encoded_str;
}

$(document).ready( function()
	{
		$(document).on("click", ".new-comment-submit-btn", function() 
		{
			var post_id = $(this).closest(".new-comment-media").prev(".media-post-box").find("#post-id").val();
			var comment_text = $(this).closest(".media").find(".new-comment-text").val();
			comment_text = comment_text.trim();
			comment_text = encodeToHtmlDecimal(comment_text);
			submit_new_comment(post_id, comment_text, $(this).closest(".media"));
			
		});
	
//		$(".new-comment-media").submit(function(event) {
//			event.preventDefault();
//			var ajax_url = $(this).attr("action");
//			var form_data = $(this).serialize();
//			
//			$.post(ajax_url, form_data, function(response) {
//				
//			})
//		})
		
//		$(".show-comments-list-btn").click(function() 
//		{
//			var comment_text = $(this).closest(".media").find(".new-comment-text").text().trim();
//			get_comments_list("getcomments?post_id=" + comment_text, $(this).closest(".media"));
//			
//		});
	}
);