<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication property="principal.username" var="loggedUsername"/>

<div class="col-md-9">
	<!-- Website Content -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Repositories</h2>
		</div>
		<div class="panel-body">
		
			<c:choose>
				<c:when test="${!empty repositories}">
					<table class="table table-striped table-hover">
						<tr>
							<th>Repository URL</th>
							<th>Domains</th>
							<th>Operations</th>
							<th>Is Available?</th>
							<th></th>
						</tr>
						
						<c:forEach var="repository" items="${repositories}">
							<tr>
								<td>${repository.url}</td>
								<td>
									<c:forEach items="${repository.domains}" var="domain">
										[${domain.name}]
									</c:forEach>
								</td>
								<td>
									<c:forEach items="${repository.operations}" var="operation">
										[${operation}]
									</c:forEach>
								</td>
								<td>
									<c:choose>
										<c:when test="${repository.repositoryUnavailableTimeEntity.time > 0}">
											No
										</c:when>
										<c:otherwise>
											Yes
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<a href="${path}/repository/edit?id=${repository.id}" class="btn btn-sm btn-default">Edit</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					You have not registered any repository.
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>