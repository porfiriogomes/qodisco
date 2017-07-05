package br.ufrn.dimap.consiste.observation;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class ObservationSearchEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String observedProperty;
	private String qoCCriterion;
	private String qoCComparison;
	private Integer qoCValue;
	
	public String getObservedProperty() {
		return observedProperty;
	}
	
	public void setObservedProperty(String observedProperty) {
		this.observedProperty = observedProperty;
	}
	
	public String getQoCCriterion() {
		return qoCCriterion;
	}
	
	public void setQoCCriterion(String qoCCriterion) {
		this.qoCCriterion = qoCCriterion;
	}
	
	public String getQoCComparison() {
		return qoCComparison;
	}
	
	public void setQoCComparison(String qoCComparison) {
		this.qoCComparison = qoCComparison;
	}
	
	public Integer getQoCValue() {
		return qoCValue;
	}
	
	public void setQoCValue(Integer qoCValue) {
		this.qoCValue = qoCValue;
	}
	
}
