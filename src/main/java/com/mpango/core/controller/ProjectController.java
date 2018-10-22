package com.mpango.core.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mpango.core.model.Expense;
import com.mpango.core.model.Farm;
import com.mpango.core.model.MyUser;
import com.mpango.core.model.Project;
import com.mpango.core.model.forms.ExpenseForm;
import com.mpango.core.model.forms.ProjectForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/projects" })
public class ProjectController {

	private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

	private static RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addProject(Model model,  HttpServletRequest request, BindingResult bindingResult) {

		log.debug("ProjectController->addProject() : >>>> model {}", model);	
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session

		if (bindingResult.hasErrors()) {
			return "redirect:/user/login";
		}
		
		ProjectForm projectForm = new ProjectForm();
		model.addAttribute("projectForm", projectForm);
	
		
		String URL_FARMS = "http://localhost:8084/MpangoFarmEngineApplication/api/farm/user/" + UserId;
		Farm[] farms = restTemplate.getForObject(URL_FARMS, Farm[].class);
		Arrays.asList(farms);
		model.addAttribute("farms", farms);


		return "addProject";
	}
	
	/*
	 * Add Project
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveProject(Model model, @ModelAttribute("projectForm") ProjectForm projectForm ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		String URL_ADD_PROJECT = "http://localhost:8084/MpangoFarmEngineApplication/api/project/";
		
		String projectName = projectForm.getProjectName();
		int farmID = projectForm.getFarmID();
		//Date dateCreated = new Date();		
		String description = projectForm.getDescription();

		if (projectName != null ) {
			Project newProject = new Project(UserId, farmID, projectName, description); 
			String postResponse = restTemplate.postForObject(URL_ADD_PROJECT, newProject, String.class);
			return "redirect:/expenses/list";
		}
		return "addExpense";
	}

}
