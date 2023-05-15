package com.phuclq.student.config;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
@EnableSwagger2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// configure AuthenticationManager so that it knows from where to load
// user for matching credentials
// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOrigins(ImmutableList.of("http://localhost:10002")); // www - obligatory
        configuration.setAllowedOrigins(ImmutableList.of("*"));  //set access from all domains
        configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(
				Collections.singletonList(new ParameterBuilder().name("Authorization").modelRef(new ModelRef("string"))
						.parameterType("header").required(true).hidden(true).defaultValue("Bearer ").build()));
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/i18n/**")
				.antMatchers("/content/**").antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
						"/configuration/security", "/swagger-ui.html", "/webjars/**")
				.antMatchers("/test/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers("/api/authenticate").permitAll()
				.antMatchers(HttpMethod.POST, "/api/register").permitAll()
				.antMatchers(HttpMethod.GET, "/api/province").permitAll()///
				.antMatchers(HttpMethod.GET, "api/publish").permitAll()
				.antMatchers(HttpMethod.GET, "/api/activate-account").permitAll()
				.antMatchers(HttpMethod.GET, "/api/file/category/home").permitAll()
				.antMatchers(HttpMethod.GET, "/api/category/file").permitAll()
				.antMatchers(HttpMethod.GET, "/api/category/file/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/file/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/school").permitAll()
				.antMatchers(HttpMethod.GET, "/api/industry").permitAll()
				.antMatchers(HttpMethod.POST, "/api/file/page-home").permitAll()
				.antMatchers(HttpMethod.POST, "/api/file/category/search").permitAll()
				.antMatchers(HttpMethod.GET, "/api/forgot-pass").permitAll()
				.antMatchers(HttpMethod.GET, "/api/momo").permitAll()
				.antMatchers(HttpMethod.GET, "/api/momo_ipn").permitAll()
				.antMatchers(HttpMethod.GET, "/api/notify").permitAll()
				.antMatchers(HttpMethod.GET, "/api/momo").permitAll()
				.antMatchers(HttpMethod.GET, "/api/momo/payment").permitAll()
				.antMatchers(HttpMethod.POST, "/api/captcha/generate").permitAll()
				.antMatchers(HttpMethod.POST, "/api/captcha/valid").permitAll()
				.antMatchers(HttpMethod.POST, "/api/file/top8").permitAll()
				.antMatchers(HttpMethod.GET, "/api/category/home").permitAll()
				.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/configuration/**",
						"/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll().anyRequest().authenticated()
				.and().rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000)
				.and().logout().deleteCookies("JSESSIONID");

		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
