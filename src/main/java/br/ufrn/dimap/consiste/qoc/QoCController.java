package br.ufrn.dimap.consiste.qoc;

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
@RequestMapping("/qoc")
public class QoCController {
	
	@Autowired
	private QoCService qoCService;
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newQoC(Model model) {
		model.addAttribute("qoc", new QoCCriterionEntity());
		model.addAttribute("qoccriteria", qoCService.getAllCriterionURIs("Default"));
		model.addAttribute("units", qoCService.getUnits("Default"));
		model.addAttribute("domains", qoCService.getDomainNames());
		return "qoc.new";
	}

	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createQoC(@ModelAttribute QoCCriterionEntity qoCCriterion,
			RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String domainSelected  = request.getParameter("domain-selected");
		qoCService.create(domainSelected, qoCCriterion);
		
		redirectAttributes.addFlashAttribute("success_message", "QoC Criterion registered with success.");
		return "redirect:/qoc";
	}
	
	@RequestMapping(value="/new/domainoption", method=RequestMethod.GET)
	public String newSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("qoc", new QoCCriterionEntity());
		model.addAttribute("qoccriteria", qoCService.getAllCriterionURIs(option));
		model.addAttribute("units", qoCService.getUnits(option));
		return "qoc.partials.new";
	}

	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("domains", qoCService.getDomainNames());
		model.addAttribute("qoccriteria", qoCService.getAllCriteria("Default"));
		return "qoc.list";
	}
	
	@RequestMapping(value="/domainoption", method=RequestMethod.GET)
	public String listSelectDomainOption(@RequestParam String option, Model model){
		model.addAttribute("qoccriteria", qoCService.getAllCriteria(option));
		return "qoc.partials.list";
	}
	
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public String show(@RequestParam("qocname") String qoCName,
			@RequestParam("repositoryurl") String repositoryUrl, Model model){
		model.addAttribute("criterion", qoCService.getCriterion(qoCName, repositoryUrl));
		return "qoc.show";
	}
	
}
