package br.ufrn.dimap.consiste.topic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.user.UserEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;

@Entity
@Table(name="tb_topic")
public class TopicEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
	private String topic;
	
	@NotBlank
	@Lob
	private String query;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private DomainEntity domain;
		
	@NotBlank
	private String brokerAddress;
	
	@ManyToOne
	@JoinColumn
	@NotNull
	private UserEntity user;
	
	public TopicEntity() {}

	public TopicEntity(String topic, String query, DomainEntity domain, String brokerAddress, UserEntity user) {
		super();
		this.topic = topic;
		this.query = query;
		this.domain = domain;
		this.brokerAddress = brokerAddress;
		this.user = user;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@JsonIgnore
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@JsonIgnore
	public DomainEntity getDomain() {
		return domain;
	}

	public void setDomain(DomainEntity domain) {
		this.domain = domain;
	}

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public void setBrokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
	}
	
	@JsonIgnore
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
