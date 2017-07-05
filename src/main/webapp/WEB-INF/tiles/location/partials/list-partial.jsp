<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:choose>
<c:when test="${!empty locations}">
	<table class="table table-striped table-hover">
		<tr>
			<th>Name</th>
			<th>Located In</th>
			<th>Latitude</th>
			<th>Longitude</th>
			<th>Altitude</th>
			<th>Type</th>
		</tr>
		
		<c:forEach var="location" items="${locations}">
			<tr>
				<td>${location.name}</td>
				<td>${location.parent != null ? location.parent : '--'}</td>
				<td>${location.latitude != null ? location.latitude : '--'}</td>
				<td>${location.longitude != null ? location.longitude : '--'}</td>
				<td>${location.altitude != null ? location.altitude : '--'}</td>
				<td>${location.type}</td>
			</tr>
		</c:forEach>
	</table>
</c:when>
<c:otherwise>
	No locations registered for this domain.
</c:otherwise>
</c:choose>

