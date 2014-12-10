package LevelEditor;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfirmPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String textToShow;
	private boolean running = true;
	private Component parent;
	
	public ConfirmPanel(String s, Component parent) {
		textToShow = s;
		
		this.parent = parent;
		initUI();
	}
	
	private void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new JLabel(textToShow));
		
		int result = JOptionPane.showConfirmDialog(parent, this, "Alert!", JOptionPane.DEFAULT_OPTION);
		if (result == JOptionPane.OK_OPTION)
			running = false;
	}
	
	public boolean IsRunning() {
		return running;
	}
}
