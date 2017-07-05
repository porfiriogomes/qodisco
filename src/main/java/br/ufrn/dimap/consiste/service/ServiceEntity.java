package br.ufrn.dimap.consiste.service;

import java.util.List;

import br.ufrn.dimap.consiste.utils.BaseEntity;

public class ServiceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;
	private String resource;
	private String description;
	private List<IOEntity> inputs;
	private List<IOEntity> outputs;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<IOEntity> getInputs() {
		return inputs;
	}
	
	public void setInputs(List<IOEntity> inputs) {
		this.inputs = inputs;
	}
	
	public List<IOEntity> getOutputs() {
		return outputs;
	}
	
	public void setOutputs(List<IOEntity> outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		
		StringBuilder inputsSb = new StringBuilder();
		String aux = "";
		if(this.inputs!=null){
			for(IOEntity i : this.inputs){
				inputsSb.append(aux).append(i.toString());
				aux = ", ";
			}
		}
		
		StringBuilder outputsSb = new StringBuilder();
		aux = "";
		if(this.inputs!=null){
			for(IOEntity o : this.outputs){
				outputsSb.append(aux).append(o.toString());
				aux = ", ";
			}
		}
		
		return "Service [name=" + name + ", resource=" + resource + ", description=" + description
				+ ", inputs=" + inputsSb.toString() + ", outputs=" + outputsSb.toString() + "]";
	}
	
}
