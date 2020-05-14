/**
 * 
 */

var GET_MANIFEST_URL_PATTERN = "stream";


var currentServerIndex = null;

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


window.fetchSegment = function(segmentIndex, manifest) 
{
	var choosenServer = chooseServer(manifest);
	if ( choosenServer == null )
		return;
	
	// fetch video source segment.
	return fetch(choosenServer + manifest.streamspath + "segment_" + segmentIndex.toString() + ".mp4")
			.then( function(response) {
				return response.arrayBuffer();
			})
			.catch( function() 
			{
				return null;
			});
}

window.getNextSegmentIndex = function(seekTime, manifest) 
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

window.watchingPageSetup = function() 
{
	$("#loading-wrapper").hide();
	
	var player = $("#player");
	player.removeClass("d-none");
}

window.requestManifest = function(media_content_id, stream_manager) 
{
	$.ajax(
		{
			type: "GET",
			url: GET_MANIFEST_URL_PATTERN + "?key=" + media_content_id,
			dataType: "json",
			success: function(data)
				{
					stream_manager.onManifestReceived(data);
				}
		}
	);
}

window.requestFullManifest = function(media_content_id, manifest, stream_manager) 
{
	var choosenServer = chooseServer(manifest);
	if ( choosenServer == null )
		return;
	
	var manifestToFillOfStreamInfo = manifest;
	
	// fetch full manifest.
	fetch(choosenServer + manifest.streamspath + "manifest")
		.then( function(response) 
		{
			if ( response.ok )
			{
				response.text().then( function(text) 
				{
					fillManifestOfStreamInfo(manifestToFillOfStreamInfo, text);
					stream_manager.onFullManifestReceived(manifestToFillOfStreamInfo);
				});
			}
			else
				stream_manager.onFullManifestReceived(null);
		})
		.catch( function() 
		{
			stream_manager.onFullManifestReceived(null);
		});
}

function fillManifestOfStreamInfo(manifest, streamManifestText) 
{
	var lines = streamManifestText.split("\n");
	if ( lines.length < 3 )
		return;
	
	manifest["duration"] = parseFloat(lines[0]);
	manifest["segments"] = parseInt(lines[1]);
	manifest["times"] = new Array(manifest.segments);
	
	if ( lines.length - 2 < manifest.times.length )
		return;
	
	for ( var i=0; i<manifest.times.length; ++i )
		manifest.times[i] = parseFloat(lines[i+2]);
}

