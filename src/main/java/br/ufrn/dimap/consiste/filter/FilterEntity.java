package br.ufrn.dimap.consiste.filter;


import br.ufrn.dimap.consiste.utils.BaseEntity;


public class FilterEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
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
