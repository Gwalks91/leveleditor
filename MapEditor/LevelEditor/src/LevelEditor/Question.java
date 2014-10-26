package LevelEditor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Question
{
	private String questionStr;
	private JLabel qstionText;
	private JTextField questionField;
	private int answer;
	
	public Question(String question)
	{
		questionStr = question;
		initUI();
	}
	
	private void initUI()
	{
		qstionText = new JLabel(questionStr);
		questionField = new JTextField(5);
	}
	
	public JLabel GetLabel()
	{
		return qstionText;
	}
	
	public JTextField GetTextField()
	{
		return questionField;
	}
}
