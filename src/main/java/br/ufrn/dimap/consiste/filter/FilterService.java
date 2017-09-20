package br.ufrn.dimap.consiste.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufrn.dimap.consiste.topic.TopicEntity;
import br.ufrn.dimap.consiste.topic.TopicRepository;

@Service
public class FilterService {

	private static final Logger LOGGER = Logger.getLogger(FilterService.class); 

	@Autowired
	public FilterRepository filterRepository;

	public void saveFilter(FilterEntity filter) {
		filterRepository.save(filter);
	}
	
	public void removeFilter(String topicName) {
		List<FilterEntity> filters = filterRepository.findAllByTopic(topicName);
		for(int i = 0; i < filters.size(); i++) {
			filterRepository.delete(filters.get(i));
		}
	}
		
	public FilterEntity getFilterByName(String filterName, TopicEntity topic) {
		List<FilterEntity> filters = filterRepository.findAllByTopic(topic.getTopic());
		for(int i = 0; i < filters.size(); i++) {
			if (filters.get(i).getFilterName().equals(filterName)) {
				return filters.get(i);
			}
		}
		return null;
	}
	
	public static boolean filterFreshness(TopicEntity topic, FilterEntity filter, NodeList list) {

		
        if (list != null && list.getLength() > 0) {
        	for (int i = 0; i < list.getLength(); i++) {
        		Node n = list.item(i);
        		String attribut = n.getAttributes().getNamedItem("name").getNodeValue();
        		if (attribut.equals("date")){
        			LOGGER.info("Freshness Filter " + n.getChildNodes().item(1).getFirstChild().getNodeValue() + " minus " + topic.getLastQuerySendDate());
        			return ((Double.parseDouble(n.getChildNodes().item(1).getFirstChild().getNodeValue()) - topic.getLastQuerySendDate()) > (Double) filter.getValue());
        		}
        	}
        }		
		return false;
	}
	
	public static boolean filterResolution(TopicEntity topic, FilterEntity filter, NodeList list) {

        if (list != null && list.getLength() > 0) {
        	for (int i = 0; i < list.getLength(); i++) {
        		Node n = list.item(i);
        		String attribut = n.getAttributes().getNamedItem("name").getNodeValue();
        		if (attribut.equals("value")){
        			return (Math.abs(topic.getLastQuerySendValue() - Double.parseDouble(n.getChildNodes().item(1).getFirstChild().getNodeValue())) > (Double) filter.getValue());
        		}
        	}
        }
		return false;
		
	}
	
	public boolean executeFilter(TopicEntity topic, NodeList list) {
		
		List<FilterEntity> filterList = filterRepository.findAllByTopic(topic.getTopic());
		boolean fresh = true;
		boolean res = true;
		
		for (int i = 0; i < filterList.size(); i++) {
			if (filterList.get(i).getFilterName().equals("resolution")) {
				res = filterResolution(topic, filterList.get(i), list);
			}
			if (filterList.get(i).getFilterName().equals("freshness")) {
				fresh = filterFreshness(topic, filterList.get(i), list);
			}
		}
		return (fresh && res);
	}

	public List<FilterEntity> createFilter(String filters, String topicName){
		List<FilterEntity> list = new ArrayList<FilterEntity>();
	
		JSONArray json = new JSONArray(filters);

		for (int i = 0 ; i < json.length(); i++) {
			JSONObject j = json.getJSONObject(i);
			if (!(j.getString("name").equals("freshness") || j.getString("name").equals("resolution"))) {
				LOGGER.error("Unkown filter");
				break;
			}
			FilterEntity filter = new FilterEntity(j.getString("name"), j.getDouble("value"), topicName);
			list.add(filter);
		}	
		return list;
	}

}
