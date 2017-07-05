<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-md-9">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h2 class="panel-title">QoC Criterion</h2>
		</div>
		<div class="panel-body">
			<h5><strong>Name:</strong> ${criterion.name}</h5>
			<h5><strong>Description:</strong> ${criterion.description}</h5>
			<h5><strong>Is Invariant?:</strong> ${criterion.invariant}</h5>
			<h5><strong>Direction:</strong> ${criterion.direction}</h5>
			<h5><strong>Maximum Value:</strong> ${criterion.maximumValue}</h5>
			<h5><strong>Minimum Value:</strong> ${criterion.minimumValue}</h5>
			<h5><strong>Unit:</strong> ${criterion.unit}</h5>
			<h5><strong>Composed by:</strong>
			<c:choose>
				<c:when test="${criterion.composedBy != null}">
					 ${criterion.composedBy}
				</c:when>
				<c:otherwise>
					This QoC Criterion is not composed of other QoC Criteria.
				</c:otherwise>
			</c:choose>
			</h5>
		</div>
	</div>
</div>