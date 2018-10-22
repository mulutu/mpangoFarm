package com.mpango.core.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.mpango.core.model.COAAccountType;
import com.mpango.core.model.ChartOfAccounts;
import com.mpango.core.model.Customer;
import com.mpango.core.model.Expense;
import com.mpango.core.model.Farm;
import com.mpango.core.model.Income;
import com.mpango.core.model.Project;
import com.mpango.core.model.ReportObject;
import com.mpango.core.model.forms.COAForm;
import com.mpango.core.model.forms.CustomerForm;
import com.mpango.core.model.forms.ExpenseForm;
import com.mpango.core.util.UserInterceptor;

@Controller
@RequestMapping(value = { "/financials" })
public class FinancialController {

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = { "/coa/add" }, method = RequestMethod.POST)
	public String addCOA(Model model, @ModelAttribute("coaForm") COAForm coaForm) {

		log.debug("FinancialController->addCOA() : >>>> model {}", model);

		if (!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0) { // if(!isAuthenticated())																															// {
			log.error("FinancialController->addCOA() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		String accountName = coaForm.getAccountName();
		String accountCode = coaForm.getAccountCode();
		int accountTypeId = coaForm.getAccountTypeId();
		String accountDescription = coaForm.getDescription();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date accountCreationDate = new Date();
		
		if (accountName != null) {
			ChartOfAccounts coa = new ChartOfAccounts();
			coa.setAccountName(accountName);
			coa.setAccountCode(accountCode);
			coa.setAccountTypeId(accountTypeId);
			coa.setUserId(UserId);
			coa.setDescription(accountDescription);
			
			String URL_ADD_ACCOUNT = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/coa/add";
			
			String postResponse = restTemplate.postForObject(URL_ADD_ACCOUNT, coa, String.class);
			return "redirect:/financials/chartofaccounts";
		}

		return "chartofaccounts";
	}
	
	
	@RequestMapping(value = { "/chartofaccounts" }, method = RequestMethod.GET)
	public String getCOA(Model model ) {

		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("FinancialController->getCOA() : >>>> NO authentication {}");
			return "redirect:/user/login?error";
		}
		
		COAForm coaForm = new COAForm();
		model.addAttribute("coaForm", coaForm);
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		log.debug("FinancialController->getCOA() ");
		
		String URL_COA_TYPES= "http://localhost:8084/MpangoFarmEngineApplication/api/financials/coa/types";
		COAAccountType[] coa_types = restTemplate.getForObject(URL_COA_TYPES, COAAccountType[].class);
		
		String URL_COA= "http://localhost:8084/MpangoFarmEngineApplication/api/financials/coa/user/" 	+ UserId;
		ChartOfAccounts[] coa = restTemplate.getForObject(URL_COA, ChartOfAccounts[].class);
		
		// https://stackoverflow.com/questions/23144358/how-to-loop-through-map-in-thymeleaf		
		model.addAttribute("coa_types", coa_types);		
		model.addAttribute("coa", coa);			
		
		return "chartofaccounts";
	}

	
	@RequestMapping(value = { "/farms" }, method = RequestMethod.GET)
	public String getFarmsPerUser(Model model ) {

		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		log.debug("FinancialController->getProjects() ");
		
		String URL_FARMS= "http://localhost:8084/MpangoFarmEngineApplication/api/financials/farms/user/" 	+ UserId;
		Farm[] farms = restTemplate.getForObject(URL_FARMS, Farm[].class);
		
		model.addAttribute("farms", farms);		
		
		return "farms";
	}
	
	
	@RequestMapping(value = { "/farms/details/{farmid}" }, method = RequestMethod.GET)
	public String getFarmDetails(Model model, @PathVariable("farmid") int farmid) {

		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		
		log.debug("FinancialController->getFarmDetails() ");
		
		String URL_FARM_DETAILS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/farmdetails/" + farmid;
		Farm farmDetails =  restTemplate.getForObject(URL_FARM_DETAILS, Farm.class);

		model.addAttribute("farmDetails", farmDetails);
		
		return "farmdetails";
		
	}
	
	

	@RequestMapping(value = { "/projects/details/{projid}" }, method = RequestMethod.GET)
	public String getProjects(Model model, @PathVariable("projid") int projid) {

		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		log.debug("FinancialController->getProjects() ");

		String URL_PROJECT_EXPENSES = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/project/expenses/" 	+ projid;
		Expense[] projExpenses = restTemplate.getForObject(URL_PROJECT_EXPENSES, Expense[].class);

		BigDecimal totalExpenses = new BigDecimal(0);
		for (Expense expense : projExpenses) {
			totalExpenses = totalExpenses.add(expense.getAmount());
		}
		model.addAttribute("totalExpenses", totalExpenses);

		String URL_PROJECT_INCOMES = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/project/incomes/"
				+ projid;
		BigDecimal totalIncome = new BigDecimal(0);
		Income[] projIncomes = null;
		try {
			projIncomes = restTemplate.getForObject(URL_PROJECT_INCOMES, Income[].class);
			for (Income income : projIncomes) {
				totalIncome = totalIncome.add(income.getAmount());
			}
		} catch (NullPointerException e) {
			log.error("FinancialController->getProjects() {} ", e);
		}
		model.addAttribute("totalIncome", totalIncome);

		BigDecimal grossprofit = new BigDecimal(0);
		grossprofit = totalIncome.subtract(totalExpenses);
		model.addAttribute("grossprofit", grossprofit);

		String URL_PROJECT = "http://localhost:8084/MpangoFarmEngineApplication/api/project/" + projid;
		Project project = restTemplate.getForObject(URL_PROJECT, Project.class);

		String URL_PROJECT_DETAILS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/projectdetails/" + projid;
		List<Map<String, Object>> projDetails = (List<Map<String, Object>>) restTemplate.getForObject(URL_PROJECT_DETAILS, Object.class);

		log.debug("FinancialController->getProjects()  List<Map<String, Object>> ::: {} ", projDetails);

		int totalExpectedOutput = 0;
		int totalActualOutput = 0;

		for (Map<String, Object> row : projDetails) {
			model.addAttribute("projDetails", row);
			totalExpectedOutput = (int) row.get("expected_output");
			totalActualOutput = (int) row.get("actual_output");
		}

		log.debug("FinancialController->getProjects()  totalExpenses ::: {} ", totalExpenses);
		log.debug("FinancialController->getProjects()  totalExpectedOutput ::: {} ", totalExpectedOutput);
		log.debug("FinancialController->getProjects()  totalActualOutput ::: {} ", totalActualOutput);

		BigDecimal expectedCostOfProduction = totalExpenses.divide(new BigDecimal(totalExpectedOutput), 2,
				RoundingMode.HALF_UP);
		BigDecimal actualCostOfProduction = totalExpenses.divide(new BigDecimal(totalActualOutput), 2,
				RoundingMode.HALF_UP);

		model.addAttribute("projExpenses", projExpenses);
		model.addAttribute("projIncomes", projIncomes);
		model.addAttribute("project", project);
		model.addAttribute("totalExpectedOutput", totalExpectedOutput);
		model.addAttribute("expectedCostOfProduction", expectedCostOfProduction);

		model.addAttribute("totalActualOutput", totalActualOutput);
		model.addAttribute("actualCostOfProduction", actualCostOfProduction);

		return "projectdetails";
	}
	
	
	

	@RequestMapping(value = { "/projects" }, method = RequestMethod.GET)
	public String getProjects(Model model ) {
		
		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		log.debug("FinancialController->getProjects() ");

		String URL_PROJECTS = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/projects/user/" + UserId;
		Project[] projects = restTemplate.getForObject(URL_PROJECTS, Project[].class);

		model.addAttribute("projects", projects);

		return "projects";
	}

	@RequestMapping(value = { "/incomeandexpense" }, method = RequestMethod.GET)
	public String incomeExpense(Model model ) {

		if(!UserInterceptor.isAuthenticated() || UserInterceptor.getLoggedUserID() == 0 ) {	//if(!isAuthenticated()) {
			log.error("UserController->dashboard() : >>>> NOOO authentication {}");
			return "redirect:/user/login?error";
		}
		
		int UserId = UserInterceptor.getLoggedUserID(); // get from login session
		
		log.debug("FinancialController->incomeExpense() ");


		String URL_EXPENSES = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/expense/user/" + UserId;
		ReportObject[] expenses = restTemplate.getForObject(URL_EXPENSES, ReportObject[].class);

		BigDecimal totalExpense = new BigDecimal(0);
		for (ReportObject row : expenses) {
			totalExpense = totalExpense.add(row.getTotalAmount());
		}

		String URL_INCOMES = "http://localhost:8084/MpangoFarmEngineApplication/api/financials/income/user/" + UserId;
		ReportObject[] incomes = restTemplate.getForObject(URL_INCOMES, ReportObject[].class);

		BigDecimal totalIncome = new BigDecimal(0);
		for (ReportObject row : incomes) {
			totalIncome = totalIncome.add(row.getTotalAmount());
		}

		BigDecimal netIncome = totalIncome.subtract(totalExpense);

		model.addAttribute("incomes", incomes);
		model.addAttribute("totalIncome", totalIncome);
		model.addAttribute("totalExpense", totalExpense);
		model.addAttribute("netIncome", netIncome);
		model.addAttribute("expenses", expenses);

		return "incomeandexpenses";
	}

}
