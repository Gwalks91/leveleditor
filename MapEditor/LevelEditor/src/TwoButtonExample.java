
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class TwoButtonExample extends JFrame {

    public TwoButtonExample() {

        initUI();
    }

    public final void initUI() 
    {
        JPanel basic = new JPanel();
        basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
        add(basic);

        basic.add(Box.createHorizontalGlue());

        JPanel bottom = new JPanel();
        bottom.setAlignmentX(2f);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JButton ok = new JButton("OK");
        JButton close = new JButton("Close");

        bottom.add(ok);
        bottom.add(Box.createRigidArea(new Dimension(15, 10)));
        bottom.add(close);
        bottom.add(Box.createRigidArea(new Dimension(15, 0)));

        basic.add(bottom);
        basic.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JPanel top = new JPanel();
		
		top.setBackground(Color.gray);
		top.setPreferredSize(new Dimension(150, 150));
		top.setLayout(new GridLayout(2,2,2,2));
		basic.add(top);
		
		String[] buttons = { "1", "2", "3", "4" };
		
		for(int i = 0; i < buttons.length; i++)
		{
			top.add(new JButton(buttons[i]));
		}

        setTitle("Two Buttons");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                TwoButtonExample ex = new TwoButtonExample();
                ex.setVisible(true);
            }
        });
    }
}