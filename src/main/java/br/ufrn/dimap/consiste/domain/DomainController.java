package br.ufrn.dimap.consiste.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/domain")
public class DomainController {

	@Autowired
	private DomainService domainService;

	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("domains", domainService.getDomains());
		return "domain.list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newDomain(Model model) {
		model.addAttribute("domain", new DomainEntity());
		return "domain.new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createDomain(@ModelAttribute DomainEntity domain, Model model,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (domainService.save(domain)) {
			model.addAttribute("domain", new DomainEntity());
			redirectAttributes.addFlashAttribute("success_message", "Domain was registered with success.");
		} else {
			result.reject("name", "Duplicated domain name.");
		}
		
		return "redirect:/domain";
	}
	
	@RequestMapping(value="/edit")
	public String editDomain(Model model, @RequestParam("name") String domainName) {
		DomainEntity domain = domainService.getDomainByName(domainName);
		model.addAttribute("domain", domain);
		return "domain.new";
	}
	
}
