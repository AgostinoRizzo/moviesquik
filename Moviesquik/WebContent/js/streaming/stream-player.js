/**
 * 
 */

import './stream-manager.js';

function removeIconClass(tagid, iconClass)
{
	var icontag = $(tagid).find(".fa");
	if ( $(icontag).hasClass(iconClass) )
		$(icontag).removeClass(iconClass);
}
function addIconClass(tagid, iconClass)
{
	$(tagid).find(".fa").addClass(iconClass);
}
function openFullscreen(tagid) 
{
	if      ( tagid.requestFullscreen )       { tagid.requestFullscreen();       }
	else if ( tagid.mozRequestFullscreen )    { tagid.mozRequestFullscreen();    }  // Firefox browser.
	else if ( tagid.webkitRequestFullscreen ) { tagid.webkitRequestFullscreen(); }  // Chrome, Safari and Opera browsers.
	else if ( tagid.msRequestFullscreen )     { tagid.msRequestFullscreen();     }  // IE and Edge browsers.
}
function closeFullscreen(tagid) 
{
	if      ( tagid.exitFullscreen )       { tagid.exitFullscreen();       }
	else if ( tagid.mozCancelFullscreen )  { tagid.mozCancelFullscreen();  }  // Firefox browser.
	else if ( tagid.webkitExitFullscreen ) { tagid.webkitExitFullscreen(); }  // Chrome, Safari and Opera browsers.
	else if ( tagid.msExitFullscreen )     { tagid.msExitFullscreen();     }  // IE and Edge browsers.
}

function PlayPauseButton(tagid, videotag)
{
	this.tagid = tagid;
	this.status = 'onplay';
	this.videotag = videotag;
	this.enabled = true;
	
	this.onClick = function()
	{
		this.status == 'onplay' ? this.onPause() 
								: this.onPlay();
	};
	this.onPlay = function()
	{
		if ( !this.enabled )
			return;
		
		this.removeAllIconClasses();
		addIconClass(this.tagid, "fa-pause");
		this.status = 'onplay';
		if ( this.videotag != null )
			this.videotag.play();
	};
	this.onPause = function()
	{
		if ( !this.enabled )
			return;
		
		this.removeAllIconClasses();
		addIconClass(this.tagid, "fa-play");
		this.status = 'onpause';
		if ( this.videotag != null )
			this.videotag.pause();
	};
	this.isOnPause = function() 
	{
		return this.status == 'onpause';
	};
	this.syncToVideoTag = function() 
	{
		if ( this.videotag.paused && !this.isOnPause() )
			this.onPause();
		else if ( !this.videotag.paused && this.isOnPause() )
			this.onPlay();
	};
	this.enable = function() 
	{
		this.enabled = true;
	};
	this.disable = function() 
	{
		this.enabled = false;
	};
	
	this.removeAllIconClasses = function()
	{
		removeIconClass(this.tagid, "fa-play");
		removeIconClass(this.tagid, "fa-pause");
	};

}


function ExpandCompressButton(tagid)
{
	this.tagid = tagid;
	this.status = 'expand';
	
	this.onClick = function() 
	{
		this.removeAllIconClasses();
		if ( this.status == 'expand' )
		{ 
			openFullscreen(document.documentElement); 
			$("body").css("overflow", "hidden");
			addIconClass(this.tagid, "fa-compress");
			this.status = 'compress'; 
		}
		else
		{ 
			closeFullscreen(document);
			$("body").css("overflow", "auto");
			addIconClass(this.tagid, "fa-expand");
			this.status = 'expand'; 
		}
	};
	
	this.removeAllIconClasses = function()
	{
		removeIconClass(this.tagid, "fa-expand");
		removeIconClass(this.tagid, "fa-compress");
	};
}


function VolumeControl(btntagid, volumeSliderRange, videotag)
{
	this.btntagid = btntagid;
	this.volumeSliderRange = volumeSliderRange;
	this.videotag = videotag;
	
	this.onClick = function() 
	{
		this.removeAllIconClasses();
		if ( volumeSliderRange.value > 0 )
		{ 
			volumeSliderRange.value = 0;
			addIconClass(this.btntagid, "fa-volume-off");
		}
		else
		{ 
			volumeSliderRange.value = 100; 
			addIconClass(this.btntagid, "fa-volume-up");
		}
		
		if ( this.videotag != null )
			this.videotag.volume = volumeSliderRange.value / 100.0;
	};
	
	this.onChange = function() 
	{
		this.removeAllIconClasses();
		if ( volumeSliderRange.value >= 50 )
			addIconClass(this.btntagid, "fa-volume-up");
		else if ( volumeSliderRange.value > 0 )
			addIconClass(this.btntagid, "fa-volume-down");
		else
			addIconClass(this.btntagid, "fa-volume-off");
		if ( this.videotag != null )
			this.videotag.volume = volumeSliderRange.value / 100.0;
	};
	
	this.removeAllIconClasses = function()
	{
		removeIconClass(this.btntagid, "fa-volume-up");
		removeIconClass(this.btntagid, "fa-volume-down");
		removeIconClass(this.btntagid, "fa-volume-off");
	};
}


