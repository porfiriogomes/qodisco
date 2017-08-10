package br.ufrn.dimap.consiste.filter;

import br.ufrn.dimap.consiste.utils.BaseEntity;

/**
 * This class implements the filter used in an asynchronous search. These filters allow you to filter
 * data directly over QoDisco. Two filters exists, one over the freshness of data and one over the 
 * resolution (or the scale) of the data's value.
 * 
 * @author courtais
 *
 */

public class FilterEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * String to represent the filter name.
	 * Could be either "freshness" or "resolution".
	 */
	private String filterName;
	
	/**
	 * The value of the filter.
	 */
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
