package misc;
import java.awt.AWTException;
import java.awt.Robot;
import java.time.LocalTime;
import java.util.Random;

public class NoSleep  {

	public static void main(String[] args) throws InterruptedException, AWTException {
		Robot hal = new Robot();
        Random random = new Random();
		
		 while(true) {	
//	        	long randomNum = ThreadLocalRandom.current().nextLong(1000 * 60 * 8, 1000 * 60 * 14);
	        	System.out.println(LocalTime.now() + " Spavam " /*+ String.format("%.2f", randomNum /1000d /60d) + " min)"*/);
	        	Thread.sleep(1000 * 60 * 10);
	            
	        	int x = random.nextInt(1000); // % 640;
	            int y = random.nextInt(1000); // % 480;
	            
	            hal.mouseMove(x, y);
	        }
	}
}
