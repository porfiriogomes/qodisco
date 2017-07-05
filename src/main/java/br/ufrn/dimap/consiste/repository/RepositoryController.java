package br.ufrn.dimap.consiste.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufrn.dimap.consiste.domain.DomainEntity;

@Controller
@RequestMapping("/repository")
public class RepositoryController {

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RepositoryDomainConverter repositoryDomainConverter;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(DomainEntity.class, repositoryDomainConverter);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model) {
		List<RepositoryEntity> repositories = repositoryService.getRepositoriesFromLoggedUser();
		model.addAttribute("repositories", repositories);
		return "repository.list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newRepository(Model model) {
		model.addAttribute("repository", new RepositoryEntity());
		model.addAttribute("domains", repositoryService.getDomains());
		
		return "repository.new";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String createRepository(@ModelAttribute(value="repository") RepositoryEntity repository,
			RedirectAttributes redirectAttributes) {

		if (repository!=null) {
			repositoryService.save(repository);
			redirectAttributes.addFlashAttribute("success_message", "Repository Registered with Success.");
		}
		return "redirect:/repository";
	}
	
	@RequestMapping(value="/edit")
	public String editRepository(Model model, @RequestParam("id") Integer repositoryId) {
		RepositoryEntity repository = repositoryService.getRepository(repositoryId);
		model.addAttribute("repository", repository);
		model.addAttribute("domains", repositoryService.getDomains());
		return "repository.new";
	}
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.OK)
	public void removeRepository(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
		repositoryService.delete(id);
	}
	
}
