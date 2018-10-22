package com.mpango.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@SessionAttributes("userDetails")
public class SessionTimerInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(SessionTimerInterceptor.class);

	private static final long MAX_INACTIVE_SESSION_TIME = 5 * 100;

    @Autowired
    private HttpSession session;

    /**
     * Executed before actual handler is executed
     **/
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        log.info("Pre handle method - check handling start time");
        long startTime = System.currentTimeMillis();
        request.setAttribute("executionTime", startTime);
        if (UserInterceptor.isAuthenticated()) {
            session = request.getSession(false);
            log.info("SessionTimerInterceptor: >>>>> request.getSession().getLastAccessedTime() {} ", request.getSession(false).getLastAccessedTime());
       
            log.info("Time since last request in this session: {} ms", System.currentTimeMillis() - request.getSession(false).getLastAccessedTime());
            if (System.currentTimeMillis() - session.getLastAccessedTime() > MAX_INACTIVE_SESSION_TIME) {
                log.warn("Logging out, due to inactive session");
                SecurityContextHolder.clearContext();
                request.logout();
                session.invalidate();
                response.sendRedirect("/spring-security-rest-full/logout");
            }
        }
        return true;
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView model) throws Exception {
        log.info("Post handle method - check execution time of handling");
        long startTime = (Long) request.getAttribute("executionTime");
        log.info("Execution time for handling the request was: {} ms", System.currentTimeMillis() - startTime);
    }
}