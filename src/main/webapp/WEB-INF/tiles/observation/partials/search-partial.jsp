<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

	function submitForm(){
		$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
		$('#searchobservation-form').submit();	
	}
	
</script>

<form:form id="searchobservation-form" method="post" action="${pageContext.request.contextPath}/observation/search" modelAttribute="observationsearch" role="form">
	<input type="hidden" name="domain-selected"/>
	<div class="panel-body">
		<div class="form-group">
			<label>Are you interested in observations about?</label>
			<form:select path="observedProperty" cssClass="form-control">
				<form:option value="None"/>
				<form:options items="${observedproperties}"/>
			</form:select>
		</div>
		
		<div id="qoc-properties">
			<label>You are interested in observations with which QoC parameters?</label>
			<div class="row">
				<div class="form-group col-md-4">
					<form:select path="qoCCriterion" cssClass="form-control">
						<form:option value="None"></form:option>
						<form:options items="${qoccriteria}"/>
					</form:select>			
				</div>
				<div class="form-group col-md-4">
					<form:select path="qoCComparison" cssClass="form-control">
						<form:option value="lessthan" label="Less than"/>
						<form:option value="<=" label="Less than or equal"/>
						<form:option value=">" label="Greater than"/>
						<form:option value=">=" label="Greater than or equal"/>
						<form:option value="=" label="Equal"/>
					</form:select>			
				</div>
				<div class="form-group col-md-4">
					<form:input path="qoCValue" type="number" class="form-control"/>
				</div>
			</div>
		</div>		
	</div>
	<div class="panel-footer clearfix">
		<input type="button" value="Search"
			class="btn btn-default pull-right" onclick="submitForm();">
	</div>
</form:form>