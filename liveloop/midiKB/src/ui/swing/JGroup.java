package ui.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class JGroup extends JPanel{
	
	
	public JGroup(){
		this.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	public void setText(String txt){
		this.setBorder(BorderFactory.createTitledBorder(txt));
	}

}
