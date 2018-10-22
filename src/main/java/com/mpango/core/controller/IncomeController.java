package com.mpango.core.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.mpango.core.model.ChartOfAccounts;
import com.mpango.core.model.Expense;
import com.mpango.core.model.Income;
import com.mpango.core.model.MyUser;
import com.mpango.core.model.PaymentMethod;
import com.mpango.core.model.Project;
import com.mpango.core.model.Supplier;
import com.mpango.core.model.Customer;
import com.mpango.core.model.forms.ExpenseForm;
import com.mpango.core.model.forms.IncomeForm;
import com.mpango.core.model.forms.PayMethodForm;
import com.mpango.core.model.forms.SupplierForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/incomes" })
public class IncomeController {

	private static final Logger log = LoggerFactory.getLogger(IncomeController.class);

	private static RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String incomeList(Model model ) {
		log.debug("IncomeController->incomeList() >>> myuser {} ");
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int userID = UserInterceptor.getLoggedUserID();
		
		String URL_EXPENSES = "http://localhost:8084/MpangoFarmEngineApplication/api/income/user/" + userID;
		Income[] incomes = restTemplate.getForObject(URL_EXPENSES, Income[].class);
		
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.addObject("incomes", incomes);
		modelAndView.setViewName("incomes");
		
		model.addAttribute("incomes", incomes);
		
		log.debug("IncomeController->incomeList() : >>>> model {}", model);

		return "incomes";
	}

	/*
	 * Add Expense
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveIncome(Model model, @ModelAttribute("incomeForm") IncomeForm incomeForm ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		String URL_ADD_INCOME = "http://localhost:8084/MpangoFarmEngineApplication/api/income/";
		int CustomerId = incomeForm.getCustomerId();
		BigDecimal Amount = incomeForm.getAmount();
		int PaymentMethodId = incomeForm.getPaymentMethodId();
		int AccountId = incomeForm.getAccountId();
		int ProjectId = incomeForm.getProjectId();
		String Notes = incomeForm.getNotes();

		int UserId = UserInterceptor.getLoggedUserID(); // get from login session

		if (Amount != null) {
			Income newIncome = new Income(Amount, CustomerId, PaymentMethodId, AccountId, ProjectId, Notes, UserId);
			String postResponse = restTemplate.postForObject(URL_ADD_INCOME, newIncome, String.class);
			return "redirect:/incomes/list";
		}
		return "addIncome";
	}

	/*
	 * Update/Edit an income
	 */
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String updateIncome(Model model, @ModelAttribute("incomeForm") IncomeForm incomeForm ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session

		int IncomeId = incomeForm.getIncomeId();
		String IncomeDateStr = incomeForm.getIncomeDate();
		// int UserId = expenseForm.getUserId();
		int CustomerId = incomeForm.getCustomerId();
		BigDecimal Amount = incomeForm.getAmount();
		int PaymentMethodId = incomeForm.getPaymentMethodId();
		int AccountId = incomeForm.getAccountId();
		int ProjectId = incomeForm.getProjectId();
		String Notes = incomeForm.getNotes();
		
		

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date IncomeDate = null;
		try {
			IncomeDate = format.parse(IncomeDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String URL_UPDATE_INCOME = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/income/";
		
		log.error("IncomeController->updateIncome() : >>>> IncomeId {}", IncomeId);

		if (Amount != null) {
			Income newIncome = new Income();
			
			newIncome.setId(IncomeId);
			newIncome.setAmount(Amount);
			newIncome.setIncomeDate(IncomeDate);
			newIncome.setCustomerId(CustomerId);
			newIncome.setPaymentMethodId(PaymentMethodId);
			newIncome.setAccountId(AccountId);
			newIncome.setProjectId(ProjectId);
			newIncome.setUserId(UserId);
			
			String postResponse = restTemplate.postForObject(URL_UPDATE_INCOME, newIncome, String.class);
			return "redirect:/incomes/list";
		}
		return "editIncome";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String editIncome(Model model, @PathVariable("id") int id ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		IncomeForm incomeForm = new IncomeForm();
		model.addAttribute("incomeForm", incomeForm);
		
		String URL_EDIT_INCOME = "http://localhost:8084/MpangoFarmEngineApplication/api/income/" + id;
		Income income = restTemplate.getForObject(URL_EDIT_INCOME, Income.class);
		model.addAttribute("income", income);

		String URL_CUSTOMERS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/customers/user/" + UserId ;
		Customer[] customers = restTemplate.getForObject(URL_CUSTOMERS, Customer[].class);
		//Arrays.asList(suppliers);
		model.addAttribute("customers", customers);

		String URL_PAYMENTMETHODS = "http://localhost:8084/MpangoFarmEngineApplication/api/paymentmethods/";
		PaymentMethod[] paymentmethods = restTemplate.getForObject(URL_PAYMENTMETHODS, PaymentMethod[].class);
		//Arrays.asList(paymentmethods);
		model.addAttribute("paymentmethods", paymentmethods);
		
		String URL_COA= "http://localhost:8084/MpangoFarmEngineApplication/api/financials/coa/user/" 	+ UserId;
		ChartOfAccounts[] coa = restTemplate.getForObject(URL_COA, ChartOfAccounts[].class);
		model.addAttribute("chartofaccounts", coa);

		String URL_PROJECTS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/projects/user/" + UserId;
		Project[] projects = restTemplate.getForObject(URL_PROJECTS, Project[].class);
		//Arrays.asList(projects);
		model.addAttribute("projects", projects);

		return "editIncome";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddIncomePage(Model model) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		IncomeForm incomeForm = new IncomeForm();
		model.addAttribute("incomeForm", incomeForm);

		String URL_CUSTOMERS = "http://localhost:8084/MpangoFarmEngineApplication/api/customers/";
		Customer[] customers = restTemplate.getForObject(URL_CUSTOMERS, Customer[].class);
		Arrays.asList(customers);
		model.addAttribute("customers", customers);

		String URL_PAYMENTMETHODS = "http://localhost:8084/MpangoFarmEngineApplication/api/paymentmethods/";
		PaymentMethod[] paymentmethods = restTemplate.getForObject(URL_PAYMENTMETHODS, PaymentMethod[].class);
		Arrays.asList(paymentmethods);
		model.addAttribute("paymentmethods", paymentmethods);

		String URL_ACCOUNTS = "http://localhost:8084/MpangoFarmEngineApplication/api/chartofaccounts/";
		ChartOfAccounts[] chartofaccounts = restTemplate.getForObject(URL_ACCOUNTS, ChartOfAccounts[].class);
		Arrays.asList(chartofaccounts);
		model.addAttribute("chartofaccounts", chartofaccounts);

		String URL_PROJECTS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/projects/user/" + UserId;
		Project[] projects = restTemplate.getForObject(URL_PROJECTS, Project[].class);
		Arrays.asList(projects);
		model.addAttribute("projects", projects);
		
		return "addIncome";
	}

}
