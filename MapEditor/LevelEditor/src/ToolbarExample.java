
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolbarExample extends JFrame
{
	
	public ToolbarExample()
	{
		initUI();
	}
	
	private void initUI()
	{
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		menubar.add(file);
		setJMenuBar(menubar);
		
		JToolBar toolbar = new JToolBar();
		
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/exitButton.png"));
		
		JButton exitButton = new JButton(icon);
		JButton exitButton1 = new JButton(icon);
		JButton exitButton2 = new JButton(icon);
		JButton exitButton3 = new JButton(icon);
		toolbar.add(exitButton);
		toolbar.add(exitButton1);
		toolbar.add(exitButton2);
		toolbar.add(exitButton3);
		
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(toolbar, BorderLayout.SOUTH);
		
		setTitle("Simple toolbar");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolbarExample ex = new ToolbarExample();
                ex.setVisible(true);
            }
        });
    }
}
