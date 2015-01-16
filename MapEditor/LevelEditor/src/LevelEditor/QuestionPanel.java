package LevelEditor;

//import java.awt.BorderLayout;

//import javax.swing.Box;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
//import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private JComboBox box;
	private TilesheetCombo tilesheetCombo;
	
	private String spritesheetFile;
	private final TextFileFilter pngFilter;
	
	public QuestionPanel()
	{
		fcTilesheet = new JFileChooser();
		fcText = new JFileChooser();
		
		pngFilter = new TextFileFilter("PNG files only", ".png");
		
		fcTilesheet.setFileFilter(pngFilter);
		
		questions = new Question[3];
		tilesheetCombo = new TilesheetCombo();
		box = tilesheetCombo.getComboBox();
		
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				questions[2].GetTextField().setText(tilesheetCombo.getSelectedName());
				spritesheetFile = tilesheetCombo.getSelectedFile();
			}
			
		});
		
		initUI();
	}
	
	private void initUI()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		SetUpQuestions();
		
		final JTextField rowField = questions[0].GetTextField();
		final JTextField colField = questions[1].GetTextField();
		
		rowField.setText("22");
		rowField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				String s = rowField.getText();
				if (s.equals("")) {
					rowField.setText("22");
				}
				try {
					int height = Integer.parseInt(rowField.getText());
					if (height < 22) {
						rowField.setText("22");
					}
					else if (height > 50) {
						rowField.setText("50");
					}
				}
				catch (NumberFormatException e) {
					rowField.setText("22");
				}
			}
			
		});
		
		colField.setText("22");
		colField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				String s = colField.getText();
				if (s.equals("")) {
					colField.setText("22");
				}
				try {
					int width = Integer.parseInt(colField.getText());
					if (width < 22) {
						colField.setText("22");
					}
					else if (width > 50) {
						colField.setText("50");
					}
				}
				catch (NumberFormatException nfe) {
					colField.setText("22");
				}
				
			}
			
		});
		
		for(int i = 0; i < questions.length; i++)
		{
			add(questions[i].GetLabel());
			add(questions[i].GetTextField());
		}
		
		
		spritesheetFile = tilesheetCombo.getSelectedFile();
		questions[2].GetTextField().setText(spritesheetFile);
		questions[2].GetTextField().setEditable(false);
		add(box);
		
		
		int result = JOptionPane.showConfirmDialog(null, this, 
	           "Please enter information!", JOptionPane.OK_CANCEL_OPTION);
	  	if (result == JOptionPane.OK_OPTION) 
	  	{
	  		running = false;
	  	}
	}
	
	private void SetUpQuestions()
	{
		questions[0] = new Question("How many tile rows do you want?", true);
		questions[1] = new Question("How many tile columns do you want?", true);
		questions[2] = new Question("Where is your file for your textures?", false);
	}
	
	public boolean IsRunning()
	{
		return running;
	}
	
	public String GetAnswer(int questionIndex)
	{
		return questions[questionIndex].GetTextField().getText();
	}
	
	public String GetSpritesheetFile() {
		return spritesheetFile;
	}
	
}
