package br.ufrn.dimap.consiste.location;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class LocationEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String parent;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private String type;
	private String url;
	
	public LocationEntity() {}
	
	public LocationEntity(String name, String parent, Double latitude,
			Double longitude, Double altitude, String type, String url) {
		super();
		this.name = name;
		this.parent = parent;
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.type = type;
		this.url = url;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getParent() {
		return parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getAltitude() {
		return altitude;
	}
	
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
