package LevelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener
{
	public JMenu startMenu;
	public JMenuItem newFile, save, load;
	
	public MenuBar()
	{
		initUI();
	}
	
	private void initUI()
	{
		startMenu = new JMenu("Start");
		newFile = new JMenuItem("New");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		
		startMenu.add(newFile);
		startMenu.add(save);
		startMenu.add(load);
		
		add(startMenu);
	}

	@Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getActionCommand().equals("save"))
		{
			System.out.println("YAYAYAYAY");
		}
		
	}
}
