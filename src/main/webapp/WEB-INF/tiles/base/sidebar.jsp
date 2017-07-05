<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="col-md-3">
	<div class="panel-group" id="accordion">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#collapseUser"><span class="fa fa-user"></span>User</a>
				</h4>
			</div>
			<div id="collapseUser" class="panel-collapse collapse out <tiles:getAsString name='useractive'/>">			
				<sec:authorize access="!isAuthenticated()">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/signin">Sign In</a></li>
						<li class="list-group-item"><a href="${path}/user">Sign Up</a></li>
					</ul>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<form role="form" id="logout_form" action="${path}/signout" method="post">
						<ul class="list-group" style="margin: 0px;">
							<li class="list-group-item" style="border: none;"><a href="javascript:{}" onclick="document.getElementById('logout_form').submit(); return false;">Sign Out</a></li>
						</ul>
					</form>
				</sec:authorize>
			</div>	
		</div>
		<sec:authorize access="isAuthenticated()">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseResource"><span class="fa fa-tag"></span>Resource</a>
					</h4>
				</div>
				<div id="collapseResource" class="panel-collapse collapse out <tiles:getAsString name='resourceactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/resource/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/resource/search">Search</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseService"><span class="fa fa-gears"></span>Service</a>
					</h4>
				</div>
				<div id="collapseService" class="panel-collapse collapse out <tiles:getAsString name='serviceactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/service/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/service/search">Search</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseObservation"><span class="fa fa-sticky-note"></span>Observation</a>
					</h4>
				</div>
				<div id="collapseObservation" class="panel-collapse collapse out <tiles:getAsString name='observationactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/observation/search">Search</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseLocation"><span class="fa fa-map-o"></span>Location</a>
					</h4>
				</div>
				<div id="collapseLocation" class="panel-collapse collapse out <tiles:getAsString name='locationactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/location/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/location">List</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseQoC"><span class="fa fa-dashboard"></span>QoC
							Criterion</a>
					</h4>
				</div>
				<div id="collapseQoC" class="panel-collapse collapse out <tiles:getAsString name='qocactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/qoc/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/qoc">List</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseSparql"><span class="fa fa-terminal"></span>SPARQL</a>
					</h4>
				</div>
				<div id="collapseSparql" class="panel-collapse collapse out <tiles:getAsString name='sparqlactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/sparql/search">Search</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseDomain"><span class="fa fa-object-group"></span>Domain</a>
					</h4>
				</div>
				<div id="collapseDomain" class="panel-collapse collapse out <tiles:getAsString name='domainactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/domain/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/domain">List</a></li>
					</ul>
				</div>
			</div>
			<div class="panel panel-default active">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseRepository"><span class="fa fa-shopping-bag"></span>Repository</a>
					</h4>
				</div>
				<div id="collapseRepository" class="panel-collapse collapse out <tiles:getAsString name='repositoryactive'/>">
					<ul class="list-group">
						<li class="list-group-item"><a href="${path}/repository/new">Add</a></li>
						<li class="list-group-item"><a href="${path}/repository">List</a></li>
					</ul>
				</div>
			</div>
		</sec:authorize>
	</div>
</div>