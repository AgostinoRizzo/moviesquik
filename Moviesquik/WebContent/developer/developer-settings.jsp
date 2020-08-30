<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>Moviesquik for Developers</h3>
<h6 class="note">Personal API settings</h6>

<br><br>

<h5>Personal API Key</h5>
<p class="note">With your API Key you can access the Moviesquik's services using third parts applications.</p>
<span class="fa fa-lock"></span> API Key: 
	<c:if test="${developer_setting.apiKey == null}">
		<span class="text-warning">not set</span><br><br>
	</c:if>
	<c:if test="${developer_setting.apiKey != null}">
		<code>${developer_setting.apiKey}</code><br><br>
	</c:if>
	
	<c:if test="${!developer_setting.active}">
		<button class="btn btn-success" id="activate-api-key-btn"><span class="fa fa-plus"></span> Activate</button><br><br>
	</c:if>
	<c:if test="${developer_setting.active}">
		<button class="btn btn-warning" id="deactivate-api-key-btn"><span class="fa fa-minus"></span> Deactivate</button><br><br>
	</c:if>
	

<c:if test="${developer_setting.active && developer_setting.apiKey != null}">
	
	<hr class="mb-4">
	
	<h5>Available API Services</h5><br>
	
	<div class="row">
		<div class="col-1"><img src="res/drawable/google_assistant_logo.png" class="service-img"></div>
		<div class="col-11">
		
			<h6>Google Assistant Service</h6>
			<p class="note">You can access the Moviesquik's services just with your voice using your <strong>Smartphone</strong>, 
			<strong>Google Home</strong> and all devices that supports the <strong>Google Assistant</strong>.</p>
			<span class="fa fa-lock"></span> Service Key: <code>${assistant_service_key}</code><br><br>
			
			<c:if test="${developer_setting.assistantServiceKey == null}">
				<button class="btn btn-success btn-sm" id="activate-assistant-service-key-btn"><span class="fa fa-plus"></span> Activate</button>
			</c:if>
			<c:if test="${developer_setting.assistantServiceKey != null}">
				<button class="btn btn-warning btn-sm" id="deactivate-assistant-service-key-btn"><span class="fa fa-minus"></span> Deactivate</button>
			</c:if>
			<br>
			
			<c:if test="${developer_setting.assistantServiceKey != null}">
			
				<hr class="mb-4">
				
				<h6>Available Service Actions (shortcut)</h6><br>
				
				<div class="tile action-tile bg-dark">
					<h5><strong>Media contents player</strong></h5>
					<p>Trigger a media content play</p>
					
					<span class="fa fa-globe"></span> URL Pattern: 
						<span><code>http://<strong>&lt;MOVIESQUIK_FQDN&gt;</strong>:<strong>&lt;MOVIESQUIK_WEB_PORT&gt;</strong>/api?api_key=${developer_setting.apiKey}&amp;service_key=${assistant_service_key}&amp;user_id=${user.id}&amp;action=play&amp;media=<strong>&lt;MEDIA_TITLE&gt;</strong></code></span><br>
					<span class="fa fa-puzzle-piece"></span> HTTP Request Method: <span><code>GET</code></span><br>
					<span class="fa fa-reply"></span> HTTP Response Data: <span><code>JSON</code></span><br>
					<span class="fa fa-lock"></span> User ID: <span><code>${user.id}</code></span><br>
					
					<c:if test="${developer_setting.playAction}">
						<span class="fa fa-info"></span> Status: <span><code>Enabled</code></span><br><br>
						<button class="btn btn-warning btn-sm" id="disable-play-action"><span class="fa fa-puzzle-piece"></span> Disable</button>
					</c:if>
					<c:if test="${!developer_setting.playAction}">
						<span class="fa fa-info"></span> Status: <span><code>Disabled</code></span><br><br>
						<button class="btn btn-primary btn-sm" id="enable-play-action"><span class="fa fa-puzzle-piece"></span> Enable</button>
					</c:if>
					
					<br>
				</div>
			
			</c:if>
			
		</div>
	</div>

</c:if>

<br>

<div>
	
	
</div>
