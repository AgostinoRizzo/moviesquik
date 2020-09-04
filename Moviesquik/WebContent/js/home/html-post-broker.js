/**
 * 
 */

const POST_TIME_REGEXP = /^(\d{4}-\d{2}-\d{2})T(\d{2}:\d{2}:\d{2})\+\d+/;

function getHumanReadablePostTime(time) 
{
	var fields = POST_TIME_REGEXP.exec(time);
	if ( fields.length >= 3 )
		return fields[1] + ' at ' + fields[2];
	return '';
}

function getFacebookPostCommentsHtml(post, currentPageId) 
{
	var html = '';
	for ( var i in post.comments )
	{
		var comment = post.comments[i];
		const isCommentFromFbPage = comment.from && comment.from.id == currentPageId ;
		
		html +=
	    	'<div class="media p-3 row media-post-box media-comment-box">' +
				  '<div class="media-body">' +
				  		'<div class="row">' +
					  		'<div class="col-auto col-light-left">' + 
					  			'<img src="' + (isCommentFromFbPage
					  							? 'res/drawable/m_logo.png" width="30px" class="avatar-img rounded-circle">' 
					  							: 'res/drawable/comment_icon.png" width="30px" class="comment-icon-img">') +
					  			(isCommentFromFbPage ? '<img src="res/drawable/fb_logo.png" class="m-page-fb-logo">' : '') +
					  		'</div>' +
						    
						    '<div class="col header-text col-light-right">' +
						    	//comment.message +
						    	//'&nbsp;' +
						    	'<small class="note" data-toggle="tooltip" data-placement="top" title="">Posted ' + getHumanReadablePostTime(comment.created_time) + '</small>' +
						    	'<p class="post-text">' + comment.message + '</p>' +
						    '</div>' +
					    '</div>' +
					    
				  '</div>' +
			'</div>';
	}
	
	if ( html.length )
		html = '<div class="dropdown-divider"></div><div class="comments-list">' + html + '</div>';
	
	return html;
}

window.createFacebookPostHtml = function(post, currentPageId, fbPageUrl) 
{
	const html = 
		'<div class="media p-3 media-post-box main-media-post-box tile">' +
		  '<div class="media-body">' +
		  		'<div class="row">' +
			  		'<div class="col-auto col-light-left">'+
			  			'<a href="' + fbPageUrl + '">' +
			  				'<img src="res/drawable/m_logo.png" class="avatar-img rounded-circle">' + 
			  			'</a>' +
			  			'<img src="res/drawable/fb_logo.png" class="m-page-fb-logo">' + 
			  		'</div>' +
				    '<div class="col col-light-right header-text">' +
				    	'<h6>Moviesquik <span class="fa fa-check-circle text-primary"></span></h6>' +
				    	'<small class="note" data-toggle="tooltip" data-placement="top" title="">Posted ' + getHumanReadablePostTime(post.createdTime) + '</small>' +
				    '</div>' +
			    '</div>' +
			    
			    (post.message ? '<p class="post-text">' + post.message + '</p>' : '') +
			    (post.description ? '<p class="post-text">' + post.description + '</p>' : '') +
			    
			    ( post.media && post.media.image ?
			    '<div class="card-image-container row">' +
						'<img class="post-media-src-item-card-img col" src="' + post.media.image.src + '">' +
				'</div><br>' : '' ) +
			    
			    '<div class="row" id="count-row">' +
			    	'<div class="col">' +
			    		'<small class="fa-container"><i class="fa fa-thumbs-up rounded-circle clickable"></i></small>' +
			    		'<small class="fa-container"><i class="fa fa-heart rounded-circle clickable"></i></small>' +
			    		'<small class="note" id="likes-loves-count"></small>' +
			    	'</div>' +
			    	'<div class="col" id="n-comments-col">' +
			    		'<small class="note show-comments-list-btn">' + post.comments.length + ' comments</small>' +
			    	'</div>' +
			    '</div>' +
			    
			    getFacebookPostCommentsHtml(post, currentPageId) +
			    
			    /*
			    <div class="dropdown-divider"></div>
			    
			    <div class="row post-action-row">
			    	<div class="col post-action"><p class="btn add-like"><i class="fa fa-thumbs-up"></i>&nbsp;&nbsp;Like<p></div>
			    	<div class="col post-action"><p class="btn comment-btn"><i class="fa fa-comment"></i>&nbsp;&nbsp;Comment<p></div>
			    	<form class="col post-action" method="POST" action="sharepost">
			    		<input type="hidden" name="share-post-id" value="${post_to_display.id}">
			    		<p class="btn share-post-submit-btn"><i class="fa fa-share"></i>&nbsp;&nbsp;Share<p>
			    	</form>
			    </div>
			    */
			    
			    
			    
				'</div>' +
			'</div>';
	
	return html;
}