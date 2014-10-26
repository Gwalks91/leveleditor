
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalIconFactory;

public class SimpleMenuExample extends JFrame
{
	public SimpleMenuExample()
	{
		initUI();
	}
	
	private void initUI()
	{
		JMenuBar menuBar = new JMenuBar();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/exitButton.png"));
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem eMenuItem = new JMenuItem("Exit", icon);
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit Application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		file.add(eMenuItem);
		
		menuBar.add(file);
		
		setJMenuBar(menuBar);
		
		setTitle("Simple Menu");
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	 public static void main(String[] args) 
	 {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                SimpleMenuExample ex = new SimpleMenuExample();
	                ex.setVisible(true);
	            }
	        });
    }
}
