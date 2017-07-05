package br.ufrn.dimap.consiste.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufrn.dimap.consiste.utils.BaseEntity;

@Embeddable
public class RepositoryUnavailableTimeEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_check")
	private Date lastCheck;
	
	private Long time;
	
	public RepositoryUnavailableTimeEntity() {}

	public RepositoryUnavailableTimeEntity(Date lastCheck, Long time) {
		super();
		this.lastCheck = lastCheck;
		this.time = time;
	}

	public Date getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
