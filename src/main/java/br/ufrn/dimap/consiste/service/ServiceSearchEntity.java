package br.ufrn.dimap.consiste.service;

import br.ufrn.dimap.consiste.utils.BaseEntity;

// This class can/should be extended to implement new parameters for the service search page
public class ServiceSearchEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String resource;
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
}
