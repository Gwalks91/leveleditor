package LevelEditor;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuestionPanel extends JPanel
{
	private Question[] questions;
	private int currentQuestion = 0;
	private boolean running = true;
	
	public QuestionPanel()
	{
		questions = new Question[8];
		
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
		
		  int result = JOptionPane.showConfirmDialog(null, this, 
		           "Please enter information!", JOptionPane.OK_CANCEL_OPTION);
		  if (result == JOptionPane.OK_OPTION) 
		  {
			  running = false;
		  }
	}
	
	private void SetUpQuestions()
	{
		questions[0] = new Question("How many tile rows do you want?");
		questions[1] = new Question("How many tile columns do you want?");
		questions[2] = new Question("How many different textures do you have?");
		questions[3] = new Question("What is the file name for your textures?");
		questions[4] = new Question("How many rows are in your texture sheet?");
		questions[5] = new Question("How many columns are in your texture sheet?");
		questions[6] = new Question("How wide are your textures?");
		questions[7] = new Question("How tall are your textures?");
	}
	
	public boolean IsRunning()
	{
		return running;
	}
	
	public String GetAnswer(int questionIndex)
	{
		return questions[questionIndex].GetTextField().getText();
	}
	
	
}
