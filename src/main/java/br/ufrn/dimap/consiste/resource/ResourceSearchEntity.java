package br.ufrn.dimap.consiste.resource;

import java.util.List;

import br.ufrn.dimap.consiste.utils.BaseEntity;

// helper class to built search queries using the web interface of QoDisco
// this class can be further modified to include other attributes as search parameters
public class ResourceSearchEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String searchFor;
	private List<String> properties;
	private String location;
	
	public String getSearchFor() {
		return searchFor;
	}
	
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	
	public List<String> getProperties() {
		return properties;
	}
	
	public void setProperties(List<String> properties) {
		this.properties = properties;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
}
