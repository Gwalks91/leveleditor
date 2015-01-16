package LevelEditor;

//import javax.swing.JButton;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JTextField;

public class Question
{
	private String questionStr;
	private JLabel qstionText;
	private JFormattedTextField questionField;
//	private int answer;
	
	public Question(String question, boolean numberField)
	{
		questionStr = question;
		if (numberField)
			questionField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		else
			questionField = new JFormattedTextField();
		
		initUI();
	}
	
	private void initUI()
	{
		qstionText = new JLabel(questionStr);
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
