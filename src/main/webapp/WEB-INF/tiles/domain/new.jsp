<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<c:choose>
				<c:when test="${domain.name==null}">
					<h2 class="panel-title">New Domain</h2>
				</c:when>
				<c:otherwise>
					<h2 class="panel-title">Edit Domain</h2>
				</c:otherwise>
			</c:choose>
		</div>
		<form:form method="post" action="${path}/domain/new" modelAttribute="domain">
			<div class="panel-body">
				<div class="form-group">
					<label for="name">Domain Name</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-genderless"></span>
						</div>
						<c:choose>
							<c:when test="${domain.name==null}">
								<form:input path="name" type="text" cssClass="form-control" placeholder="Enter a unique domain name. For example, Pollution or Air Pollution"/>
							</c:when>
							<c:otherwise>
								<form:input path="name" cssClass="form-control" readonly="true"/>
							</c:otherwise>
						</c:choose>
					</div>
					<form:errors path="name" cssClass="text-red"/>
				</div>
				<div class="form-group">
					<label for="name">Domain URL</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-genderless"></span>
						</div>
						<form:input path="ontologyUri" type="text" cssClass="form-control" placeholder="Enter a valid URI."/>
					</div>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<input type="submit" value="Save"
					class="btn btn-default pull-right">
			</div>
		</form:form>
	</div>
</div>