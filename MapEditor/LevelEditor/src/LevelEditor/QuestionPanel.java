package LevelEditor;

//import java.awt.BorderLayout;

//import javax.swing.Box;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JTextField;

public class QuestionPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Question[] questions;
	private boolean running = true;
	
	private JButton choosePath;
	
	private JFileChooser fcTilesheet;
	private JFileChooser fcText;
	
	private File spritesheetFile;
	private final TextFileFilter pngFilter;
	
	public QuestionPanel()
	{
		fcTilesheet = new JFileChooser();
		fcText = new JFileChooser();
		
		pngFilter = new TextFileFilter("PNG files only", ".png");
		
		fcTilesheet.setFileFilter(pngFilter);
		
		questions = new Question[2];
		
		
		choosePath = new JButton("Choose file");

		
		choosePath.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fcTilesheet.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					spritesheetFile = fcTilesheet.getSelectedFile();
					questions[1].GetTextField().setText(spritesheetFile.getAbsolutePath());
				}
			}
		});
		
		initUI();
	}
	
	private void initUI()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		SetUpQuestions();
		for(int i = 0; i < questions.length; i++)
		{
			add(questions[i].GetLabel());
			add(questions[i].GetTextField());
		}
		add(choosePath);
		
		
		
		int result = JOptionPane.showConfirmDialog(null, this, 
	           "Please enter information!", JOptionPane.OK_CANCEL_OPTION);
	  	if (result == JOptionPane.OK_OPTION) 
	  	{
	  		running = false;
	  	}
	}
	
	private void SetUpQuestions()
	{
		questions[0] = new Question("How many tile columns do you want?");
		questions[1] = new Question("Where is your file for your textures?");
		//questions[2] = new Question("Where do you want to save?")
		/*questions[1] = new Question("How many tile columns do you want?");
		questions[2] = new Question("How many different textures do you have?");
		questions[3] = new Question("What is the file name for your textures?");
		questions[4] = new Question("How many rows are in your texture sheet?");
		questions[5] = new Question("How many columns are in your texture sheet?");
		questions[6] = new Question("How wide are your textures?");
		questions[7] = new Question("How tall are your textures?");*/
	}
	
	public boolean IsRunning()
	{
		return running;
	}
	
	public String GetAnswer(int questionIndex)
	{
		return questions[questionIndex].GetTextField().getText();
	}
	
	public File GetSpritesheetFile() {
		return spritesheetFile;
	}
	
}
