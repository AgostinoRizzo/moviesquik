<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<div id="player" class="d-none">
	
	<video id="video-stream-tag" autoplay></video>
	
	<div id="player-controls-wrapper">
		
		<div id="video-timeline-wrapper">
			
			<div id="video-timeline">
				<div id="timeline" class="can-point">
					<div id="backward-timeline"></div>
					<!-- <div id="forward-timeline"></div> -->
				</div>
				<div id="cursor" class="clickable"></div>
			</div>
			
			<div id="video-timings">
				00:00
			</div>
			
		</div>
		
		<div id ="player-controls">
		
			<!-- play/pause button -->
			<div id="play-pause-btn" class="player-control"><i class="fa fa-play clickable"></i></div>
			
			<!-- step backward button -->
			<!-- <div id="step-backward-btn" class="player-control"><i class="fa fa-step-backward clickable"></i></div> -->
			<!-- step forward button -->
			<!-- <div id="step-forward-btn" class="player-control"><i class="fa fa-step-forward clickable"></i></div> -->
			
			<!-- volume button -->
			<div id="volume-btn" class="player-control"><i class="fa fa-volume-up clickable"></i></div>
			<!-- volume slider -->
			<div id="volume-slider-wrapper" class="player-control">
				<input id="volume-slider" type="range" min="0" max="100" value="100" class="slider can-point"></input>
			</div>
			
			<!-- media content title -->
			<div id="media-title" class="player-control text-overflow">${media_content.title}</div>
			
			<!-- expand/compress button -->
			<div id="expand-compress-btn" class="player-control right-player-control"><i class="fa fa-expand clickable"></i></div>
			
		</div>
	</div>
	
</div>