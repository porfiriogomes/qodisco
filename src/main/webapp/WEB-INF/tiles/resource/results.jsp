<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Resources</h2>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${!empty resources}">
					<table class="table table-striped table-hover">
						<tr>
							<th>Name</th>
							<th>Type</th>
							<th>Location</th>
							<th>Observes</th>
						</tr>
						
						<c:forEach var="resource" items="${resources}">
							<tr>
								<td>${resource.name}</td>
								<td>${resource.type}</td>
								<td>${resource.location}</td>
								<td>
									<c:forEach var="property" items="${resource.properties}">
										[${property}]
									</c:forEach>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
			<c:otherwise>
				We have not found any resource that satisfies your search parameters. Return to the previous page to make new searches.
			</c:otherwise>
			</c:choose>
			

		</div>

	</div>
</div>