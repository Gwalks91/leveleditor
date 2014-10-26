
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SimpleExample extends JFrame{
	
	public SimpleExample()
	{
		setTitle("Grants First Swing Project");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				SimpleExample ex = new SimpleExample();
				ex.setVisible(true);
			}
		});
	}

}
