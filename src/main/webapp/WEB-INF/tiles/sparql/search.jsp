<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">Search by Query</h2>
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
			<hr/>
			<div id="box-search-query">
				<form method="post" id="search-query-form" action="${path}/sparql/search">
					<div id="yasqe"></div>
					
					<input type="hidden" name="sparql-query"/>
					<input type="hidden" name="domain-selected"/>
								
				</form>
			</div>
		</div>
		<div class="panel-footer clearfix">
			<input type="button" value="Search"
				class="btn btn-default pull-right" onclick="submitForm();">
		</div>
	</div>
</div>

<script src='//cdn.jsdelivr.net/yasqe/2.2/yasqe.bundled.min.js'></script>

<script>
	var yasqe;
	
	$(document).ready(function(){
		yasqe = YASQE(document.getElementById("yasqe"), {
			sparql:{
				showQueryButton: false
			},
			createShareLink: null
		});
	});
	
	function submitForm(){
		$('input[name="sparql-query"]').val(yasqe.getValue());
		$('input[name="domain-selected"]').val($('#domain-option').find(":selected").val());
		$('#search-query-form').submit();
	}
</script>