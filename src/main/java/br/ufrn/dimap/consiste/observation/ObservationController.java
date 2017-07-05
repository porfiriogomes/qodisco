package br.ufrn.dimap.consiste.observation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/observation")
public class ObservationController {

	@Autowired
	private ObservationService observationService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model){
		model.addAttribute("domains", observationService.getDomainNames());
		model.addAttribute("observedproperties", observationService.getObservedProperties("Default"));
		model.addAttribute("qoccriteria", observationService.getAllCriterionURIs("Default"));
		model.addAttribute("observationsearch", new ObservationSearchEntity());
		return "observation.search";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String submitSearch(@ModelAttribute ObservationSearchEntity observationSearch,
			Model model, HttpServletRequest request){
		String domainSelected = request.getParameter("domain-selected");
		
		if(observationSearch.getQoCComparison().equals("lessthan"))
			observationSearch.setQoCComparison("<");
		
		model.addAttribute("observations", observationService.search(domainSelected, observationSearch));
		return "observation.results";
	}
	
	@RequestMapping(value="/search/domainoption", method=RequestMethod.GET)
	public String searchSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("observedproperties", observationService.getObservedProperties(option));
		model.addAttribute("qoccriteria", observationService.getAllCriterionURIs(option));
		model.addAttribute("observationsearch", new ObservationSearchEntity());
		return "observation.partials.search";
	}
	
}
