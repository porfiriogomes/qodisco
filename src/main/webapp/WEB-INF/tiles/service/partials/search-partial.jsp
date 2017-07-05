<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

	function submitForm(){
		$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
		$('#searchservice-form').submit();	
	}

</script>

<form:form id="searchservice-form" method="post" action="${pageContext.request.contextPath}/service/search" modelAttribute="servicesearch" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">		
		<div class="form-group">
			<label>The Service is offered by?</label>
			<form:select path="resource" cssClass="form-control">
				<form:option value="None"/>
				<form:options items="${resources}"/>
			</form:select>
		</div>
	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Search"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>
