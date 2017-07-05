<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Services</h2>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${!empty services}">
					<table class="table table-striped table-hover">
						<tr>
							<th>Name</th>
							<th>Description</th>
						</tr>
						
						<c:forEach var="service" items="${services}">
							<tr>
								<td>${service.name}</td>
								<td>${service.description}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
			<c:otherwise>
				We have not found any service that satisfies your search parameters. Return to the previous page to make new searches.
			</c:otherwise>
			</c:choose>	

		</div>
	</div>
</div>