
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public class ToolbarsExample extends JFrame
{
	public ToolbarsExample()
	{
		initUI();
	}
	
	public final void initUI()
	{
		JToolBar toolbar1 = new JToolBar();
		JToolBar toolbar2 = new JToolBar();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		ImageIcon newi = new ImageIcon(this.getClass().getResource("/images/new.png"));
		ImageIcon open = new ImageIcon(this.getClass().getResource("/images/open.png"));
		ImageIcon save = new ImageIcon(this.getClass().getResource("/images/save.png"));
		ImageIcon exit = new ImageIcon(this.getClass().getResource("/images/exitButton.png"));
		
		JButton newb = new JButton(newi);
		JButton openb = new JButton(open);
		JButton saveb = new JButton(save);
		
		toolbar1.add(newb);
		toolbar1.add(openb);
		toolbar1.add(saveb);
		toolbar1.setAlignmentX(0);
		
		JButton exitb = new JButton(exit);
		toolbar2.add(exitb);
		toolbar2.setAlignmentX(0);
		
		exitb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		panel.add(toolbar1);
		panel.add(toolbar2);
		
		add(panel, BorderLayout.PAGE_END);
		
		setTitle("ToolBars");
		setSize(360,250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ToolbarsExample ex = new ToolbarsExample();
                ex.setVisible(true);
            }
        });
    }
}
