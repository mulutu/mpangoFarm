package com.mpango.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mpango.core.model.MyUser;
import com.mpango.core.model.forms.RegisterForm;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = { "/" })
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	/*
	 * The landing page + Register form
	 */
	@RequestMapping(value = { "/errorxxx" }, method = RequestMethod.GET)
	public String error(Model model) {
		return "/errorccc";
	}

	/*
	 * The landing page + Register form
	 */
	@RequestMapping(value = { "/", "", "/index" }, method = RequestMethod.GET)
	public ModelAndView welcomeIndex(HttpSession session, HttpServletRequest request) {
		log.debug("IndexController -> welcomeIndex() >>>>> {}" + session.getId());

		Integer pageViews = 1;
		if (request.getSession().getAttribute("pageViews") != null) {
			pageViews += (Integer) request.getSession().getAttribute("pageViews");
		}
		request.getSession().setAttribute("pageViews", pageViews);
		//model.addAttribute("pageViews", pageViews);

		return new ModelAndView("index", "registerForm", new RegisterForm());
	}

}
