<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#domain-option").on('change', function(){
		var selectedOption = $(this).find(":selected").val();
		$.get("<%=request.getContextPath()%>/location/new/domainoption", { option: selectedOption },
			function(data){
				$("#box-location").html(data);	
		});
	});	
});

function submitForm() {
	$('input[name="domain-selected"]').val($('#domain-option').find(':selected').val());
	$('#newlocation-form').submit();
}

</script>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">New Location</h2>
		</div>
		<div class="panel-body">
			<div class="form-group">
				<label for="domain-option">Domain</label>
				<select id="domain-option" name="domain-option" class="form-control">
					<c:forEach items="${domains}" var="aux">
						<c:choose>
							<c:when test="${aux eq 'Default'}">
								<option value="${aux}" selected>${aux}</option>
							</c:when>
							<c:otherwise>
								<option value="${aux}">${aux}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
		</div>
		<form:form id="newlocation-form" method="post" action="${path}/location/new" modelAttribute="location" role="form">
			<input type="hidden" name="domain-selected"/>
			<div class="panel-body">
				<div id="box-location">
					<jsp:include page="partials/new-partial.jsp"/>
				</div>
			</div>
			<div class="panel-footer clearfix">
				<input type="button" value="Save"
					class="btn btn-default pull-right" onclick="submitForm();">
			</div>
		</form:form>
	</div>
</div>