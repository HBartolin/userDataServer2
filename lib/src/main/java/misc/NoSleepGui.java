package misc;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class NoSleepGui extends JFrame {
	private static final long serialVersionUID = 5154067950651146263L;
//	private final static Image icon32 = Toolkit.getDefaultToolkit().getImage("./delete48.png");
	private static Image icon32;
	//	this.getClass().getResourceAsStream();
//	private final static Image icon32 = ImageIO.read(getClass().getResource("delete48.png"));
//	private static Image icon32 = new Image(NoSleepGui.class.getClassLoader().getResourceAsStream("./delete48.png"));
	private boolean minimizeToTray=true;

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		for(LookAndFeelInfo feelInfo: UIManager.getInstalledLookAndFeels()) {
			if("Nimbus".equals(feelInfo.getName())) {
				UIManager.setLookAndFeel(feelInfo.getClassName());
				break;
			}
		}
		
		UIManager.getLookAndFeelDefaults().put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
//		icon32 = ImageIO.read(new File("delete48.png"));
		
		icon32 = ImageIO.read(NoSleepGui.class.getResource("/" + "delete48.png"));
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					new NoSleepGui();
				} catch (AWTException | InterruptedException e) {
					e.printStackTrace(); 
				}
            }
        });
	}
	
	public NoSleepGui() throws AWTException, InterruptedException {
		setTitle("NoSleepGui");
		setIconImage(icon32);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				log.trace("Clipcomrade exit from window close");

				exitApp();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				if(minimizeToTray) {
					setMainFrameHidden();
				}
			}
		});
		
		setLayout(new BorderLayout());
		
		JTextArea ta = new JTextArea();
		ta.setEditable(false);
//		DefaultCaret caret = (DefaultCaret) ta.getCaret();
//		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scroll = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		add(scroll, BorderLayout.CENTER);
		setSize(400, 500);
		createSystemTray();
		
//		pack();

//		setVisible(true);
		
		MyThread myThread = new MyThread(ta);
		myThread.start();
	}
	
	private final void exitApp() {
		System.exit(0);
	}
	
	private synchronized void setMainFrameHidden() {
		setVisible(false);
	}
	
	private void createSystemTray() {
		if(SystemTray.isSupported()==false) {
		//	log.warn("SystemTray is not supported");

			return;
		}

		SystemTray tray = SystemTray.getSystemTray();
	    PopupMenu popup = new PopupMenu();

	    TrayIcon trayIcon = new TrayIcon(icon32, "NoSleepGui", popup);
	    trayIcon.setImageAutoSize(true);
	    trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1) {
					if(isVisible()) {
//		        		log.trace("Hide from tray icon left click");

		            	setMainFrameHidden();
		        	} else {
//		        		log.trace("Show from tray icon left click");

		            	setMainFrameVisible();
		        	}
				}
			}
		});
	    
	    try {
			tray.add(trayIcon);
		} catch(AWTException e) {
//			log.error("Unable to add icon on tray", e);
//			DialogUtil.showInfoAboutError();
		}

		MenuItem menuHide = new MenuItem("Hide");
		popup.add(menuHide);
		ActionListener menuHideListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(isVisible()) {
//	        		log.trace("Hide from tray icon");

	            	setMainFrameHidden();
	        	}
	        }
	    };
	    menuHide.addActionListener(menuHideListener);

	    MenuItem menuShow = new MenuItem("Show");
	    popup.add(menuShow);
	    ActionListener menuShowListener = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(isVisible()==false) {
//	        		log.trace("Show from tray icon");

	            	setMainFrameVisible();
	        	}

	        	toFront();
	        }
	    };
	    menuShow.addActionListener(menuShowListener);

	    popup.addSeparator();

		MenuItem menuExit = new MenuItem("Exit");
		popup.add(menuExit);
		ActionListener menuExitListener = new ActionListener() {
			@Override
	        public void actionPerformed(ActionEvent e) {
//	            log.info("Exit from tray icon");

	            exitApp();
	        }
	    };
	    
	    menuExit.addActionListener(menuExitListener);
	}
	
	public synchronized void setMainFrameVisible() {
		setVisible(true);
		setExtendedState(Frame.NORMAL);
	}
	
}