function MediaTimeline(timelineTagId, backwardTimelineTagId, forwardTimelineTagId, cursorTagId, timingsTagId, videotag)
{
	this.timelineTagId = timelineTagId;
	this.backwardTimelineTagId = backwardTimelineTagId;
	this.forwardTimelineTagId = forwardTimelineTagId;
	this.cursorTagId = cursorTagId;
	this.timingsTagId = timingsTagId;
	this.videotag = videotag;
	this.timeOffset = 0;
	this.videoTagTimeOffset = 0;
	this.streamManager = null;
	
	this.getCurrentTime = function() 
	{
		return this.timeOffset + videotag.currentTime - this.videoTagTimeOffset;
	};
	
	this.updateCurrentTime = function(manifest, bufferedWindow) 
	{
		this.updateTime(this.getCurrentTime(), manifest, bufferedWindow);
	};
	
	this.updateSeekingVirtualTime = function(seekTime, manifest)
	{
		this.updateTime(seekTime * manifest.duration, manifest, 0);
	};
	
	this.updateTime = function(time, manifest, bufferedWindow) 
	{
		var fullCurrTime = time;  // expressed in seconds.
		var currTime = fullCurrTime + 1;
		var hours = Math.floor(currTime / 3600);
		currTime %= 3600;
		
		var minutes = Math.floor(currTime / 60);
		currTime %= 60;
		
		$(this.timingsTagId).text(this.getTimeString(hours) + ":" + 
								  this.getTimeString(minutes) + ":" + 
								  this.getTimeString(Math.floor(currTime)));
		
		var perc = (fullCurrTime / manifest.duration) * 100.0;
		if ( perc < 0.0 ) perc = 0.0;
		else if ( perc > 100.0 ) perc = 100.0;
		perc = Math.floor(perc);
		
		$(this.cursorTagId).css("left", "calc(" + perc + "% - 7px)");
		$(this.backwardTimelineTagId).css("width", perc + "%");
		
		var buffperc = (bufferedWindow / manifest.duration) * 100.0;
		if ( buffperc < 0.0 ) buffperc = 0.0;
		else if ( perc + buffperc > 100.0 ) buffperc = 100.0 - perc;
		buffperc = Math.floor(buffperc);
		
		$(this.forwardTimelineTagId).css("left", perc + "%");
		$(this.forwardTimelineTagId).css("width", buffperc + "%");
	};
	
	this.onSeek = function(e) 
	{
		var value = (e.clientX - $(this.timelineTagId).offset().left + 1) / 
					$(this.timelineTagId).width();
		if ( value < 0.0 ) value = 0.0;
		else if ( value > 1.0 ) value = 1.0;
		console.log("ON SEEK: " + value);
		this.streamManager.onSeek(value);
	};
	
	this.getTimeString = function(time)
	{
		if ( time < 10 )
			return "0" + time.toString();
		return time.toString();
	};
}


function VideoLoader(videoLoaderTagId) 
{
	this.tagid = videoLoaderTagId;
	
	this.show = function() 
	{
		$(this.tagid).show();
	};
	
	this.hide = function() 
	{
		$(this.tagid).hide();
		
		//if ( $('#interval-btn-container').length && $('#interval-btn-container').hasClass('d-none') )
		//	$('#interval-btn-container').removeClass('d-none');
	};
	
	this.showLoadingError = function() 
	{
		$("#no-connection-modal").modal('show');
	};
	
	this.showServersError = function() 
	{
		$("#no-servers-modal").modal('show');
	};
}


var playPauseBtnTag = "#play-pause-btn";
var expandCompressBtnTag = "#expand-compress-btn";
var volumeBtnTag = "#volume-btn";
var volumeSliderTag = "volume-slider";
var timelineTag = "#timeline";
var backwardTimelineTag = "#backward-timeline";
var forwardTimelineTag = "#forward-timeline";
var cursorTag = "#cursor";
var videoTimingsTag = "#video-timings";
var videoLoaderTag = "#video-loader-wrapper";

var playPauseBtn = null;
var expandCompressBtn = null;
var volumeControl = null;
var volumeSliderRange = null;
var mediaTimeline = null;
var videoLoader = null;
var streamManager = null;

$(document).ready( function()
	{
		/* player controls setup. */
	
		var videotag = document.querySelector("video");
		
		// current timestamp for movie party streaming
		var currTimestamp = $("#curr-timestamp").val();
		var multicasting = currTimestamp != undefined;
		if ( multicasting )
			currTimestamp = currTimestamp/1000.0;
		
		// play/pause button setup.
		playPauseBtn = new PlayPauseButton(playPauseBtnTag, videotag);
		playPauseBtn.onPause();
		$(playPauseBtnTag).click( function() { if (!multicasting) playPauseBtn.onClick(); });
		
		// expand/reduce button setup.
		expandCompressBtn = new ExpandCompressButton(expandCompressBtnTag);
		$(expandCompressBtnTag).click( function() { expandCompressBtn.onClick(); });
		
		// volume control setup.
		volumeSliderRange = document.getElementById(volumeSliderTag);
		volumeControl = new VolumeControl(volumeBtnTag, volumeSliderRange, videotag);
		$(volumeBtnTag).click( function() { volumeControl.onClick(); });
		volumeSliderRange.oninput = function() { volumeControl.onChange(); };
		
		// media timeline setup.
		mediaTimeline = new MediaTimeline(timelineTag, backwardTimelineTag, forwardTimelineTag, cursorTag, videoTimingsTag, videotag);
		$(timelineTag).click( function(e) { if (!multicasting) mediaTimeline.onSeek(e); } );
		
		// video loader setup.
		videoLoader = new VideoLoader(videoLoaderTag);
		
		/* stream manager setup.*/
		streamManager = new StreamManager($(this).find("#media-id").val(), playPauseBtn, mediaTimeline, videoLoader, videotag, currTimestamp != undefined ? currTimestamp : 0);
		mediaTimeline.streamManager = streamManager;
		streamManager.onStart();
		
		//$("#seek-btn").click( function() { onSeek(); } );
	}
);

window.onPlayerIntervalStateChange = function(state) 
{
	if ( state )
		playPauseBtn.onPause();
	else
		playPauseBtn.onPlay();
}