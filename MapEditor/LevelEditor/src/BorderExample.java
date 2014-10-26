
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class BorderExample extends JFrame
{
	public BorderExample() 
	{
		initUI();
		
	}
	
	public final void initUI()
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		setResizable(false);
		
		top.setBackground(Color.gray);
		top.setPreferredSize(new Dimension(150, 150));
		top.setLayout(new GridLayout(2,2,2,2));
		panel.add(top);
		
		String[] buttons = { "1", "2", "3", "4" };
		
		for(int i = 0; i < buttons.length; i++)
		{
			top.add(new JButton(buttons[i]));
		}
		
		panel.setBorder(new EmptyBorder(new Insets(100, 100, 100, 100)));
		
		add(panel);
		
		pack();
		
		setTitle("Boarder Example");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                BorderExample ex = new BorderExample();
                ex.setVisible(true);
            }
        });
    }
}
