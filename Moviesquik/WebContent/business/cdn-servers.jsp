<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<br>

<div id="cdn-servers-list">
	
	<h5>CDN Servers</h5>
	<p class="note">
		Streaming Servers list of the Context Distribution Network.
	</p>
	
	<div class="table-responsive tile">
		<table class="table table-striped table-sm">
		
			<thead>
				<tr>
					<th><span class="fa fa-lock"></span> Server Key</th>
					<th>Name</th>
					<th>Location</th>
					<th>Description</th>
					<th>Status</th>
				</tr>
			</thead>
			
			<tbody>
			
				<c:forEach items="${cdnservers}" var="server">
					<tr>
						<td><span class="server-key">${server.key}</span> &nbsp;&nbsp;<span class="fa fa-copy clickable-light server-key-copy-btn"></span></td>
						<td>${server.name}</td>
						<td>${server.location.latitude}, ${server.location.longitude}</td>
						<td>${server.description}</td>
						<td>
							<c:if test="${server.status}"><span class="fa fa-circle text-success"></span> ON</c:if>
							<c:if test="${!server.status}"><span class="fa fa-circle text-danger"></span> OFF</c:if>
						</td>
					</tr>
				</c:forEach>
				
			</tbody>
			
		</table>
	</div>
	
	<br><br>
	
	<div class="row">
	
		<div class="col-6">
			<h5>CDN Servers Map</h5>
			<p class="note">
				<span class="fa fa-info text-warning"></span> The map displays the streaming servers locations for the Context Distribution Network.
			</p>
		</div>
		
		<div class="col-6">
			<h5>CDN Servers Usage Chart</h5>
			<p class="note">
				<span class="fa fa-info text-warning"></span> The chart displays in real time the current streaming servers usage for the Context Distribution Network. 
				The displayed values are monitored by the server every second and they are expressed in the number of <strong>requests/second</strong>.
			</p>
		</div>
		
	</div>
	
	<div class="row">
		<div class="loader loader-sm d-none" id="cdn-charts-loader"></div>
	</div>
	
	<div class="row" id="cdn-charts-row">
	
		<div class="col-6 cdn-map" id="cdn-map">
		</div>
		
		<div class="col-6 cdn-chart" id="cdn-usage">
		</div>
		
	</div>
	
</div>
