package br.ufrn.dimap.consiste.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public UserEntity getUser() {
		return new UserEntity();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String newAccount(Model model) {
		return "user.new";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String createAccount(@ModelAttribute("user") @Validated UserEntity user, BindingResult result,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {
		
		String passwordConfirmation = request.getParameter("passwordconfirmation");
				
		if (result.hasErrors()) {
			return "user.new";
		}
		
		if (!user.getPassword().equals(passwordConfirmation)) {
			result.rejectValue("password", "messageCode", "Password does not match the confirm password.");
			return "user.new";
		}
		
		if (userService.exists(user.getUsername())) {
			result.rejectValue("username", "messageCode", "User with this username already exists.");
			return "user.new";
		}

		try {
			userService.create(user);
		} catch (Exception e) {
			result.rejectValue("username", "messageCode", "Error registering user, please try again later.");
			return "user.new";
		}

		// Signing in user after registering		
		UserDetails userAuthenticated = userService.getUser(user.getUsername());
		Authentication auth = new UsernamePasswordAuthenticationToken(userAuthenticated, user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
		
		redirectAttributes.addFlashAttribute("success_message", "You have sign up with success.");
		
		return "redirect:/";
	}
	
}
