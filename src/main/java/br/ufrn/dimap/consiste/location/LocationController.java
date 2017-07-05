package br.ufrn.dimap.consiste.location;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("domains", locationService.getDomainNames());
		model.addAttribute("locations", locationService.getAllLocationsFullDescription("Default"));
		return "location.list";
	}
	
	@RequestMapping(value="/domainoption", method=RequestMethod.GET)
	public String listSelectDomainOption(@RequestParam String option, Model model) {
		model.addAttribute("locations", locationService.getAllLocationsFullDescription(option));
		return "location.partials.list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newLocation(Model model) {
		model.addAttribute("domains", locationService.getDomainNames());
		model.addAttribute("fixedstructures", locationService.getAllFixedStructures("Default"));
		model.addAttribute("locations", locationService.getAllLocations("Default"));
		
		model.addAttribute("location", new LocationEntity());
		return "location.new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createLocation(@ModelAttribute LocationEntity location, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String domainSelected = request.getParameter("domain-selected");

		locationService.create(domainSelected, location);
		
		redirectAttributes.addFlashAttribute("success_message", "Location registered with success.");
		
		return "redirect:/location/new";
	}
	
	@RequestMapping(value="/new/domainoption", method=RequestMethod.GET)
	public String newSelectDomainOption(@RequestParam String option, Model model) {
		model.addAttribute("fixedstructures", locationService.getAllFixedStructures(option));
		model.addAttribute("locations", locationService.getAllLocations(option));
		
		model.addAttribute("location", new LocationEntity());
		return "location.partials.new";
	}
	
}
