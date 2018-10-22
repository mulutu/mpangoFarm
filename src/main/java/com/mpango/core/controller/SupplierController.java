package com.mpango.core.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.mpango.core.model.Farm;
import com.mpango.core.model.MyUser;
import com.mpango.core.model.Project;
import com.mpango.core.model.Supplier;
import com.mpango.core.model.forms.ProjectForm;
import com.mpango.core.model.forms.SupplierForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/suppliers" })
public class SupplierController {

	private static final Logger log = LoggerFactory.getLogger(SupplierController.class);
	
	private static RestTemplate restTemplate = new RestTemplate();
	

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String supplierList(Model model) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int userID = UserInterceptor.getLoggedUserID();
		
		String URL_SUPPLIERS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/suppliers/user/" + userID;
		Supplier[] suppliers = restTemplate.getForObject(URL_SUPPLIERS, Supplier[].class);
		model.addAttribute("suppliers", suppliers);
		return "suppliers";
	}
	
	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addSupplier(Model model) {

		log.debug("SupplierController->addSupplier() : >>>> model {}", model);	
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}

		SupplierForm supplierForm = new SupplierForm();
		model.addAttribute("supplierForm", supplierForm);

		return "addSupplier";
	}
	
	/*
	 * Add Supplier
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveSupplier(Model model, @ModelAttribute("supplierForm") SupplierForm supplierForm ) {
		
		log.error("SupplierController->saveSupplier() ");
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("SupplierController->saveSupplier() >>>> NO authentication {}");
			return "redirect:/user/login?error";
		}
		
		String URL_ADD_SUPPLIER= "http://localhost:8084/MpangoFarmEngineApplication/api/supplier/";
		
		int userID = UserInterceptor.getLoggedUserID();
		String supplierName = supplierForm.getSupplierName();		
		String description = supplierForm.getDescription();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		if (supplierName != null ) {
			Supplier newSupplier = new Supplier(date, userID, supplierName, description); 
			String postResponse = restTemplate.postForObject(URL_ADD_SUPPLIER, newSupplier, String.class);
			return "redirect:/suppliers/list";
		}
		log.error("SupplierController->saveSupplier() >>> supplierName == null {} ", supplierName );
		return "addSupplier";
	}

}
