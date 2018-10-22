package com.mpango.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.mpango.core.model.MyUser;
import com.mpango.core.model.forms.LoginForm;
import com.mpango.core.model.forms.RegisterForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@SessionAttributes("userDetails")
@RequestMapping(value = { "/user" })
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	private static RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public String dashboard(Model model ) {
		
		if(!UserInterceptor.isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		return "dashboard";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model, @RequestParam(value = "error",required = false) String error,
			@RequestParam(value = "logout",	required = false) String logout) {
		LoginForm loginForm = new LoginForm();
		
		if (error != null) {
			model.addAttribute("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out from Mpango successfully.");
		}
		
		model.addAttribute("loginForm", loginForm);
		return "login";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String loginUser(ModelAndView modelAndView, @ModelAttribute("loginForm") LoginForm loginForm ) {

		log.debug("UserController->loginUser(): ");		

		String email = loginForm.getEmail();
		String password = loginForm.getPassword();

		if (!email.isEmpty() && !password.isEmpty()) {

			UsernamePasswordAuthenticationToken tkn = new UsernamePasswordAuthenticationToken(email, password, null);

			Authentication authentication = authenticationManager.authenticate(tkn);

			if (authentication.isAuthenticated()) {
				
				//SecurityContextHolder.getContext().setAuthentication(authentication);				
				SecurityContext securityContext = SecurityContextHolder.getContext();
			    securityContext.setAuthentication(authentication);

			    // Create a new session and add the security context.
			    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
				HttpSession session = attr.getRequest().getSession(true); // true == allow create				
			    //HttpSession session = request.getSession(true);
			    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
			    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

				log.debug("UserController->loginUser():  session.getId()) {} ", session.getId());
				
				MyUser userDetails = (MyUser) authentication.getPrincipal();
				session.setAttribute("userDetails", userDetails);
				//modelAndView.addObject("userDetails", userDetails);

				return "redirect:dashboard";
			}
		} else {
			modelAndView.addObject("loginError", "Invalid credentials");
			//return new RedirectView("login");
			return "redirect:login";
		}
		return "redirect:login";
	}

	/*
	 * Process confirmation link
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String showConfirmationPage(Model model, @RequestParam("token") String token) {
		model.addAttribute("token", token);
		String URL_TOKEN = "http://localhost:8084/MpangoFarmEngineApplication/api/confirmtoken/" + token;

		ResponseEntity<String> response = restTemplate.getForEntity(URL_TOKEN, String.class);

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println(" sdjhf sdkfsdjfhjsdk fsdh >>>>>>> " + response.getStatusCode());
			return "redirect:/login";
		}
		return "confirm";
	}

	/*
	 * Create/register a new user
	 */
	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	public String registerUser(Model model, @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult) {
		String Email = registerForm.getEmail();
		String Username = registerForm.getEmail();
		String Password = registerForm.getPassword();

		String URL_REGISTER = "http://localhost:8084/MpangoFarmEngineApplication/api/register2/";

		if (!Email.isEmpty() && !Password.isEmpty()) {
			MyUser newUser = new MyUser(Email, Username, Password, "", "");
			model.addAttribute("newUser", newUser);
			try {
				restTemplate.postForObject(URL_REGISTER, newUser, String.class);
			} catch (HttpStatusCodeException e) {
				String statusCode = e.getStatusCode().name();
				if (statusCode.equalsIgnoreCase("CONFLICT")) {
					model.addAttribute("errorMessage", "USER EXISTS");
				} else {
					model.addAttribute("errorMessage", "ERROR OCCURED");
				}
				return "registerfailed";
			}
		}
		return "redirect:/login";
	}
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		
		log.debug("UserController->logoutPage():  session.getId()) {} ", request);
		
		Authentication authentication = null;
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false); // true == allow create
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		try{
			authentication = securityContext.getAuthentication();
			new SecurityContextLogoutHandler().logout(request, response, authentication);	
		}catch(NullPointerException e) {
			log.error("UserController->dashboard() : >>>> logoutPage >>>>  NOOO authentication {}", authentication);
		}	    
	    
	    return "redirect:/user/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}

}
