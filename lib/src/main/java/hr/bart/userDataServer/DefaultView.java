package hr.bart.userDataServer;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultView implements WebMvcConfigurer {
	private static final String INDEX_HTML = "index.html";
    private static final String _INDEX_HTML = "/" + INDEX_HTML;
    private final Logger LOGGER=LoggerFactory.getLogger(getClass());

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        File file;
        try {
            file = new ClassPathResource("static").getFile();
            String[] names = file.list();

            LOGGER.info("--------------------------------------------");
            for(String name : names) {
            	File isFolder=new File(file.getAbsolutePath(), name);
            	
                if (isFolder.isDirectory()) {
                	if(new File(isFolder, INDEX_HTML).exists()) {
                		String avc="/" + name;
                		String avcd=avc + "/";
                		String svn="forward:/" + name +_INDEX_HTML;
                		
                		registry.addViewController(avc).setViewName(svn);
                		LOGGER.info(avc + " -> " + svn);
                    	registry.addViewController(avcd).setViewName(svn);
                    	LOGGER.info(avcd + " -> " + svn);
                    	
                    	skenDeeper(registry, isFolder, name);
                	}
                }
            }
            LOGGER.info("--------------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void skenDeeper(ViewControllerRegistry registry, File isFolder, String name) throws IOException {
		/* List<Path> pathList=null;
		
		try(Stream<Path> stream = Files.walk(Paths.get(isFolder.toURI()))) {
			pathList=stream.filter(Files::isDirectory)
		    	.filter(x -> new File(x.toFile(), INDEX_HTML).exists())
		    	.collect(Collectors.toList());
		}
		
		String dir=isFolder.getParentFile().getAbsoluteFile().toString();
		
		if(pathList!=null) {
			for(int i=0; i<pathList.size(); i++) {
				Path p=pathList.get(i);
				String ss=p.toFile().getAbsolutePath().substring(dir.length());
				String odS=ss.replace("\\", "/");
				String doS="forward:" + odS + _INDEX_HTML;
				
				registry.addViewController(odS).setViewName(doS);
			}
		} */
		
        File[] list = isFolder.listFiles();
		
        if(list == null) return;
        
        for(File f: list) {
        	if(f.isDirectory()) {
        		if(new File(f, INDEX_HTML).exists()) {
        			String avc="/" + name + "/" + f.getName();
        			String avcd=avc + "/";
        			String svn="forward:/" + name + "/" + f.getName() + _INDEX_HTML;
        			
            		registry.addViewController(avc).setViewName(svn);
            		LOGGER.info(avc + " -> " + svn);
                	registry.addViewController(avcd).setViewName(svn);
                	LOGGER.info(avcd + " -> " + svn);
                	
                	skenDeeper(registry, f, avc.substring(1));
            	}
        	}
        } 
	}
	
}