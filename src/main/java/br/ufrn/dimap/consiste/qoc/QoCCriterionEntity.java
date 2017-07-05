package br.ufrn.dimap.consiste.qoc;

import java.util.List;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class QoCCriterionEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private Boolean invariant;
	private String direction;
	private Integer minimumValue;
	private Integer maximumValue;
	private String unit;
	private List<String> composedBy;
	private String repositoryUrl;
	
	public QoCCriterionEntity() {}

	public QoCCriterionEntity(String name, String description, Boolean invariant, String direction,
			Integer minimumValue, Integer maximumValue, String unit, List<String> composedBy, String repositoryUrl) {
		super();
		this.name = name;
		this.description = description;
		this.invariant = invariant;
		this.direction = direction;
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
		this.unit = unit;
		this.composedBy = composedBy;
		this.repositoryUrl = repositoryUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getInvariant() {
		return invariant;
	}

	public void setInvariant(Boolean invariant) {
		this.invariant = invariant;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Integer getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(Integer minimumValue) {
		this.minimumValue = minimumValue;
	}

	public Integer getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(Integer maximumValue) {
		this.maximumValue = maximumValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<String> getComposedBy() {
		return composedBy;
	}

	public void setComposedBy(List<String> composedBy) {
		this.composedBy = composedBy;
	}
	
	public String getRepositoryUrl() {
		return repositoryUrl;
	}
	
	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}
	
}
