<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Resources</h2>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${!empty observations}">
					<table class="table table-striped table-hover">
						<tr>
							<th>Observed By</th>
							<th>Observed Property</th>
							<th>Observed Value</th>
							<th>QoC Criterion</th>
							<th>QoC Value</th>
						</tr>
						
						<c:forEach var="observation" items="${observations}">
							<tr>
								<td>${observation.observedBy}</td>
								<td>${observation.observedProperty}</td>
								<td>${observation.value}</td>
								<td>${observation.criterion}</td>
								<td>${observation.qoCValue}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
			<c:otherwise>
				We have not found any observation that satisfies your search parameters. Return to the previous page to make new searches.
			</c:otherwise>
			</c:choose>
			

		</div>

	</div>
</div>