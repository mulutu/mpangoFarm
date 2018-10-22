package com.mpango.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.mpango.core.util.LoggerInterceptor;
import com.mpango.core.util.SessionTimerInterceptor;
import com.mpango.core.util.UserInterceptor;


@EnableWebMvc
@Configuration
public class MvcWebConfig implements WebMvcConfigurer{
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new LoggerInterceptor());
        //registry.addInterceptor(new UserInterceptor());
        //registry.addInterceptor(new SessionTimerInterceptor());
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * registry.addResourceHandler("/pdfs/**")
		 * .addResourceLocations("/WEB-INF/pdfs/"); <link href="css/cover.css"
		 * rel="stylesheet">
		 */
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		/* ----- index configs ---- */
		registry.addResourceHandler("/index/assets/**").addResourceLocations("classpath:/static/index/assets/");
		registry.addResourceHandler("/index/assets/css/**").addResourceLocations("classpath:/static/index/assets/css/");
		registry.addResourceHandler("/index/assets/fonts/**").addResourceLocations("classpath:/static/index/assets/fonts/");
		registry.addResourceHandler("/index/assets/images/**").addResourceLocations("classpath:/static/index/assets/images/");
		registry.addResourceHandler("/index/assets/js/**").addResourceLocations("classpath:/static/index/assets/js/");
		registry.addResourceHandler("/index/assets/scss/**").addResourceLocations("classpath:/static/index/assets/scss/");
		/* ----- app configs ---- */
		registry.addResourceHandler("/app/assets/**").addResourceLocations("classpath:/static/app/assets/");
		registry.addResourceHandler("/app/sass/**").addResourceLocations("classpath:/static/app/sass/");

	}

	@Bean
	@Description("Thymeleaf template resolver serving HTML 5")
	public ClassLoaderTemplateResolver templateResolver() {

		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		templateResolver.setPrefix("templates/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("UTF-8");

		return templateResolver;
	}

	@Bean
	@Description("Thymeleaf template engine with Spring integration")
	public SpringTemplateEngine templateEngine() {

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

	@Bean
	@Description("Thymeleaf view resolver")
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");

		return viewResolver;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	

	
}
