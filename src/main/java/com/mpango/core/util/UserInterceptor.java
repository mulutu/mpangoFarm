package com.mpango.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.SmartView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mpango.core.model.MyUser;

@SessionAttributes("userDetails")
public class UserInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(UserInterceptor.class);

	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		if (isUserLogged()) {
			addToModelUserDetails(request.getSession(false));
		}
		return true;
	}

	/**
	 * Executed before after handler is executed. If view is a redirect view, we
	 * don't need to execute postHandle
	 **/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		if (model != null && !isRedirectView(model)) {
			if (isUserLogged()) {
				addToModelUserDetails(model);
			}
		}
	}

	/**
	 * Used before model is generated, based on session
	 */
	private void addToModelUserDetails(HttpSession sessionx) {
		log.info("================= addToModelUserDetails ============================");
		//String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false); // true == allow create	
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String loggedUsername =  securityContext.getAuthentication().getName();
		
		
		session.setAttribute("username", loggedUsername);
		log.info("user(" + loggedUsername + ") session : " + session);
		log.info("================= addToModelUserDetails ============================");

	}

	/**
	 * Used when model is available
	 */
	private void addToModelUserDetails(ModelAndView model) {
		log.info("================= addToModelUserDetails ============================");
		//String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false); // true == allow create	
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String loggedUsername =  securityContext.getAuthentication().getName();
		
		model.addObject("loggedUsername", loggedUsername);
		log.trace("session : " + model.getModel());
		log.info("================= addToModelUserDetails ============================");

	}

	public static boolean isRedirectView(ModelAndView mv) {

		String viewName = mv.getViewName();
		if (viewName.startsWith("redirect:/")) {
			return true;
		}

		View view = mv.getView();
		return (view != null && view instanceof SmartView && ((SmartView) view).isRedirectView());
	}

	public static boolean isUserLogged() {
		
		Authentication authentication = null;
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(false); // true == allow create	
		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		try {
			authentication = securityContext.getAuthentication();
			String name =  authentication.getName();
			log.info("================= isUserLogged ========== YESSSSS ---- {} " , name);
			return true;
		} catch (NullPointerException e) {
			log.info("================= isUserLogged ========== NOOOOOO ");
			return false;
		}
	}
	
	public static boolean isAuthenticated() {
		boolean res = false;
		
		Authentication authentication = null;
		
		try{
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session= attr.getRequest().getSession(false); // true == allow create	
			SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
			authentication = securityContext.getAuthentication();
			res = authentication.isAuthenticated();
		}catch(NullPointerException e) {
			log.error("UserInterceptor->isAuthenticated() : >>>>  NO authentication " );
		}		
		return res;
	}
	
	public static int getLoggedUserID() {
		int userId = 0;
		
		Authentication authentication = null;
	    
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(false); // true == allow create		
		SecurityContext securityContext = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		try{
			authentication = securityContext.getAuthentication();
			MyUser userDetails = (MyUser) authentication.getPrincipal();
			userId = userDetails.getId();
		}catch(NullPointerException e) {
			log.error("UserInterceptor->isAuthenticated() : >>>>  NO authentication " );
		}		
		return userId;
	}
}