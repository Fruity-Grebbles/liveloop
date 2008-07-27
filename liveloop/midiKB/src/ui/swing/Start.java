package ui.swing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.marco.utils.ObjectPropertySerializer;
import com.marco.utils.swing.SwingUtils;

import sun.security.action.GetLongAction;
import ui.Config;
import ui.Msgs;

public class Start {

	private JFrame sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Config config;
	private JPanel canvasImage = null;
	private JLabel linkAbout = null;
	/**
	 * This method initializes canvasImage	
	 *
	 */
	private void createCanvasImage() {
		
		try {
			final Image image = ImageIO.read(Msgs.class.getResourceAsStream("maudio.jpg"));
			canvasImage = new JPanel(){
				public void paint(Graphics g) {
					g.drawImage(image,0,0,this);
				}			
			};
			Msgs.setToolTip(canvasImage,Msgs.intro);
			this.sShell.add(canvasImage);
			
			canvasImage.setBounds(new Rectangle(60, 0, 410, 168));
		} catch (IOException e) {
			throw new Error(e);
		}	
	}
	
	protected void customLAF(){
		com.marco.utils.Utils.initTime();
		UIDefaults d=UIManager.getLookAndFeel().getDefaults();
		com.marco.utils.Utils.printTime();
		Color black=new Color(23,23,23);
		Object obj=d.get("Panel.background");
		//UIManager.put("Panel.background",new Color(27,27,27));
		UIManager.put("ToolTip.border",new LineBorder(new Color(163,184,204)));
		UIManager.put("TitledBorder.font",new FontUIResource(new Font("FixedSys",Font.BOLD,15)));
		UIManager.put("TitledBorder.titleColor",new ColorUIResource(new Color(163,184,204)));
		//UIManager.put("ComboBox.selectedBackground",new ColorUIResource(new Color(0,201,67)));
		UIManager.put("TextField.caretForeground",new ColorUIResource(new Color(250,250,250)));
		//if(true) return;
		for(Map.Entry e : d.entrySet()){
			String s=""+e.getKey();
			if(s.endsWith(".background")){
				if(e.getValue() instanceof Color){
					UIManager.put(e.getKey(),black);
				}else if(e.getValue() instanceof ColorUIResource){
					UIManager.put(e.getKey(),new ColorUIResource(black));
				}				
				
			}else if(s.endsWith(".foreground")){
				if(e.getValue() instanceof Color){
					UIManager.put(e.getKey(),new Color(250,250,250));
				}else if(e.getValue() instanceof ColorUIResource){
					UIManager.put(e.getKey(),new ColorUIResource(new Color(250,250,250)));
				}				
				
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		//update the tooltip time 
		ToolTipManager.sharedInstance().setInitialDelay(2000);
		ToolTipManager.sharedInstance().setDismissDelay(100000);
		
		Start s=new Start();
		s.customLAF();
		
		
		s.createSShell();
		s.sShell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		s.sShell.setLocationRelativeTo(null);
		
		s.sShell.setVisible(true);
		//SwingUtilities.updateComponentTreeUI(s.sShell);
		
		// TODO Auto-generated method stub
		/* Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments)
		 * for the correct SWT library path in order to run with the SWT dlls. 
		 * The dlls are located in the SWT plugin jar.  
		 * For example, on Windows the Eclipse SWT 3.1 plugin jar is:
		 *       installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		/*
		Display display = Display.getDefault();
		Start thisClass = new Start();
		thisClass.createSShell();
		thisClass.sShell.open();

		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		thisClass.config.stop();*/
	}
	
	protected Config getConfig(){
		String file=Msgs.getConfigFile();
		Config c=Config.load(file);
		if(c==null)
			c=new Config();
		return c;		
	}

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell=new JFrame();
		sShell.setResizable(false);
		
		sShell.setLayout(null);
		sShell.setTitle("Live Loop -- http://code.google.com/p/liveloop/");
		sShell.setSize(new Dimension(531, 572));
		sShell.setLayout(null);
		try {
			sShell.setIconImage(ImageIO.read(Msgs.class.getResourceAsStream("icon.gif")));
		} catch (IOException e2) {
			throw new Error(e2);
		}
		config=getConfig();
		final LoopConfigPanel pan = new LoopConfigPanel();
		sShell.add(pan);
		//SWTUtils.setBackground(sShell,new Color(27,27,27)));
		//SWTUtils.setForeground(sShell,new Color(Display.getCurrent(),new RGB(255,255,255)));
		sShell.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				config.stop();
				pan.disposeInput();
				config.save(Msgs.getConfigFile());
			}			
			
		});
		
				
	
		pan.setBounds(new Rectangle(0, 165, 526, 376));
		pan.setConfig(config);
		createCanvasImage();
		linkAbout = new JLabel();
		sShell.getContentPane().add(linkAbout);
		linkAbout.setBounds(new Rectangle(478, 145, 33, 17));
		linkAbout.setText("<html><a style='cursor:pointer'><u>Infos</u></a>");
		linkAbout.addMouseListener(new MouseAdapter() {
			

			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(URI.create(Msgs.webSite));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Msgs.setToolTip(linkAbout, Msgs.info);
		
		
	}

}
