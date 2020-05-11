/**
 * 
 */

import './main.js';

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

function PlayPauseButton(tagid)
{
	this.tagid = tagid;
	this.status = 'onplay';
	
	this.onClick = function()
	{
		this.status == 'onplay' ? this.onPause() 
								: this.onPlay();
	};
	this.onPlay = function()
	{
		this.removeAllIconClasses();
		addIconClass(this.tagid, "fa-pause");
		this.status = 'onplay';
		if ( videotag != null )
			videotag.play();
	};
	this.onPause = function()
	{
		this.removeAllIconClasses();
		addIconClass(this.tagid, "fa-play");
		this.status = 'onpause';
		if ( videotag != null )
			videotag.pause();
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


function VolumeControl(btntagid, volumeSliderRange)
{
	this.btntagid = btntagid;
	this.volumeSliderRange = volumeSliderRange;
	
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
		
		if ( videotag != null )
			videotag.volume = volumeSliderRange.value / 100.0;
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
		if ( videotag != null )
			videotag.volume = volumeSliderRange.value / 100.0;
	};
	
	this.removeAllIconClasses = function()
	{
		removeIconClass(this.btntagid, "fa-volume-up");
		removeIconClass(this.btntagid, "fa-volume-down");
		removeIconClass(this.btntagid, "fa-volume-off");
	};
}


var playPauseBtnTag = "#play-pause-btn";
var expandCompressBtnTag = "#expand-compress-btn";
var volumeBtnTag = "#volume-btn";
var volumeSliderTag = "volume-slider";

var playPauseBtn = null;
var expandCompressBtn = null;
var volumeControl = null;
var volumeSliderRange = null;

$(document).ready( function()
	{
		/* player controls setup. */
	
		// play/pause button setup.
		playPauseBtn = new PlayPauseButton(playPauseBtnTag);
		playPauseBtn.onPlay();
		$(playPauseBtnTag).click( function() { playPauseBtn.onClick(); });
		
		// expand/reduce button setup.
		expandCompressBtn = new ExpandCompressButton(expandCompressBtnTag);
		$(expandCompressBtnTag).click( function() { expandCompressBtn.onClick(); });
		
		// volume control setup.
		volumeSliderRange = document.getElementById(volumeSliderTag);
		volumeControl = new VolumeControl(volumeBtnTag, volumeSliderRange);
		$(volumeBtnTag).click( function() { volumeControl.onClick(); });
		volumeSliderRange.oninput = function() { volumeControl.onChange(); };
		
		
		requestManifest( $(this).find("#media-id").val() );
		
		//$("#seek-btn").click( function() { onSeek(); } );
	});