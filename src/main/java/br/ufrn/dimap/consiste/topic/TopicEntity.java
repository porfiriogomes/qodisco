package br.ufrn.dimap.consiste.topic;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.user.UserEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;
import br.ufrn.dimap.consiste.filter.FilterEntity;

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
	
	@OneToMany
	@JoinColumn
	private List<FilterEntity> asyncFilter;
	
	@Column(updatable=true)
	private double lastQuerySendDate;
	
	@Column(updatable=true)
	private double lastQuerySendValue;
	
	public TopicEntity() {}

	public TopicEntity(String topic, String query, DomainEntity domain, String brokerAddress, UserEntity user, List<FilterEntity> asyncFilter) {
		super();
		this.topic = topic;
		this.query = query;
		this.domain = domain;
		this.brokerAddress = brokerAddress;
		this.user = user;
		this.lastQuerySendDate = 0;
		this.lastQuerySendValue = 0;
		this.asyncFilter = asyncFilter;
	}
	
	public TopicEntity(String topic, String query, DomainEntity domain, String brokerAddress, UserEntity user) {
		this(topic, query, domain, brokerAddress, user, null);
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
	
	public boolean hasFilter() {
		boolean hasFilter = true;
		if (this.asyncFilter==null || this.asyncFilter.isEmpty()) hasFilter=false;
		return (hasFilter);
	}
	
	public List<FilterEntity> getAsyncFilter() {
		return this.asyncFilter;
	}
	
	
	public void setAsyncFilter(List<FilterEntity> asyncFilter) {
		this.asyncFilter = asyncFilter;
	}
	
	public double getLastQuerySendDate() {
		return this.lastQuerySendDate;
	}
	
	public void setLastQuerySendDate(double date) {
		this.lastQuerySendDate = date;
	}
	
	public double getLastQuerySendValue() {
		return this.lastQuerySendValue;
	}
	
	public void setLastQuerySendValue(double value) {
		this.lastQuerySendValue = value;
	}
}
