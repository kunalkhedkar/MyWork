- Spring Security

1. Add Dependencies
	
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-web</artifactId>
			<version>4.0.1.RELEASE</version>
  		</dependency>

       <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
            <version>4.0.1.RELEASE</version>
        </dependency>	


2. Add a class for configuration 
	- with anotation  @Configuration  @EnableWebSecurity
	- use autowired method which gives AuthenticationManagerBuilder object to setup user credentials
	- Override configure method and configure which pages should hav direct access or required authentication.

	Eg.

			@Configuration
			@EnableWebSecurity
			public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
				
				@Autowired
				public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
					
					auth.inMemoryAuthentication().withUser("kunal").password("123").roles("USER","ADMIN");
				}
				@Override
				protected void configure(HttpSecurity http) throws Exception {
				
					http.authorizeRequests().antMatchers("/login").permitAll()
					.antMatchers("/","/*todo*/**").access("hasRole('USER')").and()
					.formLogin();
					
				}
			}


3. Add Filters to web.xml
	
		<filter>
	    	<filter-name>springSecurityFilterChain</filter-name>
	    	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	   </filter>
	 
	   <filter-mapping>
	   		<filter-name>springSecurityFilterChain</filter-name>
	    	'<url-pattern>/*</url-pattern>'
	   </filter-mapping> 




******************	 Login-Logout

	- Login
	  	Login will done automatically by spring security inside class that extends WebSecurityConfigurerAdapter we provide
	  user credentials and page access

	- Logout 
		for Logout we need to get current logged-in user authentication and pass it SecurityContextLogoutHandler().logout()
			along with request and response.


			@RequestMapping(value = "/logout", method = RequestMethod.GET)
			public String logout(HttpServletRequest request,
					HttpServletResponse response) {
				Authentication auth = SecurityContextHolder.getContext()
						.getAuthentication();
				if (auth != null) {
					new SecurityContextLogoutHandler().logout(request, response, auth);
				}
				return "redirect:/";
			}




