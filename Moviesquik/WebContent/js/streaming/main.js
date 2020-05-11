/**
 * 
 */

var GET_MANIFEST_URL_PATTERN = "stream";
var VIDEO_AUDIO_CODEC = 'video/mp4; codecs="avc1.64001e, mp4a.40.2"';

var mediaSource = null;
var sourceBuffer = null;
var manifest = null;
window.videotag = null;

var nextSegmentIndex = null;
var currentServerIndex = null;
var cachedSegments = null;
var isFetching = false;
var seekFlag = false;
var seekTime;

function chooseServer(manifest) 
{
	if ( manifest.servers.length == 0 )
		return null;
	if ( manifest.servers.length == 1 )
		return manifest.servers[0];
	if ( currentServerIndex == null )
		{ currentServerIndex = Math.floor( Math.random() * manifest.servers.length ); }
	else
		{ currentServerIndex = (currentServerIndex + 1) % manifest.servers.length; }
	return manifest.servers[currentServerIndex];
}

function fetchSegment(segmentIndex, manifest) 
{
	var choosenServer = chooseServer(manifest);
	if ( choosenServer == null )
		return;
	
	// fetch video source.
	return fetch(choosenServer + manifest.streamspath + "segment_" + segmentIndex.toString() + ".mp4").then( function(response) {
		return response.arrayBuffer();
	});
}

function fetchNextSegment() 
{
	// fetch next segment video source.
	fetchSegment(nextSegmentIndex, manifest)
		.then( function( srcdata ) 
		{
			isFetching = false;
			if ( !seekFlag || start_segment )
				sourceBuffer.appendBuffer(srcdata);
			else
				onSourceBufferUpdateEnd();
		});
}
var start_segment = false;
var new_seek_time = null;
function onSourceBufferUpdateEnd(e) 
{
	if ( isFetching )
		return;
	
	if ( seekFlag )
	{
		videotag.pause();
		//videotag.currentTime = 0;
		if ( !start_segment )
			{
				//var i = getNextSegmentIndex(seekTime, manifest);
				sourceBuffer.timestampOffset = 0;
				nextSegmentIndex = 0;//( i > 0 ) ? i - 1 : 0;
				setTimeout(fetchNextSegment, 1);
				start_segment = true;
				return;
			}
		new_seek_time = sourceBuffer.buffered.end(0);
		nextSegmentIndex = getNextSegmentIndex(seekTime, manifest);
		// fetch the next segment.
		isFetching = true;
		seekFlag = false;
		//if ( !sourceBuffer.updating && mediaSource.readyState == 'open' )
		//	mediaSource.endOfStream();
		
		//sourceBuffer.timestampOffset = 0;
		setTimeout(fetchNextSegment, 1);
		return;
	}
	
	if ( new_seek_time != null )
		{ videotag.currentTime = new_seek_time + 1; new_seek_time = null; }
	//videotag.play();
	
	if ( nextSegmentIndex == 0)
		{ watchingPageSetup(); }
	
	cachedSegments[nextSegmentIndex] = true;
	console.log("SEGMENT: " + nextSegmentIndex);
	nextSegmentIndex += 1;
	
	if ( nextSegmentIndex >= manifest.segments )
	{
		videotag.play();
		if ( !sourceBuffer.updating && mediaSource.readyState == 'open' )
			;//mediaSource.endOfStream();
	}
	else
	{
		//sourceBuffer.timestampOffset += 8;//manifest.periods[nextSegmentIndex-1] - 0.01;
		
//		var index = getNextSegmentIndex(videotag.currentTime, manifest);
//		if ( !cachedSegments[index] )
//		{ 
//			sourceBuffer.abort();
//			/*sourceBuffer.addEventListener('abort', function() {
//				
//				resetSegmentsCache(cachedSegments);
//				sourceBuffer.timestampOffset = manifest.times[index];
//				nextSegmentIndex = index; 
//				// fetch the next segment.
//				setTimeout(fetchNextSegment, 12000);
//			});*/
//			
//			mediaSource.removeSourceBuffer(sourceBuffer);
//			
//			// start streaming.
//			streaming(videotag, mediaSource, manifest);
//		}else
		
		// fetch the next segment.
		isFetching = true;
		setTimeout(fetchNextSegment, 1);
	}
}

function streaming(videotag, mediaSource, manifest) 
{
	mediaSource.removeEventListener('sourceopen', onMediaSourceOpen);
	
	// add audio and video source buffers.
	//const audioSourceBuffer = mediaSource.addSourceBuffer('audio/mp4; codecs="mp4a.40.2"');
	//const videoSourceBuffer = mediaSource.addSourceBuffer('video/mp4; codecs="avc1.64001e"');
	sourceBuffer = mediaSource.addSourceBuffer(VIDEO_AUDIO_CODEC);
	sourceBuffer.mode = 'sequence';
	sourceBuffer.addEventListener('updateend', onSourceBufferUpdateEnd);
	
	// initialize cached segments array.
	cachedSegments = new Array(manifest.segments);
	resetSegmentsCache(cachedSegments);
	
	// initialize streaming variables.
	nextSegmentIndex = 0;
	
	if ( manifest.segments > 0 && manifest.duration > 0.0 )
	{
		//mediaSource.duration = manifest.duration;
		//videotag.duration = manifest.duration;
		sourceBuffer.timestampOffset = 0;
		
		fetchNextSegment();
	}
}

function resetSegmentsCache(cachedSegments)
{
	for( var i=0; i<cachedSegments.length; ++i )
		{ cachedSegments[i] = false; }
}

function getNextSegmentIndex(seekTime, manifest) 
{
	// apply binary search to find the next segment index
	// according to the current seek time.
	
	var times = manifest.times;
	var i=0, j=times.length-1;
	
	var last_min = null;
	
	while ( i <= j )
	{
		var c = Math.floor((j - i) / 2) + i;
		if ( times[c] >= seekTime )
			{ j = c - 1; }
		else
			{
				last_min = c;
				i = c + 1;
			}
	}
	if ( last_min == null )
		{ last_min = 0; }
	
	return last_min;
}

function watchingPageSetup() 
{
	$("#loading-wrapper").hide();
	
	var player = $("#player");
	player.removeClass("d-none");
}

function onMediaSourceOpen(e) 
{
	// start streaming.
	streaming(videotag, mediaSource, manifest);
}

function initalizeStreaming(manifest) 
{
	//alert(manifest.segments);
	//alert(manifest.servers);
	
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
	
	var body = $(document).find("body");
	videotag = document.querySelector("video");
	
	//setFullscreenVideo(videotag);
	
	//create the MediaSource and its URL.
	mediaSource  = new MediaSource();
	var url = URL.createObjectURL(mediaSource);
	
	// attach the MediaSource to the video tag.
	videotag.src = url;
	
	mediaSource.addEventListener('sourceopen', onMediaSourceOpen);	
}

window.requestManifest = function(media_content_id) 
{
	$.ajax(
		{
			type: "GET",
			url: GET_MANIFEST_URL_PATTERN + "?key=" + media_content_id,
			dataType: "json",
			success: function(data)
				{	
					manifest = data;
					initalizeStreaming(data);
				}
		}
	);
}

var seek = 10.2;
window.onSeek = function() 
{
	sourceBuffer.remove(0, 1000);  // TODO: check conditions.
	seekTime = seek;
	seek += 12.4;
	seekFlag = true;
	start_segment = false;
}

