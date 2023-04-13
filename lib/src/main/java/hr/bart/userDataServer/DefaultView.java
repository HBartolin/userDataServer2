package hr.bart.userDataServer;

import java.io.File;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultView implements WebMvcConfigurer {
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
        			String odS="/" + name + "/" + f.getName();
        			String doS="forward:/" + name + "/" + f.getName() + _INDEX_HTML;
        			
            		registry.addViewController(odS).setViewName(doS);
                	System.out.println(odS + " -> " + doS);
                	
                	skenDeeper(registry, f, odS.substring(1));
            	}
        	}
        } 
	}
	
}