package br.ufrn.dimap.consiste.sparql;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/sparql")
public class SparqlController {

	@Autowired
	private SparqlService sparqlService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model) {
		model.addAttribute("domains", sparqlService.getDomainNames());
		return "sparql.search";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String submitSearch(Model model, HttpServletRequest request){
		String query = request.getParameter("sparql-query");
		String domainSelected = request.getParameter("domain-selected");
		
		String result = sparqlService.search(domainSelected, query);
		result = result.replaceAll("<", "&lt;");
		model.addAttribute("result", result);
		return "sparql.list";
	}
	
}
