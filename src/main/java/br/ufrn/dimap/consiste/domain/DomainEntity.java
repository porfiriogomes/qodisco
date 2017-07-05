package br.ufrn.dimap.consiste.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufrn.dimap.consiste.user.UserEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;

@Entity
@Table(name="tb_domain")
public class DomainEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	private String name;
	
	@NotBlank
	private String ontologyUri;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private UserEntity user;
	
	public DomainEntity() {}

	public DomainEntity(Integer id, String name, String ontologyUri, UserEntity user) {
		super();
		this.name = name;
		this.ontologyUri = ontologyUri;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOntologyUri() {
		return ontologyUri;
	}

	public void setOntologyUri(String ontologyUri) {
		this.ontologyUri = ontologyUri;
	}

	@JsonIgnore
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
