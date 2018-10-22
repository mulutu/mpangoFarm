package com.mpango.core.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.mpango.core.model.Customer;
import com.mpango.core.model.forms.CustomerForm;
import com.mpango.core.model.forms.ProjectForm;
import com.mpango.core.model.forms.SupplierForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/customers" })
public class CustomerController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	private static RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String customerList(Model model) {

		log.debug("CustomerController->addCustomer() : >>>> model {}", model);

		if (!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) { // if(!isAuthenticated())// {
			log.error("CustomerController->customerList() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}

		int userID = UserInterceptor.getLoggedUserID();

		String URL_CUSTOMERS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/customers/user/"
				+ userID;
		Customer[] customers = restTemplate.getForObject(URL_CUSTOMERS, Customer[].class);
		model.addAttribute("customers", customers);

		return "customers";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String addCustomer(Model model) {

		log.debug("CustomerController->addCustomer() : >>>> model {}", model);

		if (!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0) { // if(!isAuthenticated())
																															// {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}

		CustomerForm customerForm = new CustomerForm();
		model.addAttribute("customerForm", customerForm);

		return "addCustomer";
	}

	/*
	 * Add Supplier
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveCustomer(Model model, @ModelAttribute("customerForm") CustomerForm customerForm) {

		if (!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0) { // if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}

		String URL_ADD_CUSTOMER = "http://localhost:8084/MpangoFarmEngineApplication/api/customer/";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		int userID = UserInterceptor.getLoggedUserID();		
		String customerName = customerForm.getCustomerNames();
		String description = customerForm.getDescription();
		
		log.error("CustomrrController->saveCustomer() : >>>> customerName {}", customerName);
		log.error("CustomrrController->saveCustomer() : >>>> description {}", description);
		log.error("CustomrrController->saveCustomer() : >>>> userID {}", userID);

		if (customerName != null) {
			Customer newCustomer = new Customer(date, userID, customerName, description);
			String postResponse = restTemplate.postForObject(URL_ADD_CUSTOMER, newCustomer, String.class);
			return "redirect:/customers/list";
		}
		return "addCustomer";
	}

}
