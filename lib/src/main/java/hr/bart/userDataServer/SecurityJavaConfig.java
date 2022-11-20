package hr.bart.userDataServer;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig /*extends WebSecurityConfigurerAdapter*/  {
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	/** 
	 * https://www.devglan.com/online-tools/bcrypt-hash-generator
	 * password = q
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("q").password("$2a$04$FGxuScCSe.iBtNEHcr4TaONTdrXVUK0gdpe7yafmjc.wy14mxYkga").roles("USER");
	}
	
	@Bean
	public CorsWebFilter corsWebFilter() {
	    CorsConfiguration corsConfig = new CorsConfiguration();
	    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	    corsConfig.setMaxAge(3600L);
	    corsConfig.addAllowedMethod("*");
	    corsConfig.addAllowedHeader("Requestor-Type");
	    corsConfig.addExposedHeader("X-Get-Header");

	    UrlBasedCorsConfigurationSource source =
	        new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig);

	    return new CorsWebFilter((org.springframework.web.cors.reactive.CorsConfigurationSource) source);
	}
	
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
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	} */
	
	/*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http
            .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
            .httpBasic();
        
        return http.build();
    } */
	 
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable();
	/*http.cors().and().authorizeRequests()
				.anyRequest().authenticated()
				.and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);/
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
	
	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
}