
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

public class FlowLayoutExample extends JFrame
{
	 public FlowLayoutExample() 
	 {
	        initUI();
	 }
	 
	 public final void initUI()
	 {
		 FlowLayout flow = new FlowLayout();
		 JPanel panel = new JPanel(flow);

		 flow.setAlignment(FlowLayout.CENTER);
		 JTextArea area = new JTextArea("Text Area");
		 area.setPreferredSize(new Dimension(100,100));
		 
		 JButton button = new JButton("Button");
		 panel.add(button);
		 
		 JTree tree = new JTree();
		 panel.add(tree);
		 
		 panel.add(area);
		 
		 add(panel);
		 
		 pack();
		 
	     setTitle("FlowLayout Example");
	     setSize(100, 400);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     setLocationRelativeTo(null);
	 }

	    public static void main(String[] args) {

	        SwingUtilities.invokeLater(new Runnable() {

	            public void run() {

	                FlowLayoutExample ex = new FlowLayoutExample();
	                ex.setVisible(true);
	            }
	        });
	    }
}

