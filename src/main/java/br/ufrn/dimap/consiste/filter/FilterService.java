package br.ufrn.dimap.consiste.filter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufrn.dimap.consiste.topic.TopicEntity;

public class FilterService {

	private static final Logger LOGGER = Logger.getLogger(FilterService.class); 

		
	public static FilterEntity getFilterByName(String filterName, TopicEntity topic) {
		for(int i = 0; i < topic.getAsyncFilter().size(); i++) {
			if (topic.getAsyncFilter().get(i).getFilterName().equals(filterName)) {
				return topic.getAsyncFilter().get(i);
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
	
	public static boolean executeFilter(TopicEntity topic, NodeList list) {
		
		List<FilterEntity> filterList = topic.getAsyncFilter();
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

	public static List<FilterEntity> createFilter(String filters){
		List<FilterEntity> list = new ArrayList<FilterEntity>();
	
		JSONArray json = new JSONArray(filters);

		for (int i = 0 ; i < json.length(); i++) {
			JSONObject j = json.getJSONObject(i);
			if (!(j.getString("name").equals("freshness") || j.getString("name").equals("resolution"))) {
				LOGGER.error("Unkown filter");
				break;
			}
			FilterEntity filter = new FilterEntity(j.getString("name"), j.getDouble("value"));
			list.add(filter);
		}	
		return list;
	}

}
