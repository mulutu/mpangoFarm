package com.mpango.core.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionEventListener extends HttpSessionEventPublisher {
	
	public static final Logger logger = LoggerFactory.getLogger(SessionEventListener.class);

    public void sessionCreated(HttpSessionEvent event) {
    	logger.debug(" SessionEventListener -> sessionCreated  ");
        super.sessionCreated(event);
        event.getSession().setMaxInactiveInterval(60*3);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	
    	logger.debug(" SessionEventListener -> sessionDestroyed  ");
    	
        String name = null;
        SessionRegistry sessionRegistry = getSessionRegistry(event);
        SessionInformation sessionInfo = (sessionRegistry != null ? sessionRegistry
            .getSessionInformation(event.getSession().getId()) : null);
        UserDetails ud = null;
        if (sessionInfo != null) {
            ud = (UserDetails) sessionInfo.getPrincipal();}
        if (ud != null) {
            name = ud.getUsername();
            // YOUR METHOD IS CALLED HERE 
            getMyService(event).myMethod(name);
        }
        super.sessionDestroyed(event);
    }

    public YourBean4Service getMyService(HttpSessionEvent event) {
    	logger.debug(" SessionEventListener -> YourBean4Service getMyService  ");
        HttpSession session = event.getSession();
        ApplicationContext ctx = (ApplicationContext) WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        return (YourBean4Service) ((BeanFactory) ctx).getBean("yourBean4Service");
    }

    public SessionRegistry getSessionRegistry(HttpSessionEvent event) {
    	logger.debug(" SessionEventListener -> SessionRegistry getSessionRegistry  ");
        HttpSession session = event.getSession();
        ApplicationContext ctx =
            (ApplicationContext) WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        return (SessionRegistry) ((BeanFactory) ctx).getBean("sessionRegistry");
    }
}
