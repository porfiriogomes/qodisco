<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:choose>
<c:when test="${!empty qoccriteria}">
	<table class="table table-striped table-hover">
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Operations</th>
		</tr>
		
		<c:forEach var="qoc" items="${qoccriteria}">
			<tr>
				<td>${qoc.name}</td>
				<td>${qoc.description}</td>
				<td>
					<a href="${path}/qoc/show?qocname=${qoc.name}&repositoryurl=${qoc.repositoryUrl}"
						class="btn btn-sm btn-default">Show</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:when>
<c:otherwise>
	No QoC Criteria registered for this domain.
</c:otherwise>
</c:choose>

