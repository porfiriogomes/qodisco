package br.ufrn.dimap.consiste.observation;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class ObservationEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String observedBy;
	private String value;
	private String date;
	private String qoCValue;
	private String criterion;
	private String name;
	private String observedProperty;
	
	public String getObservedBy() {
		return observedBy;
	}
	
	public void setObservedBy(String observedBy) {
		this.observedBy = observedBy;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getQoCValue() {
		return qoCValue;
	}
	
	public void setQoCValue(String qoCValue) {
		this.qoCValue = qoCValue;
	}
	
	public String getCriterion() {
		return criterion;
	}
	
	public void setCriterion(String criterion) {
		this.criterion = criterion;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getObservedProperty() {
		return observedProperty;
	}
	
	public void setObservedProperty(String observedProperty) {
		this.observedProperty = observedProperty;
	}
	
}
