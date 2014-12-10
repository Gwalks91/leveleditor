package LevelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JMenu startMenu, toolMenu;
	public JMenuItem newFile, save, load, delete, hide;
	
	public MenuBar()
	{
		initUI();
	}
	
	private void initUI()
	{
		startMenu = new JMenu("Start");
		toolMenu = new JMenu("Tools");
		toolMenu.setMnemonic(KeyEvent.VK_A);
		
		hide = new JMenuItem("Hide Buttons", KeyEvent.VK_D);
		delete = new JMenuItem("Delete", KeyEvent.VK_S);
		
		newFile = new JMenuItem("New");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		
		startMenu.add(newFile);
		startMenu.add(save);
		startMenu.add(load);
		
		toolMenu.add(delete);
		toolMenu.add(hide);
		
		add(startMenu);
		add(toolMenu);
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
