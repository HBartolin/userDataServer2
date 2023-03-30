package hr.bart.userDataServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DefaultView extends WebMvcConfigurerAdapter {
	private static final String INDEX_HTML = "index.html";
    private static final String _INDEX_HTML = "/" + INDEX_HTML;

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        File file;
        try {
            file = new ClassPathResource("static").getFile();
            String[] names = file.list();

            System.out.println("--------------------------------------------");
            for(String name : names) {
            	File isFolder=new File(file.getAbsolutePath() + "/" + name);
            	
                if (isFolder.isDirectory()) {
                	if(new File(isFolder, INDEX_HTML).exists()) {
                		registry.addViewController("/" + name).setViewName("forward:/" + name +_INDEX_HTML);
                    	System.out.println("/" + name + " -> " + "forward:/" + name +_INDEX_HTML);
                    	
                    	skenDeeper(registry, isFolder, name);
                	}
                }
            }
            System.out.println("--------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/v1" ).setViewName( "forward:/v1/index.html" );
        registry.addViewController( "/v1/" ).setViewName( "forward:/v1/index.html" );
//        registry.addViewController( "/svelteKit" ).setViewName( "forward:/svelteKit/index.html" );
        registry.addViewController( "/svelteKit/" ).setViewName( "forward:/svelteKit/index.html" );
//        registry.addViewController( "/svelteKit/about/" ).setViewName( "forward:/svelteKit/about/index.html" );
        registry.addViewController( "/svelteKit/about2/" ).setViewName( "forward:/svelteKit/about2/index.html" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    }*/

	private void skenDeeper(ViewControllerRegistry registry, File isFolder, String name) throws IOException {
//		try(Stream<Path> stream = Files.walk(Paths.get(isFolder.toURI()))) {
//		    stream.filter(Files::isDirectory)
//		          .forEach(skenEvenDeeper());
//		}
		
        File[] list = isFolder.listFiles();
		
        if (list == null) return;
        
        for ( File f : list ) {
        	if ( f.isDirectory() ) {
        		if(new File(f, INDEX_HTML).exists()) {
        			String odS="/" + name + "/" + f.getName();
        			String doS="forward:/" + name + "/" + f.getName() + _INDEX_HTML;
        			
            		registry.addViewController(odS).setViewName(doS);
                	System.out.println(odS + " -> " + doS);
                	
                	skenDeeper(registry, f, odS.substring(1));
            	}
        	}
        }
	}
	
	private Consumer<? super Path> skenEvenDeeper() {
		System.out.println();
		return null;
	}
}