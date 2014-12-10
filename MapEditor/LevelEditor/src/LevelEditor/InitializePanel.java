package LevelEditor;

import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

public class InitializePanel extends JPanel {
	
	public enum State {
		NewFile,
		LoadFile,
		None
	}
	
	private JRadioButtonMenuItem loadButton;
	private JRadioButtonMenuItem newButton;
	private ButtonGroup radioMenu;
	private boolean running = true;
	private State mode;
	
	public InitializePanel() {
		mode = State.None;
		
		radioMenu = new ButtonGroup();
		loadButton = new JRadioButtonMenuItem("Load File");
		newButton = new JRadioButtonMenuItem("New File");
		
		radioMenu.add(loadButton);
		radioMenu.add(newButton);
		
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = State.LoadFile;
			}
		});
		
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mode = State.NewFile;
			}
		});
		initUI();
	}
	
	private void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new JLabel("New or load file?"));
		add(newButton);
		add(loadButton);
		//add(menu);
		
		int result = JOptionPane.showConfirmDialog(null, this, "Please enter information!", JOptionPane.OK_CANCEL_OPTION);
	  	if (result == JOptionPane.OK_OPTION) 
	  	{
	  		running = false;
	  	}
	}

	public State GetMode() {
		return mode;
	}
	
	public boolean IsRunning() {
		return running;
	}
}
