package br.ufrn.dimap.consiste.filter;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.ufrn.dimap.consiste.topic.TopicEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;

/**
 * This class implements the filter used in an asynchronous search. These filters allow you to filter
 * data directly over QoDisco. Two filters exists, one over the freshness of data and one over the 
 * resolution (or the scale) of the data's value.
 * 
 * @author courtais
 *
 */

@Entity
@Table(name="tb_filter")
public class FilterEntity extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String id;
	
	/**
	 * String to represent the filter name.
	 * Could be either "freshness" or "resolution".
	 */
	@Column
	private String filterName;
	
	/**
	 * The value of the filter.
	 */
	@Column
	private double value;
	
	@JoinColumn
	@NotNull
	private String async_filter_topic;
	
	public FilterEntity() {}
	
	public FilterEntity(String filterName, double value, String topicName) {
		super();
		this.id = "" + UUID.randomUUID();
		this.filterName = filterName;
		this.value = value;
		this.async_filter_topic = topicName;
	}
	
	public String getId() {
		return this.id;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public String getTopicName() {
		return this.async_filter_topic;
	}

}
