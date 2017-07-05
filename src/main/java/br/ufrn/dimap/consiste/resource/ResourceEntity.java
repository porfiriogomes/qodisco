package br.ufrn.dimap.consiste.resource;

import java.util.List;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class ResourceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<String> properties;
	private String type;
	private String location;
	private String description;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getProperties() {
		return properties;
	}
	
	public void setProperties(List<String> properties) {
		this.properties = properties;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
		
}
