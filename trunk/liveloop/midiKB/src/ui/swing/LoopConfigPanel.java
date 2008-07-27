package ui.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



import ui.Config;
import ui.Msgs;

import com.sun.corba.se.spi.orbutil.fsm.Input;

import engine.api.MidiIn;
import engine.boxes.input.ClassicInput;
import engine.utils.MidiConnect;
import engine.utils.MidiUtils;

public class LoopConfigPanel extends JPanel {

	
	private JGroup groupDevices = null;
	private JLabel labelInput = null;
	private JLabel labelOutput = null;
	private JComboBox comboInput = null;
	private JComboBox comboOutput = null;
	private JGroup groupControls = null;
	private JLabel labelNbTrack = null;
	private JTextField textNbTrack = null;
	private JGroup groupSequencer = null;
	private JLabel labelResolution = null;
	private JLabel labelRecord = null;
	private JTextField textRecord = null;
	private JLabel labelPause = null;
	private JTextField textPause = null;
	private JLabel labelClear = null;
	private JTextField textClear = null;
	private JComboBox comboTrackControls = null;
	private JTextField textTrackControl = null;
	private JTextField textPPQ = null;
	private JLabel labelSeqLength = null;
	private JTextField textSeqLength = null;
	private JButton buttonStart = null;
	
	private Config config;  //  @jve:decl-index=0:
	
	
	private MidiIn currentInput;
	private JLabel labelTempo = null;
	private JTextField textTempo = null;
		
	public LoopConfigPanel() {
		initialize();
		loadContent();
	}
	public void setConfig(Config config){
		this.config=config;
		loadFromConfig();
	}

