<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">New Repository</h2>
		</div>
		<form:form method="post" action="${path}/repository/new" modelAttribute="repository">
			<form:hidden path="id"/>
			<div class="panel-body">
				<div class="form-group">
					<label for="name">Repository URL</label>
					<div class="input-group">
						<div class="input-group-addon">
							<span class="fa fa-genderless"></span>
						</div>
						<form:input path="url" type="text" class="form-control" placeholder="Enter a valid URL."/>
					</div>
				</div>
				<div class="form-group">
					<label for="name">Repository's Supported Operations</label>
					<div class="input-group">
						<div class="form-check">
							<label class="form-check-label">
								<form:checkbox path="operations" value="update" cssClass="form-check-input"/>
								Update
							</label>													
						</div>
						<div class="form-check">
							<label class="form-check-label">
								<form:checkbox path="operations" value="query" cssClass="form-check-input"/>
								Query
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label>Repository's Domains</label>
					<form:select path="domains" cssClass="form-control">
						<c:forEach items="${domains}" var="domain">
							<form:option value="${domain.name}" label="${domain.name}"/>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<input type="submit" value="Save"
					class="btn btn-default pull-right">
			</div>
		</form:form>
	</div>
</div>