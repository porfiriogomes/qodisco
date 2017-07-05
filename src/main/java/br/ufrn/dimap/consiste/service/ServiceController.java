package br.ufrn.dimap.consiste.service;

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
@RequestMapping("/service")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newService(Model model){
		model.addAttribute("domains", serviceService.getDomainNames());
		model.addAttribute("resources", serviceService.getResourcesByDomain("Default"));
		return "service.new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createService(@ModelAttribute ServiceEntity service,
			RedirectAttributes redirectAttributes, HttpServletRequest request){
		String domainSelected = request.getParameter("domain-selected");
		
		if (service.getInputs()!=null){
			for(IOEntity input : service.getInputs()){
				if(input.getName() == null || input.getType() == null)
					service.getInputs().remove(input);
			}
		}

		if (service.getOutputs()!=null){
			for(IOEntity output : service.getOutputs()){
				if(output.getName() == null || output.getType() == null)
					service.getOutputs().remove(output);
			}
		}	
		serviceService.create(domainSelected, service);
		
		redirectAttributes.addFlashAttribute("success_message", "Service registered with success.");

		return "redirect:/service/new";
	}
	
	@RequestMapping(value="/new/domainoption", method=RequestMethod.GET)
	public String newSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("resources", serviceService.getResourcesByDomain(option));
		return "service.partials.new";
	}
	
	@RequestMapping(value="/addinput", method=RequestMethod.GET)
	public String addInput(@RequestParam(value="fieldid") Integer fieldId,
			@RequestParam(value="domainselected") String domainSelected, Model model){
		model.addAttribute("inputnumber", fieldId);
		model.addAttribute("units", serviceService.getUnits(domainSelected));
		return "service.partials.new.input";
	}
	
	@RequestMapping(value="/addoutput", method=RequestMethod.GET)
	public String addOutput(@RequestParam(value="fieldid") Integer fieldId,
			@RequestParam(value="domainselected") String domainSelected, Model model){
		model.addAttribute("outputnumber", fieldId);
		model.addAttribute("units", serviceService.getUnits(domainSelected));
		return "service.partials.new.output";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model){
		model.addAttribute("domains", serviceService.getDomainNames());
		model.addAttribute("resources", serviceService.getResourcesByDomain("Default"));
		return "service.search";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@ModelAttribute ServiceSearchEntity serviceSearch,
			HttpServletRequest request, Model model){
		String domainSelected = request.getParameter("domain-selected");
		model.addAttribute("services", serviceService.search(domainSelected, serviceSearch));
		return "service.results";
	}
	
	@RequestMapping(value="/search/domainoption", method=RequestMethod.GET)
	public String searchSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("resources", serviceService.getResourcesByDomain(option));
		return "service.partials.search";
	}
	
	@ModelAttribute("service")
	public ServiceEntity getService(){
		ServiceEntity service = new ServiceEntity();
		service.setInputs(new AutoPopulatingList<IOEntity>(IOEntity.class));
		service.setOutputs(new AutoPopulatingList<IOEntity>(IOEntity.class));
		return service;
	}
	
	@ModelAttribute("servicesearch")
	public ServiceSearchEntity getServiceSearch(){
		return new ServiceSearchEntity();
	}
	
}
