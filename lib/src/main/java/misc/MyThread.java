package misc;

import java.awt.AWTException;
import java.awt.Robot;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JTextArea;

public class MyThread extends Thread {
	private JTextArea ta;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

	public MyThread(JTextArea ta) {
		this.ta = ta;
	}

	public void run() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		Random random = new Random();

		while (true) {
			LocalTime localTime = LocalTime.now();
			ta.append(localTime.format(dtf) + " Spavam\n");
			ta.setCaretPosition(ta.getDocument().getLength() - 1);

			try {
				Thread.sleep(1000 * 60 * 14);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			int x = random.nextInt(1000); // % 640;
			int y = random.nextInt(1000); // % 480;

			robot.mouseMove(x, y);
		}
	}
}