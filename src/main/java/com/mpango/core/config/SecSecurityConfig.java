package com.mpango.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mpango.core.auth.CustomAuthenticationProvider;
import com.mpango.core.util.MySimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableRedisHttpSession
@EnableWebSecurity // Very important!
@EnableGlobalMethodSecurity
//@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
@ComponentScan
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

	public SecSecurityConfig() {
		super();
	}
	
	

	// @Bean("authenticationManager")
	// @Override
	// public AuthenticationManager authenticationManagerBean() throws Exception {
	// return super.authenticationManagerBean();
	// }

	@Autowired
	private CustomAuthenticationProvider authProvider;
	

	/*@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory();
	}*/

	
	 @Bean 
	 public JedisConnectionFactory connectionFactory() { // It will create
		 //filter for Redis store which will override default Tomcat Session 
		 return new JedisConnectionFactory(); 
	 }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.eraseCredentials(false);
		auth.authenticationProvider(authProvider);
	}

	// @Bean("authenticationManager")
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws
	// Exception{
	// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	// }
	
	//@Bean
	//public HttpSessionEventPublisher httpSessionEventPublisher() {
	    //return new HttpSessionEventPublisher();
	    //return new SessionEventListener();
	//}

	//@Override // Very important!
	//protected void configure(HttpSecurity http) throws Exception {

		//http.csrf().disable();

		//http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);		
		//http.sessionManagement().maximumSessions(2);		
		//http.sessionManagement().invalidSessionUrl("/invalidSession.html");		
		//http.sessionManagement().sessionFixation().migrateSession();
		
		//http.sessionManagement().sessionFixation().newSession().maximumSessions(1)
	     //   .expiredUrl("/expired")
	      //  .sessionRegistry(sessionRegistry());
		
		//http.logout().deleteCookies("JSESSIONID");

		// The pages does not require login
		// http.authorizeRequests().antMatchers("/", "/login", "/logout*", "/confirm*", "/index").permitAll();

		// /userInfo page requires login as USER or ADMIN.
		// If no login, it will redirect to /login page.
		//http.authorizeRequests().antMatchers("/user/dashboard").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// For ADMIN only.
		// http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

		//http.authorizeRequests().anyRequest().authenticated();

		//http.httpBasic();

		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		// http.sessionManagement().maximumSessions(2);
		// http.sessionManagement().invalidSessionUrl("/invalidSession.html");
		// http.sessionManagement().sessionFixation().migrateSession();

		// When the user has logged in as XX.
		// But access a page that requires role YY,
		// AccessDeniedException will throw.
		//http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Config for Login Form
		//http.authorizeRequests().and().formLogin()//
				// Submit URL of login page.
				//.loginProcessingUrl("/user/login") // Submit URL
				// .loginProcessingUrl("/j_spring_security_check") // Submit URL
				//.successHandler(myAuthenticationSuccessHandler()).loginPage("/user/login")//
				// .defaultSuccessUrl("/dashboard")//
				//.failureUrl("/user/login?error=true")//
				// .usernameParameter("username")//
				// .passwordParameter("password")
				// Config for Logout Page
				//.and().logout().logoutUrl("/user/logout").logoutSuccessUrl("/user/logoutSuccessful");

		//super.configure(http); //Very important!

	//}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);		
		http.sessionManagement().maximumSessions(1).expiredUrl("/user/login?error");		
		http.sessionManagement().invalidSessionUrl("/invalidSession.html");		
		http.sessionManagement().sessionFixation().migrateSession();
		http.logout().deleteCookies("JSESSIONID");
		
		
		http.authorizeRequests().antMatchers("/", "/user/login", "/user/logout", "/user/confirm", "/index").permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
				
		http.formLogin()
				.loginPage("/user/login") 
				.defaultSuccessUrl("/user/dashboard")
	            .failureUrl("/user/login?error")
				.permitAll();   
		
		http.logout()
				.logoutUrl("/user/logout") 
        		.logoutSuccessUrl("/user/login?logout")
        		.invalidateHttpSession(true)
        		.deleteCookies("JSESSIONID")
        		.permitAll();
	}
	
	@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/resources/**").anyRequest();
	}

	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		return new MySimpleUrlAuthenticationSuccessHandler();
	}
}
