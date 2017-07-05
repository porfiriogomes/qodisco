package br.ufrn.dimap.consiste.repository;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.user.UserEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;

@Entity
@Table(name="tb_repository")
public class RepositoryEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	private String url;
	
	@ManyToMany
	private Set<DomainEntity> domains;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private UserEntity user;
	
	@ElementCollection
	@CollectionTable(name="tb_operation", joinColumns=@JoinColumn(name="repository_id"))
	@Enumerated(EnumType.STRING)
	private Set<RepositoryOperationTypeEnum> operations;
	
	@Embedded
	private RepositoryUnavailableTimeEntity repositoryUnavailableTimeEntity;
	
	public RepositoryEntity() {}

	public RepositoryEntity(Integer id, String url, Set<DomainEntity> domains, UserEntity user,
			Set<RepositoryOperationTypeEnum> operations,
			RepositoryUnavailableTimeEntity repositoryUnavailableTimeEntity) {
		super();
		this.id = id;
		this.url = url;
		this.domains = domains;
		this.user = user;
		this.operations = operations;
		this.repositoryUnavailableTimeEntity = repositoryUnavailableTimeEntity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<DomainEntity> getDomains() {
		return domains;
	}

	public void setDomains(Set<DomainEntity> domains) {
		this.domains = domains;
	}

	@JsonIgnore
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<RepositoryOperationTypeEnum> getOperations() {
		return operations;
	}

	public void setOperations(Set<RepositoryOperationTypeEnum> operations) {
		this.operations = operations;
	}

	@JsonIgnore
	public RepositoryUnavailableTimeEntity getRepositoryUnavailableTimeEntity() {
		return repositoryUnavailableTimeEntity;
	}

	public void setRepositoryUnavailableTimeEntity(RepositoryUnavailableTimeEntity repositoryUnavailableTimeEntity) {
		this.repositoryUnavailableTimeEntity = repositoryUnavailableTimeEntity;
	}
	
}
