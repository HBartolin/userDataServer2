package hr.bart.userDataServer;

import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig /* extends WebSecurityConfigurerAdapter */ {
	private final Logger LOGGER=LoggerFactory.getLogger(getClass());
//	@Autowired
//	private AuthenticationEntryPoint authEntryPoint;
	
	/** 
	 * https://www.devglan.com/online-tools/bcrypt-hash-generator
	 * password = q
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("q").password("$2a$04$FGxuScCSe.iBtNEHcr4TaONTdrXVUK0gdpe7yafmjc.wy14mxYkga").roles("USER");
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//LOGGER.info("=======================================================================");
		//http.csrf();
		//http.cors().and().csrf().disable();
	    
	    http
        .csrf(csrf -> csrf.disable());
		 
		return http.build();
	} 
	
	/*@Bean
	public WebMvcConfigurer corsConfigurer()
	{
	   ArrayList<String> allowDomainsList=new ArrayList<>();
	   allowDomainsList.add("http://localhost:8080");
	   allowDomainsList.add("http://localhost:5173");
	   allowDomainsList.add("http://localhost:4200");
	   allowDomainsList.add("http://localhost:4173");
	   allowDomainsList.add("http://localhost/");

	   String[] allowDomains = allowDomainsList.toArray(new String[allowDomainsList.size()]);
	   
	   LOGGER.info("CORS configuration: " + allowDomainsList);
	   return new WebMvcConfigurer() {
	      @Override
	      public void addCorsMappings(CorsRegistry registry) {
	         registry.addMapping("/**").allowedOrigins(allowDomains)
	         	.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE");
	      }
	   };
	} */
	
//	 @Override
//	    public void addViewControllers(ViewControllerRegistry registry) {
//	        registry.addRedirectViewController("/", "index.html");
//	    }
	
	/* protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("#######################################################################");
		 http.csrf(); //.disable();
	/*http.cors().and().authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic()
				.authenticationEntryPoint(authEntryPoint); */
	//} 
	
//	protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                //HTTP Basic authentication
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
//                .and()
//                .csrf().disable()
//                .formLogin().disable();
//    }

	
	
	/* @Bean
	public WebMvcConfigurer corsMappingConfigurer() {
	   return new WebMvcConfigurer() {
	       @Override
	       public void addCorsMappings(CorsRegistry registry) {
	           WebConfigProperties.Cors cors = webConfigProperties.getCors();
	           registry.addMapping("/**")
	             .allowedOrigins(cors.getAllowedOrigins())
	             .allowedMethods(cors.getAllowedMethods())
	             .maxAge(cors.getMaxAge())
	             .allowedHeaders(cors.getAllowedHeaders())
	             .exposedHeaders(cors.getExposedHeaders());
	       }
	   };
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.antMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	} */
	
	/* @Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	} 
	
	/*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
            .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
            .httpBasic();
        
        return http.build();
    } */
	
	/*@Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    } */
	
	/*@Bean
	  CorsConfigurationSource corsConfigurationSource() 
	  {
		CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    configuration.setAllowedMethods(Arrays.asList("*") /*Arrays.asList("GET", "POST", "PUT"));
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    
	    return source;
	  } 
	
	@Bean
    public WebMvcConfigurer corsConfigurer() 
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    } */
	
}