	private void loadContent() {
		java.util.List<String> inputs=MidiConnect.getInputs();
		for(String in : inputs)
			comboInput.addItem(in);
		java.util.List<String> outputs=MidiConnect.getOutputs();
		for(String out : outputs)
			comboOutput.addItem(out);
		
	
		
		comboInput.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				String select=(String) comboInput.getSelectedItem();
				try {
					if(currentInput!=null)
						currentInput.close();
					currentInput=new ClassicInput(select);
					connectControls(currentInput);
				} catch (MidiUnavailableException e1) {
					error(e1);
				}	
			}		
		});
		
		comboTrackControls.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				int index=comboTrackControls.getSelectedIndex();
				if(index<0)
					return;
				textTrackControl.setText(""+config.trackControls[index]);
				textTrackControl.requestFocus();
			}			
		});
		
		
		//set messages
		Msgs.setToolTip(comboInput,Msgs.input);
		Msgs.setToolTip(comboOutput,Msgs.output);
		Msgs.setToolTip(textClear,Msgs.clear);
		Msgs.setToolTip(textRecord,Msgs.record);
		Msgs.setToolTip(textPause,Msgs.pause);
		
		Msgs.setToolTip(textPPQ,Msgs.resolution);
		Msgs.setToolTip(textSeqLength,Msgs.seqLen);
		Msgs.setToolTip(textTempo,Msgs.tempo);
		
		Msgs.setToolTip(textNbTrack,Msgs.nbTrack);
		Msgs.setToolTip(comboTrackControls,Msgs.track);
		Msgs.setToolTip(textTrackControl,Msgs.track);
		
		
		
	}
	
	protected void loadFromConfig(){
		comboInput.setSelectedItem(config.input);
		comboOutput.setSelectedItem(config.output);
		
		
		
		if(config.recordControl>0)
			textRecord.setText(""+config.recordControl);
		if(config.pauseControl>0)
			textPause.setText(""+config.pauseControl);
		if(config.clearControl>0)
			textClear.setText(""+config.clearControl);
		if(config.trackControls!=null)
			textNbTrack.setText(""+config.trackControls.length);
		if(config.tempo>0)
			textTempo.setText(""+config.tempo);
		if(config.sequencerLen>0)
			textSeqLength.setText(""+config.sequencerLen);
		if(config.resolution>0)
			textPPQ.setText(""+config.resolution);
		
		int[] t=config.trackControls;
		for(int i=0;i<t.length;i++){
			comboTrackControls.addItem("Control "+(i+1));
		}
		
		
	}
	
	private void connectControls(MidiIn input){
		input.addReceiver(new JGetControl(textClear));
		input.addReceiver(new JGetControl(textPause));
		input.addReceiver(new JGetControl(textRecord));
		input.addReceiver(new JGetControl(textTrackControl));		
	}

	private void error(Throwable e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Live Loop", JOptionPane.WARNING_MESSAGE);
		e.printStackTrace();
	}
	private void initialize() {
        createGroupDevices();
        createGroupControls();
        createGroupSequencer();
        this.setSize(new Dimension(519, 374));
        this.setSize(new Dimension(271, 142));
        
        this.setLayout(null);
        buttonStart = new JButton();
        this.add(buttonStart);
        
        buttonStart.setBounds(new Rectangle(195, 330, 121, 31));
        buttonStart.setText("Start Looping !");
        buttonStart.addActionListener(new ActionListener() {
        	boolean run=false;
        	
        	public void actionPerformed(ActionEvent arg0) {
        		if(!run){        			
        			run=start();
        			if(run){
	        			groupDevices.setEnabled(false);
	        			groupSequencer.setEnabled(false);
	        			groupControls.setEnabled(false);
	        			buttonStart.setText("Stop");
        			}
        		}else{
        			groupDevices.setEnabled(true);
        			groupSequencer.setEnabled(true);
        			groupControls.setEnabled(true);
        			buttonStart.setText("Start Looping !");
        			run=false;
        			stop();
        		
        		}    		
        		
        	}
        });
        this.setSize(new Dimension(467, 211));
		
	}
	
	protected boolean start(){
		if(currentInput!=null)
			currentInput.close();
		try{
		
			config.clearControl=Integer.parseInt(textClear.getText());
			config.pauseControl=Integer.parseInt(textPause.getText());
			config.recordControl=Integer.parseInt(textRecord.getText());
			
			config.input=(String) comboInput.getSelectedItem();
			config.output=(String) comboOutput.getSelectedItem();
			config.sequencerLen=Integer.parseInt(textSeqLength.getText());
			config.tempo=Integer.parseInt(textTempo.getText());
			config.resolution=Integer.parseInt(textPPQ.getText());
		}catch (Throwable e) {
			error(new IllegalArgumentException("You must complete all fields with correct values"));
			return false;
		}
		try {
			
			config.startLooping();
		} catch (MidiUnavailableException e) {
			error(e);
			return false;
		}
		return true;
	}
	
	protected void stop(){
		config.stop();
		if(currentInput!=null){
			try {
				currentInput=new ClassicInput((String)this.comboInput.getSelectedItem());
				connectControls(currentInput);
			} catch (MidiUnavailableException e) {
				error(e);
			}
		}
	}
	public void disposeInput(){
		if(currentInput!=null)
			currentInput.close();
	}

	/**
	 * This method initializes groupDevices	
	 *
	 */
	private void createGroupDevices() {
		groupDevices = new JGroup();
		this.add(groupDevices);
		
		groupDevices.setLayout(null);
		groupDevices.setText("Devices");
		groupDevices.setBounds(new Rectangle(15, 16, 496, 105));
		
		labelInput = new JLabel();
		groupDevices.add(labelInput);
		labelInput.setBounds(new Rectangle(18, 28, 91, 16));
		labelInput.setText("Input");
		labelOutput = new JLabel();
		groupDevices.add(labelOutput);
		labelOutput.setBounds(new Rectangle(18, 58, 91, 16));
		labelOutput.setText("Output :");
		createComboInput();
		createComboOutput();

	}

	/**
	 * This method initializes comboInput	
	 *
	 */
	private void createComboInput() {
		comboInput = new JComboBox();
		groupDevices.add(comboInput);
		comboInput.setBounds(new Rectangle(138, 28, 346, 21));
		
	}

	/**
	 * This method initializes comboOutput	
	 *
	 */
	private void createComboOutput() {
		comboOutput = new JComboBox();
		groupDevices.add(comboOutput);
		comboOutput.setBounds(new Rectangle(138, 58, 346, 21));
		
	}

	/**
	 * This method initializes groupControls	
	 *
	 */
	
	private void createGroupControls() {
		groupControls = new JGroup();
		this.add(groupControls);
		
		groupControls.setLayout(null);
		groupControls.setText("Controls Properties");
		groupControls.setBounds(new Rectangle(15, 135, 211, 181));
		labelNbTrack = new JLabel();
		groupControls.add(labelNbTrack);
		
		labelNbTrack.setBounds(new Rectangle(18, 118, 121, 16));
		labelNbTrack.setText("Number of Tracks :");
		textNbTrack = new JTextField();
		groupControls.add(textNbTrack);
		
		textNbTrack.setBounds(new Rectangle(153, 118, 46, 16));
		
		textNbTrack.addKeyListener(new KeyAdapter() {			
			

			@Override
			public void keyReleased(KeyEvent arg0) {
				fillTrackCombo();	
			}

		});
		labelRecord = new JLabel();
		groupControls.add(labelRecord);
		
		labelRecord.setBounds(new Rectangle(18, 28, 121, 16));
		labelRecord.setText("Record Control :");
		textRecord = new JTextField();
		groupControls.add(textRecord);
		
		textRecord.setBounds(new Rectangle(153, 28, 46, 16));
		/*textRecord.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				config.recordControl=Integer.parseInt(textRecord.getText());
			}
		});*/
		labelPause = new JLabel();
		groupControls.add(labelPause);
		
		labelPause.setBounds(new Rectangle(18, 58, 121, 16));
		labelPause.setText("Pause Control :");
		textPause = new JTextField();
		groupControls.add(textPause);
		
		textPause.setBounds(new Rectangle(153, 58, 46, 16));
		/*textPause.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				config.pauseControl=Integer.parseInt(textPause.getText());
			}
		});*/
		labelClear = new JLabel();
		groupControls.add(labelClear);
		
		labelClear.setBounds(new Rectangle(18, 88, 121, 16));
		labelClear.setText("Clear Control :");
		textClear = new JTextField();
		groupControls.add(textClear);
		
		textClear.setBounds(new Rectangle(153, 88, 46, 16));
		/*textClear.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
			public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
				config.clearControl=Integer.parseInt(textClear.getText());
			}
		});*/
		createComboTrackControls();
		textTrackControl = new JTextField();
		groupControls.add(textTrackControl);
		
		textTrackControl.setBounds(new Rectangle(153, 148, 46, 16));
		/*labelcp = new JLabel();
		groupControls.add(labelcp);
		
		labelcp.setBounds(new Rectangle(18, -2, 123, 17));
		labelcp.setText("<html><b>Controls Properties</b>");
		*/
		
		textTrackControl.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				int index=comboTrackControls.getSelectedIndex();
				if(index<0)
					return;
				config.trackControls[index]=(Integer.parseInt(textTrackControl.getText()));
			}
		});
		textTrackControl.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent evt) {				
				int index=comboTrackControls.getSelectedIndex();
				if(index<0)
					return;
				config.trackControls[index]=(Integer.parseInt(textTrackControl.getText()));
				
			}
			
		});
	}
	

	private void fillTrackCombo() {
		int nb=1;
		try{
			nb=Integer.parseInt(textNbTrack.getText());
		}catch (Throwable e) {
			// TODO: handle exception
		}
		if(nb>50)
			throw new IllegalArgumentException("Invalid Data");
		comboTrackControls.removeAllItems();
		
		config.trackControls=new int[nb];
		for(int i=0;i<nb;i++){
			comboTrackControls.addItem("Control "+(i+1));
		}
		
	}

	/**
	 * This method initializes groupSequencer	
	 *
	 */
	private void createGroupSequencer() {
		groupSequencer = new JGroup();
		this.add(groupSequencer);
		groupSequencer.setLayout(null);
		groupSequencer.setText("Loop Sequencer Properties");
		groupSequencer.setBounds(new Rectangle(240, 135, 271, 181));
		labelResolution = new JLabel();
		groupSequencer.add(labelResolution);
		
		labelResolution.setBounds(new Rectangle(18, 28, 121, 16));
		labelResolution.setText("Resolution in PPQ :");
		textPPQ = new JTextField();
		groupSequencer.add(textPPQ);
		
		textPPQ.setBounds(new Rectangle(180, 30, 46, 16));
		textPPQ.setText("4");
		labelSeqLength = new JLabel();
		groupSequencer.add(labelSeqLength);
		
		labelSeqLength.setBounds(new Rectangle(18, 58, 148, 16));
		labelSeqLength.setText("Sequence Length in QN :");
		textSeqLength = new JTextField();
		groupSequencer.add(textSeqLength);
		
		textSeqLength.setBounds(new Rectangle(180, 60, 46, 16));
		textSeqLength.setText("8");
		labelTempo = new JLabel();
		groupSequencer.add(labelTempo);
		
		labelTempo.setBounds(new Rectangle(18, 87, 148, 17));
		labelTempo.setText("Tempo in QN per Min :");
		textTempo = new JTextField();
		groupSequencer.add(textTempo);
		
		textTempo.setBounds(new Rectangle(180, 90, 46, 16));
		textTempo.setText("60");
		//manage tempo
		labelTempo.setVisible(false);
		textTempo.setVisible(false);
		
	}

	/**
	 * This method initializes comboTrackControls	
	 *
	 */
	private void createComboTrackControls() {
		comboTrackControls = new JComboBox();
		groupControls.add(comboTrackControls);
		
		comboTrackControls.setBounds(new Rectangle(18, 148, 121, 16));
	}
}  //  @jve:decl-index=0:visual-constraint="105,27"

