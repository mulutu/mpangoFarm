package com.mpango.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.mpango.core.model.Farm;
import com.mpango.core.model.MyUser;
import com.mpango.core.model.forms.FarmForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/farms" })
public class FarmController {

	private static final Logger log = LoggerFactory.getLogger(FarmController.class);

	private static RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addFarmForm(Model model) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		FarmForm farmForm = new FarmForm();
		model.addAttribute("farmForm", farmForm);
		return "addFarm";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveFarm(Model model, @ModelAttribute("farmForm") FarmForm farmForm) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		String URL_ADD_EXPENSE = "http://localhost:8084/MpangoFarmEngineApplication/api/farm/";
		String farmName = farmForm.getFarmName();
		int farmSize = farmForm.getSize();
		String location = farmForm.getLocation();
		String description = farmForm.getDescription();
		//int UserId = 1; // get from login session

		if (!farmName.isEmpty()) {
			Farm newFarm = new Farm(farmName, farmSize, location, description, UserId);
			String postResponse = restTemplate.postForObject(URL_ADD_EXPENSE, newFarm, String.class);
			return "redirect:/expenses";
		}
		// model.addAttribute("errorMessage", errorMessage);
		return "addFarm";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String farmList(Model model) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		String URL_FARMS = "http://localhost:8084/MpangoFarmEngineApplication/api/farm/user/" + UserId;
		Farm[] farms = restTemplate.getForObject(URL_FARMS, Farm[].class);
		model.addAttribute("farms", farms);
		return "farms";
	}

}
