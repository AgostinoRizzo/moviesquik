<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" id="ga-view-id" value="${google_analytics_view_id}">

<br>

<div id="analytics-dashboard-container">
	
	<h4>Moviesquik Analytics Dashboard</h4>
	<p class="note">
		Streaming Servers list of the Context Distribution Network.
	</p>
	
	<div class="row">
	
		<div class="col col-12">
		
			<h5>Time period</h5><br>
			
			<div class="row">
				
				<div class="col-4">
					<label for="time-period">Start date</label>
					<div class="input-group">
						<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						<input name="start-date" class="form-control" id="start-date" required type="date">
					</div>
				</div>
				
				<div class="col-4">
					<label for="time-period">End date</label>
					<div class="input-group">
						<span class="input-group-addon"><span class="fa fa-calendar"></span></span>
						<input name="end-date" class="form-control" id="end-date" required type="date">
					</div>
				</div>
				
				<div class="col-4" id="sync-btn-col">
					<div class="btn btn-light" id="analytics-data-sync-btn"><img src="res/drawable/google_analytics_icon.png"> Sync</div>
				</div>
				
			</div>
			
			
		</div>
		
	</div>
	
	<br><br>
	
	<div class="row">
		<div class="col-12" id="fetch-data-error-div"></div>
	</div>
	
	<div class="row">
		
		<div class="col-6">
		
			<div class=""><h5>Total traffic</h5></div>
			<div class="row dashboard-row">
				
				<div class="col col-4">
					<div class="tile bg-primary">
						<h5>Total Sessions</h5>
						<h1 id="total-sessions">0</h1>
						<h5>Mobile Sessions</h5>
						<h1 id="mobile-sessions">0</h1>
					</div>
				</div>
				
				<div class="col col-4">
					<div class="tile bg-info">
						<h5>Total Pageviews</h5>
						<h1 id="total-pageviews">0</h1>
						<h5>Mobile Pageviews</h5>
						<h1 id="mobile-pageviews">0</h1>
					</div>
				</div>
				
				<div class="col col-4">
					<div class="tile bg-warning">
						<h5>Total Duration</h5>
						<h1 id="total-session-duration">0s</h1>
						<h5>Mobile Duration</h5>
						<h1 id="mobile-session-duration">0s</h1>
					</div>
				</div>
				
			</div>
			
			<br>
			
			<div class=""><h5>Visitors</h5></div>
			<div class="row dashboard-row">
				
				<div class="col col-6">
					<div class="tile bg-primary">
						<h5>New Visitors</h5>
						<h1 id="new-visitors">0</h1>
					</div>
				</div>
				
				<div class="col col-6">
					<div class="tile bg-info">
						<h5>Returning Visitors</h5>
						<h1 id="returning-visitors">0</h1>
					</div>
				</div>
				
			</div>	
			
		</div>
		
		<div class="col-6">
			<div class=""><h5>Sessions by Country</h5></div>
			<div id="sessions-by-country-chart">
			
			</div>
			<div id="image-sessions-by-country-chart" class="d-none">
			
			</div>
		</div>
		
	</div>
	
	<br><br>
	
	<div class="row">
		<div class="col-12">
			<div class=""><h5>Top Content</h5></div>
			<div class="row dashboard-row">
				
				<div class="col col-12 tile bg-dark">
					<table class="table table-striped table-sm">
		
						<thead>
							<tr>
								<th>Page Path</th>
								<th>Page Views</th>
								<th>Unique Page Views</th>
								<th>Time On Page</th>
								<th>Bounces</th>
								<th>Entrances</th>
								<th>Exits</th>
							</tr>
						</thead>
						
						<tbody id="top-content-data">
							
						</tbody>
						
					</table>
				</div>
				
			</div>
		</div>
	</div>
	
	<br><br>
	
	<div class="row">
		<div class="col-12">
			<div class=""><h5>Browser and Operating System</h5></div>
			<div class="row dashboard-row">
				
				<div class="col col-12 tile bg-dark">
					<table class="table table-striped table-sm">
		
						<thead>
							<tr>
								<th>Operating System</th>
								<th>Operating System Version</th>
								<th>Browser</th>
								<th>Browser Version</th>
								<th>Total Sessions</th>
							</tr>
						</thead>
						
						<tbody id="browser-os-data">
							
						</tbody>
						
					</table>
				</div>
				
			</div>
		</div>
	</div>
	
</div>

<br><br>