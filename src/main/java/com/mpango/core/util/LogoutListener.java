package com.mpango.core.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

	public static final Logger logger = LoggerFactory.getLogger(LogoutListener.class);

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		
		logger.debug(" LogoutListener >>>>>>>>>> 1111 ");
		List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
		UserDetails ud;
		for (SecurityContext securityContext : lstSecurityContext) {
			ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
			// ...
		}
	}

}