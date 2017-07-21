package br.ufrn.dimap.consiste.filter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ufrn.dimap.consiste.utils.BaseEntity;


public class FilterEntity extends BaseEntity{
	
	private String filterName;
	
	private double value;
	
	public FilterEntity(String filterName, double value) {
		super();
		this.filterName = filterName;
		this.value = value;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
