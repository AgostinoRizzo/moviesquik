/**
 * 
 */

import './html-post-broker.js';

const FACEBOOK_API_APP_ID = '325081788542228';
const ACCESS_TOKEN = 'EAAEnqQCHbRQBACPZAZCrpFp6tlEQGMHffrZCdkzLyZCSrI141uCZCNAg7o2KsXsUCUWWIZCvNWITl0yKqLFfJqMVLCNWKgwB7DTzSxFm6HEVP1WGzfPUoo3HnLBXsx4K7PjCBs7M1pEzN9tGp9aVCZCFBM6bbjFyn5Fe6r9dpXZA8ZAZCJHgEsrhVtKKdZBVbR8ZCdd0ZBPZC65ZCrMZAzsf2ZAG5wQVJ';
const FACEBOOK_PAGE_ID = '101419298349489';
const FETCH_FB_PAGE_POSTS_COMMENTS_EVENT_NAME = 'fetch_fb_posts_comments';
const INSERT_FB_PAGE_POSTS_EVENT_NAME = 'insert_fb_posts';

var facebookPagePosts = [];

window.fbAsyncInit = function() 
{
	FB.init({
		appId 	: FACEBOOK_API_APP_ID,
		status	: true,
		xfbml	: true,
		version	: 'v8.0'
	});
	
	FB.getLoginStatus( function(response) 
	{
		if ( response.error )
			return;
		if ( response.status == 'connected' )
			console.log("connected");
	});
}

function addFacebookPostAttachment(post, attachmentResponseData) 
{
	if ( !attachmentResponseData )
		return;
	if ( attachmentResponseData.description ) post.description = attachmentResponseData.description;
	if ( attachmentResponseData.media ) post.media = attachmentResponseData.media;
	if ( attachmentResponseData.target ) post.target = attachmentResponseData.target;
}

function addFacebookPostComments(post, commentResponseData) 
{
	if ( !commentResponseData )
		return;
	if ( !post.comments )
		post.comments = [];
	post.comments.push(commentResponseData);
}

function addFacebookPagePost(postData) 
{
	//alert(JSON.stringify(postData));
	
	var post = {};
	post.id = postData.id;
	post.createdTime = postData.created_time;
	
	if ( postData.message )
		post.message = postData.message;
	
	// finally add the new post to the collection
	facebookPagePosts.push(post);
}

function fetchFacebookPagePosts() 
{
	FB.api('/' + FACEBOOK_PAGE_ID + '/feed', {access_token : ACCESS_TOKEN}, function(response) 
		{
			if ( response.error )
				return;
			
			if ( response.data.length )
			{
				for ( var ifeed in response.data )
					addFacebookPagePost( response.data[ifeed] );
				fetchAllFacebookPagePostAttachments();
			}
		}
	);
}

function fetchFacebookPagePostAttachments(post, isLast) 
{
	FB.api('/' + post.id + '/attachments', {access_token : ACCESS_TOKEN}, function(response) 
		{
			if ( response.error )
				return;
			if ( response.data )  // take just the first attachment
				addFacebookPostAttachment( post, response.data[0] );
			if ( isLast )
				document.dispatchEvent( new Event(FETCH_FB_PAGE_POSTS_COMMENTS_EVENT_NAME) );
		}
	);
}

function fetchAllFacebookPagePostAttachments() 
{
	for ( var i in facebookPagePosts )
	{
		var post = facebookPagePosts[i];
		var isLast = i == facebookPagePosts.length - 1;
		
		fetchFacebookPagePostAttachments(post, isLast);
	}
}

function fetchFacebookPagePostComments(post, isLast) 
{
	FB.api('/' + post.id + '/comments', {access_token : ACCESS_TOKEN}, function(response) 
		{
			if ( response.error )
				return;
			if ( response.data )
				for ( var i in response.data )
					addFacebookPostComments( post, response.data[i] );
			if ( isLast )
				document.dispatchEvent( new Event(INSERT_FB_PAGE_POSTS_EVENT_NAME) );
		}
	);
}


function fetchFacebookPagePostsComments() 
{
	for ( var i in facebookPagePosts )
	{
		var post = facebookPagePosts[i];
		var isLast = i == facebookPagePosts.length - 1;
		
		post.comments = [];
		fetchFacebookPagePostComments(post, isLast);
	}
}

function insertFacebookPagePosts() 
{
	//alert( facebookPagePosts );
	
	for ( var i in facebookPagePosts )
	{
		var post = facebookPagePosts[i];
		var postHtml = createFacebookPostHtml(post, FACEBOOK_PAGE_ID);
		
		// appent the html to the posts list
		$('#news-list').closest('#posts-column').find('.fb-posts-loader').addClass('d-none');
		$('#news-list').prepend(postHtml);
	}
}

$(document).ready( function()
	{
		// TODO: fetch access token, page id using AJAX
		document.addEventListener(FETCH_FB_PAGE_POSTS_COMMENTS_EVENT_NAME, fetchFacebookPagePostsComments);
		document.addEventListener(INSERT_FB_PAGE_POSTS_EVENT_NAME, insertFacebookPagePosts);
		fetchFacebookPagePosts();
	}
);