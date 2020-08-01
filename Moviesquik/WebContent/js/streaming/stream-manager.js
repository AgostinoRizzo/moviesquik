/**
 * 
 */

import './stream-utils.js';

// video/audio codecs.
var VIDEO_AUDIO_CODEC = 'video/mp4; codecs="avc1.64001e, mp4a.40.2"';

//streaming constants.
var MIN_BUFFERED_WINDOW = 20.0;           // expressed in seconds. it must be <= MIN_PLAYABLE_BUFFERED_WINDOW
var MIN_PLAYABLE_BUFFERED_WINDOW = 10.0;  // expressed in seconds.
var MANAGING_INTERVAL = 1000;             // expressed in milliseconds.
var MAX_SOURCE_BUFFER_TIME = 100;

const STREAM_MANAGER_STATE =
{
	BOOTSTRAP: 'bootstrap',
	BUFFERING: 'buffering',
	SEEKING:   'seeking'
}

window.StreamManager = function(mediaId, playPauseBtn, mediaTimeline, videoLoader, videotag, startTimestamp=0)
{	
	var self = this;
	
	this.mediaId = mediaId;
	this.manifest = null;
	this.videotag = videotag;
	this.playPauseBtn = playPauseBtn;
	this.mediaTimeline = mediaTimeline;
	this.videoLoader = videoLoader;
	this.startTimestamp = startTimestamp;
	
	this.mediaSource = null;
	this.sourceBuffer = null;
	
	// streaming variables.
	this.nextSegmentIndex = null;
	this.isFetching = false;
	this.firstSegment = null;
	this.seekTime = null;
	this.firstSegmentFlag = false;
	this.newSeekTime = null;
	this.onMinPlayableWindow = false;
	this.fetchErrorCount = 0;
	this.startTimestampSeek = null;
	this.startedWithTimestampSeek = false;
	
	// state variable.
	this.state = STREAM_MANAGER_STATE.BOOTSTRAP;
	
	this.onStart = function() 
	{
		requestManifest(this.mediaId, this);
	};
	
	this.onManifestReceived = function(manifest)
	{
		this.manifest = manifest;
		if ( this.manifest.servers.length > 0 )
			requestFullManifest(this.mediaId, this.manifest, this);
		else
			self.videoLoader.showServersError();
	};
	
	this.onFullManifestReceived = function(manifest)
	{
		if ( manifest == null )
		{
			self.videoLoader.showLoadingError();
			return;
		}
		this.initalizeStreaming();
	};
	
	/* initialization routine. */
	this.initalizeStreaming = function() 
	{
		if ( !'MediaSource' in window )
		{
			alert("Media Source not supported by this browser.");
			return;
		}
		if ( !MediaSource.isTypeSupported(VIDEO_AUDIO_CODEC) )
		{
			alert("Media CODEC (" + VIDEO_AUDIO_CODEC + ") not supported by this browser.");
			return;
		}
		
		//create the MediaSource and its URL.
		this.mediaSource  = new MediaSource();
		var url = URL.createObjectURL(this.mediaSource);
		
		// attach the MediaSource to the video tag.
		this.videotag.src = url;
		
		this.mediaSource.addEventListener('sourceopen', function() { self.onMediaSourceOpen(); });	
		
		setInterval(function() { self.onManage(); }, MANAGING_INTERVAL);
	}
	
	/* main managing routine. */
	this.onManage = function() 
	{
		if ( this.state == STREAM_MANAGER_STATE.BOOTSTRAP || this.sourceBuffer.updating || this.isFetching )
			return;
		
		if ( this.state == STREAM_MANAGER_STATE.BUFFERING )  // on buffering state.
		{
			if ( this.isMinPlayableBufferedWindow() )
			{
				this.onMinPlayableWindow = true;
				this.startLoading();
			}
			else if ( this.onMinPlayableWindow )
			{
				this.stopLoading();
				this.onMinPlayableWindow = false;
			}
			
			this.stopLoading();
			this.onMinPlayableWindow = false;
			
			if ( this.isThereSegmentsToFetch() )
				this.fetchNextSegment();
			
			this.mediaTimeline.updateCurrentTime(this.manifest, this.getBufferedWindow());
			//this.playPauseBtn.syncToVideoTag();
		}
		else                                                // on seeking state.
		{
			this.startLoading();
		}
	}
	
	/* on seek managing routine. */
	this.onSeek = function(value)
	{
		if ( this.state != STREAM_MANAGER_STATE.BUFFERING || this.sourceBuffer.updating || this.isFetching )
			return;
		
		this.state = STREAM_MANAGER_STATE.SEEKING;
		this.firstSegmentFlag = false;
		this.seekTime = value;
		this.mediaTimeline.updateSeekingVirtualTime(this.seekTime, this.manifest);
		this.startLoading();
		//playPauseBtn.onPause();
		this.sourceBuffer.remove(0, MAX_SOURCE_BUFFER_TIME);  // TODO: check conditions.
	}
	
	/* media source open manager routine. */
	this.onMediaSourceOpen = function() 
	{
		if ( this.sourceBuffer != null )
			return;
		
		// add video/audio source buffer.
		this.sourceBuffer = this.mediaSource.addSourceBuffer(VIDEO_AUDIO_CODEC);
		this.sourceBuffer.mode = 'sequence';
		this.sourceBuffer.addEventListener('updateend', function() { self.onSourceBufferUpdateEnd(); });
		
		// initialize streaming variables.
		this.nextSegmentIndex = 0;
		this.sourceBuffer.timestampOffset = 0;
		
		this.startLoading();
		
		if ( this.manifest.segments > 0 && this.manifest.duration > 0.0 )
		{
			if ( this.startTimestamp > 0 && this.startTimestamp < this.manifest.duration )
			{
				var startSegmentIndex = getNextSegmentIndex(startTimestamp, this.manifest);
				if ( startSegmentIndex > 0 )
				{
					this.startTimestampSeek = this.startTimestamp / this.manifest.duration;
					this.startedWithTimestampSeek = true;
					this.videotag.volume = 0.0;
				}
			}
			this.fetchNextSegment();
		}
	};
	
	/* source buffer update manager routine. */
	this.onSourceBufferUpdateEnd = function() 
	{
		if ( this.isFetching )
			return;
		
		if ( this.state == STREAM_MANAGER_STATE.BOOTSTRAP )  // on bootstrap state.
		{
			if ( this.nextSegmentIndex == 0 && !this.startedWithTimestampSeek )
				{ watchingPageSetup(); }
			
			//if ( this.startTimestampSeek != null && this.nextSegmentIndex != 0 )
			//{ watchingPageSetup(); this.startTimestampSeek = null; this.videotag.volume = 1.0; }
			
			++this.nextSegmentIndex;
			
			if ( this.isThereSegmentsToFetch() )
			{
				this.fetchNextSegment();
			}
			else
			{ 
				this.stopLoading();
				this.state = STREAM_MANAGER_STATE.BUFFERING;
			}
		}
		else if ( this.state == STREAM_MANAGER_STATE.BUFFERING )  // on buffering state.
		{
			if ( this.startTimestampSeek != null )
			{
				this.onSeek(this.startTimestampSeek);
				this.startTimestampSeek = null;
			}
			else
				++this.nextSegmentIndex;
		}
		else                                                      // on seeking state.
		{	
			if ( this.newSeekTime != null )
			{
				this.videotag.currentTime = this.newSeekTime;
				this.newSeekTime = null;
				this.mediaTimeline.timeOffset = this.manifest.times[this.nextSegmentIndex];
				this.mediaTimeline.videoTagTimeOffset = this.videotag.currentTime;
				this.state = STREAM_MANAGER_STATE.BUFFERING;
				++this.nextSegmentIndex;
				//playPauseBtn.onPlay();
				return;
			}
			
			if ( this.firstSegmentFlag )
			{
				this.newSeekTime = this.sourceBuffer.buffered.end(0) + 1;
				this.nextSegmentIndex = getNextSegmentIndex(this.seekTime * this.manifest.duration, this.manifest);
				
				if ( this.isThereSegmentsToFetch() )
					this.fetchNextSegment();
				
				if ( this.startedWithTimestampSeek ) // activate view
				{ 
					this.startedWithTimestampSeek = false;
					watchingPageSetup();
					this.videotag.volume = 1.0;
				}
			}
			else
			{
				this.sourceBuffer.timestampOffset = 0;
				this.nextSegmentIndex = 0;
				//this.sourceBuffer.appendBuffer(this.firstSegment);
				
				this.firstSegmentFlag = true;
				
				if ( this.isThereSegmentsToFetch() )
					this.fetchNextSegment();
			}
		}
	};
	
	this.getBufferedWindow = function() 
	{
		if ( this.sourceBuffer.buffered.length == 0 )
			return 0;
		return this.sourceBuffer.buffered.end(0) - this.videotag.currentTime;
	};
	
	this.isThereSegmentsToFetch = function() 
	{
		return this.getBufferedWindow() < MIN_BUFFERED_WINDOW && this.isThereMoreSegments();
	};
	
	this.isMinPlayableBufferedWindow = function() 
	{
		return this.getBufferedWindow() < MIN_PLAYABLE_BUFFERED_WINDOW && this.isThereMoreSegments();
	};
	
	this.isThereMoreSegments = function() 
	{
		return this.nextSegmentIndex < this.manifest.segments;
	};
	
	this.fetchNextSegment = function()
	{
		if ( this.isFetching )
			return;
		
		this.isFetching = true;
		console.log("Fetching segment " + this.nextSegmentIndex + "...");		
		
		var segmentIndex = this.nextSegmentIndex;
		
		// fetch next segment video source.
		fetchSegment(segmentIndex, this.manifest)
			.then( function( srcdata ) 
			{
				self.isFetching = false;
				if ( srcdata == null )
				{
					++self.fetchErrorCount;
					if ( self.state == STREAM_MANAGER_STATE.BOOTSTRAP || self.fetchErrorCount >= 3 )
					{
						self.videoLoader.showLoadingError();
						self.fetchErrorCount = 0;
					}
					return;
				}
				
				if ( segmentIndex == 0 )
					self.firstSegment = srcdata.slice(0);
				self.sourceBuffer.appendBuffer(srcdata);
			});
	};
	
	this.startLoading = function() 
	{
		this.videotag.pause();
		this.videoLoader.show();
		this.playPauseBtn.syncToVideoTag();
		this.playPauseBtn.disable();
	};
	
	this.stopLoading = function() 
	{
		this.videoLoader.hide();
		this.playPauseBtn.enable();
		playPauseBtn.onPlay(); 
	};
}