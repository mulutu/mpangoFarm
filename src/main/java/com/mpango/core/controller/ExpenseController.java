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
import com.mpango.core.model.MyUser;
import com.mpango.core.model.PaymentMethod;
import com.mpango.core.model.Project;
import com.mpango.core.model.Supplier;
import com.mpango.core.model.forms.ExpenseForm;
import com.mpango.core.model.forms.PayMethodForm;
import com.mpango.core.model.forms.SupplierForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/expenses" })
public class ExpenseController {

	private static final Logger log = LoggerFactory.getLogger(ExpenseController.class);

	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = { "/paymethod/add" }, method = RequestMethod.GET)
	public String showAddPayMethodPage(Model model) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		PayMethodForm payMethodForm = new PayMethodForm();
		model.addAttribute("payMethodForm", payMethodForm);

		return "addPayMethod";
	}
	
	/*
	 * Add Payment Method
	 */
	@RequestMapping(value = { "/paymethod/add" }, method = RequestMethod.POST)
	public String saveSupplier(Model model, @ModelAttribute("supplierForm") PayMethodForm payMethodForm, @SessionAttribute("userDetails") MyUser myuser) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		String URL_ADD_PAYMETHOD= "http://localhost:8084/MpangoFarmEngineApplication/api/paymethod/";
		
		String payMethodName = payMethodForm.getPayMethodName();		
		String description = payMethodForm.getDescription();

		if (payMethodName != null ) {
			PaymentMethod newPayMethod= new PaymentMethod(payMethodName, description); 
			String postResponse = restTemplate.postForObject(URL_ADD_PAYMETHOD, newPayMethod, String.class);
			return "redirect:/expenses/list";
		}
		return "addPayMethod";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String expenseList(Model model) {
		
		log.debug("ExpenseController->expenseLists() >>> myuser ");
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int userID = UserInterceptor.getLoggedUserID();
		
		String URL_EXPENSES = "http://localhost:8084/MpangoFarmEngineApplication/api/expense/user/" + userID;
		Expense[] expenses = restTemplate.getForObject(URL_EXPENSES, Expense[].class);
		
		model.addAttribute("expenses", expenses);

		return "expenses";
	}

	/*
	 * Add Expense
	 */
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST)
	public String saveExpense(Model model, @ModelAttribute("expenseForm") ExpenseForm expenseForm, @SessionAttribute("userDetails") MyUser myuser) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		String URL_ADD_EXPENSE = "http://localhost:8084/MpangoFarmEngineApplication/api/expense/";
		int SupplierId = expenseForm.getSupplierId();
		BigDecimal Amount = expenseForm.getAmount();
		int PaymentMethodId = expenseForm.getPaymentMethodId();
		int AccountId = expenseForm.getAccountId();
		int ProjectId = expenseForm.getProjectId();
		String Notes = expenseForm.getNotes();

		int UserId = myuser.getId(); // get from login session

		if (Amount != null) {
			Expense newExpense = new Expense(Amount, SupplierId, PaymentMethodId, AccountId, ProjectId, Notes, UserId);
			String postResponse = restTemplate.postForObject(URL_ADD_EXPENSE, newExpense, String.class);
			return "redirect:/expenses/list";
		}
		return "addExpense";
	}

	/*
	 * Update/Edit an expense
	 */
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String updateExpense(Model model, @ModelAttribute("expenseForm") ExpenseForm expenseForm ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		int ExpenseId = expenseForm.getExpenseId();
		String ExpenseDateStr = expenseForm.getExpenseDate();
		// int UserId = expenseForm.getUserId();
		int SupplierId = expenseForm.getSupplierId();
		BigDecimal Amount = expenseForm.getAmount();
		int PaymentMethodId = expenseForm.getPaymentMethodId();
		int AccountId = expenseForm.getAccountId();
		int ProjectId = expenseForm.getProjectId();
		String Notes = expenseForm.getNotes();
		//int UserId = myuser.getId(); // get from login session

		DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date ExpenseDate = null;
		try {
			ExpenseDate = format.parse(ExpenseDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String URL_UPDATE_EXPENSE = "http://localhost:8084/MpangoFarmEngineApplication/api/expense/" + ExpenseId;

		if (Amount != null) {
			Expense newExpense = new Expense(Amount, ExpenseDate, SupplierId, PaymentMethodId, AccountId, ProjectId, Notes, UserId);
			restTemplate.put(URL_UPDATE_EXPENSE, newExpense, String.class);
			return "redirect:/expenses/list";
		}
		return "editExpense";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String editExpense(Model model, @PathVariable("id") int id, @SessionAttribute("userDetails") MyUser myuser) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		ExpenseForm expenseForm = new ExpenseForm();
		model.addAttribute("expenseForm", expenseForm);
		
		String URL_EDIT_EXPENSE = "http://localhost:8084/MpangoFarmEngineApplication/api/expense/" + id;
		Expense expense = restTemplate.getForObject(URL_EDIT_EXPENSE, Expense.class);
		model.addAttribute("expense", expense);

		String URL_SUPPLIERS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/suppliers/user/" + UserId;
		Supplier[] suppliers = restTemplate.getForObject(URL_SUPPLIERS, Supplier[].class);
		//Arrays.asList(suppliers);
		model.addAttribute("suppliers", suppliers);

		String URL_PAYMENTMETHODS = "http://localhost:8084/MpangoFarmEngineApplication/api/paymentmethods/";
		PaymentMethod[] paymentmethods = restTemplate.getForObject(URL_PAYMENTMETHODS, PaymentMethod[].class);
		//Arrays.asList(paymentmethods);
		model.addAttribute("paymentmethods", paymentmethods);

		String URL_ACCOUNTS = "http://localhost:8084/MpangoFarmEngineApplication/api/chartofaccounts/";
		ChartOfAccounts[] chartofaccounts = restTemplate.getForObject(URL_ACCOUNTS, ChartOfAccounts[].class);
		//Arrays.asList(chartofaccounts);
		model.addAttribute("chartofaccounts", chartofaccounts);

		String URL_PROJECTS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/projects/user/" + UserId;
		Project[] projects = restTemplate.getForObject(URL_PROJECTS, Project[].class);
		//Arrays.asList(projects);
		model.addAttribute("projects", projects);
		return "editExpense";
	}

	@RequestMapping(value = { "/add" }, method = RequestMethod.GET)
	public String showAddExpensePage(Model model, @SessionAttribute("userDetails") MyUser myuser) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		ExpenseForm expenseForm = new ExpenseForm();
		model.addAttribute("expenseForm", expenseForm);

		String URL_SUPPLIERS = "http://localhost:8084/MpangoFarmEngineApplication/api/suppliers/";
		Supplier[] suppliers = restTemplate.getForObject(URL_SUPPLIERS, Supplier[].class);
		Arrays.asList(suppliers);
		model.addAttribute("suppliers", suppliers);

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

		return "addExpense";
	}

}
