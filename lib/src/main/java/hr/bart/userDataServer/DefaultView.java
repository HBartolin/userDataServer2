package hr.bart.userDataServer;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/v1" ).setViewName( "forward:/v1/index.html" );
        registry.addViewController( "/v1/" ).setViewName( "forward:/v1/index.html" );
//        registry.addViewController( "/svelteKit" ).setViewName( "forward:/svelteKit/index.html" );
        registry.addViewController( "/svelteKit/" ).setViewName( "forward:/svelteKit/index.html" );
        registry.addViewController( "/svelteKit/about2/" ).setViewName( "forward:/svelteKit/about2/index.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    }
}