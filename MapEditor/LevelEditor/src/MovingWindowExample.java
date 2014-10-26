import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class MovingWindowExample extends JFrame
        implements ComponentListener {

    private JLabel labelx;
    private JLabel labely;

    public MovingWindowExample() {

        initUI();
    }

    public final void initUI() {
    	setExtendedState(JFrame.MAXIMIZED_BOTH); 

        JPanel panel = new JPanel();
        panel.setLayout(null);

        labelx = new JLabel("x: ");
        labelx.setFont(new Font("Serif", Font.BOLD, 14));
        labelx.setBounds(20, 20, 60, 25);

        labely = new JLabel("y: ");
        labely.setFont(new Font("Serif", Font.BOLD, 14));
        labely.setBounds(20, 45, 60, 25);

        panel.add(labelx);
        panel.add(labely);

        add(panel);

        addComponentListener(this);
        getWidth();

        setTitle("Moving window");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void componentResized(ComponentEvent e) {
    	  int x = e.getComponent().getWidth();
          int y = e.getComponent().getHeight();
          labelx.setText("x: " + x);
          labely.setText("y: " + y);
    }

    public void componentMoved(ComponentEvent e) {
      
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                MovingWindowExample ex = new MovingWindowExample();
                ex.setVisible(true);
            }
        });
    }
}
