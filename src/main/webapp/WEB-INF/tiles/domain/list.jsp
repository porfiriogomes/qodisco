<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication property="principal.username" var="loggedUsername"/>

<div class="col-md-9">
	<!-- Website Content -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Domains</h2>
		</div>
		<div class="panel-body">
			<c:choose>
			<c:when test="${!empty domains}">
				<table class="table table-striped table-hover">
					<tr>
						<th>Domain Name</th>
						<th>Ontology URI</th>
						<th>Owner</th>
						<th></th>
					</tr>
					
					<c:forEach var="domain" items="${domains}">
						<tr>
							<td>${domain.name}</td>
							<td>${domain.ontologyUri}</td>
							<td>${domain.user.username}</td>
							<td>
								<c:if test="${domain.user.username==loggedUsername}">
									<a href="${path}/domain/edit?name=${domain.name}" class="btn btn-sm btn-default">Edit</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				No domains registered.
			</c:otherwise>
			</c:choose>

		</div>
	</div>
</div>