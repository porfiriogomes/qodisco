<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#domain-option").on('change', function(){
		var selectedOption = $(this).find(":selected").val();
		console.log('here');
		$.get("<%=request.getContextPath()%>/location/domainoption", { option: selectedOption },
			function(data){
				$("#box-location").html(data);	
		});
	});
});
</script>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">List of Locations</h2>
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
			
			<div id="box-location">
				<jsp:include page="partials/list-partial.jsp"/>
			</div>
		</div>

	</div>
</div>