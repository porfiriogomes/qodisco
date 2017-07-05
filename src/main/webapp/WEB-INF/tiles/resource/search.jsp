<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#domain-option").on('change', function(){
		var selectedOption = $(this).find(":selected").val();
		$.get("<%=request.getContextPath()%>/resource/search/domainoption", { option: selectedOption },
			function(data){
				$("#box-resource").html(data);	
		});
	});	
});
</script>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Search for Resources</h2>
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
		<hr/>
		<div id="box-resource">
			<jsp:include page="partials/search-partial.jsp"/>
		</div>
	</div>
</div>