package LevelEditor;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SavePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Question saveFileNameQuestion;
	private boolean running = true;
	
	public SavePanel() {
		saveFileNameQuestion = new Question("What do you want to name this file?");
		
		initUI();
	}
	
	private void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(saveFileNameQuestion.GetLabel());
		add(saveFileNameQuestion.GetTextField());
		
		int result = JOptionPane.showConfirmDialog(null,this, "Enter in save file!", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			running = false;
		}
	}
	
	public boolean IsRunning() {
		return running;
	}
	
	public String GetAnswer() {
		return saveFileNameQuestion.GetTextField().getText();
	}
}
