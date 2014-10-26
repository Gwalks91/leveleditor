

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GridLayoutExample extends JFrame
{
	public GridLayoutExample()
	{
		initUI();
	}
	
	public final void initUI()
	{
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setLayout(new GridLayout(3, 2, 5, 5));
		panel.setMaximumSize(new Dimension(100, 100));
		panel.setBackground(new Color(0.1f, 0.1f, 0.1f));
		
		String[] buttons = 
		{
				"CLS", "BCK", "", "1", "2", "3",
		};
		
		for (int i = 0; i< buttons.length; i++)
		{
			if(i==2)
				panel.add(new JLabel(buttons[i]));
			else
				panel.add(new JButton(buttons[i]));
		}
		
		add(panel);
		
		setTitle("GridLayout");
		setSize(150,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                GridLayoutExample ex = new GridLayoutExample();
                ex.setVisible(true);
            }
        });
    }
}
