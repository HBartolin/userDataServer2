package hr.bart.userDataServer;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
//@ComponentScan
public class UserDataServerApplication {

	public static void main(String[] args) {
//		ElasticApmAttacher.attach();
		ApplicationContext ctx =SpringApplication.run(UserDataServerApplication.class, args);
//		String[] beans = ctx.getBeanDefinitionNames();
		
//		for(String s: beans) {
//			LOGGER.info(s);
//		}		
	}

}
