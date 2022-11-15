package hr.bart.userDataServer;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import co.elastic.apm.attach.ElasticApmAttacher;

@SpringBootApplication
public class UserDataServerApplication {

	public static void main(String[] args) {
//		ElasticApmAttacher.attach();
		ApplicationContext ctx =SpringApplication.run(UserDataServerApplication.class, args);
		String[] beans = ctx.getBeanDefinitionNames();
		
//		for(String s: beans) {
//			System.out.println(s);
//		}		
	}

}
