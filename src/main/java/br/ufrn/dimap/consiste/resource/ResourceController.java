package br.ufrn.dimap.consiste.resource;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;

	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newResource(Model model) {
		model.addAttribute("domains", resourceService.getDomainNames());
		model.addAttribute("devicetypes", resourceService.getDeviceTypes("Default"));
		model.addAttribute("locations", resourceService.getLocations("Default"));
		
		return "resource.new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createResource(@ModelAttribute ResourceEntity resource, RedirectAttributes redirectAttributes,
			HttpServletRequest request){
		String domainSelected = request.getParameter("domain-selected");
		
		if (resource.getProperties() != null)
			resource.getProperties().removeAll(Collections.singleton(null));
		
		resourceService.create(domainSelected, resource);
		
		redirectAttributes.addFlashAttribute("success_message", "Resource registered with success");
		return "redirect:/resource/new";
	}
	
	@RequestMapping(value="/new/domainoption", method=RequestMethod.GET)
	public String newSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("locations", resourceService.getLocations(option));
		model.addAttribute("devicetypes", resourceService.getDeviceTypes(option));

		return "resource.partials.new";
	}
	
	@RequestMapping(value="/addproperty", method=RequestMethod.GET)
	public String addProperty(@RequestParam(value="fieldid") Integer fieldId,
			@RequestParam(value="domainselected") String domainSelected, Model model){
		model.addAttribute("propertiesnumber", fieldId);
		model.addAttribute("observedproperties", resourceService.getObservedProperties(domainSelected));
		return "resource.partials.new.properties";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model){
		model.addAttribute("domains", resourceService.getDomainNames());
		model.addAttribute("locations", resourceService.getLocations("Default"));
		model.addAttribute("devicetypes", resourceService.getDeviceTypes("Default"));
		
		return "resource.search";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String submitSearch(@ModelAttribute ResourceSearchEntity resourceSearch, Model model, HttpServletRequest request){
		String domainSelected = request.getParameter("domain-selected");
		model.addAttribute("resources", resourceService.search(domainSelected, resourceSearch));
		return "resource.results";
	}
	
	@RequestMapping(value="/search/domainoption", method=RequestMethod.GET)
	public String searchSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("locations", resourceService.getLocations(option));
		model.addAttribute("devicetypes", resourceService.getDeviceTypes(option));
				
		return "resource.partials.search";
	}
	
	@ModelAttribute("resource")
	public ResourceEntity getResource(){
		ResourceEntity resource = new ResourceEntity();
		resource.setProperties(new AutoPopulatingList<String>(String.class));
		return resource;
	}

	@ModelAttribute("resourcesearch")
	public ResourceSearchEntity getResourceSearch(){
		ResourceSearchEntity resourceSearch = new ResourceSearchEntity();
		resourceSearch.setProperties(new AutoPopulatingList<String>(String.class));
		return resourceSearch;
	}

}
