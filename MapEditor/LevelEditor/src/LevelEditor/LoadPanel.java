package LevelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoadPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser textFileChooser;
	private JFileChooser pngFileChooser;
	private Question questions[];
	
	private JButton chooseTextFile;
	private JButton chooseTilesheet;
	
	private TextFileFilter pngFilter;
	private TextFileFilter textFilter;
	
	private File tilesheetFile;
	private File textFile;
	
	private boolean running;
	
	public LoadPanel() {
		textFileChooser = new JFileChooser();
		pngFileChooser = new JFileChooser();
		
		questions = new Question[2];
		
		chooseTextFile = new JButton("Choose Text File");
		chooseTilesheet = new JButton("Choose Tilesheet");
		
		pngFilter = new TextFileFilter("PNG files only", ".png");
		textFilter = new TextFileFilter("Text files only", ".txt");
		
		textFileChooser.setFileFilter(textFilter);
		pngFileChooser.setFileFilter(pngFilter);
		
		running = true;
		
		chooseTextFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = textFileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textFile = textFileChooser.getSelectedFile();
					questions[0].GetTextField().setText(textFile.getAbsolutePath());
				}
			}
		});
		
		chooseTilesheet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int returnVal = pngFileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					tilesheetFile = pngFileChooser.getSelectedFile();
					questions[1].GetTextField().setText(tilesheetFile.getAbsolutePath());
				}
				
			}
			
		});
		
		initUI();
	}
	
	private void initUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		questions[0] = new Question("File name for text file?");
		questions[1] = new Question("File name for tilesheet to use?");
		
		for (int i = 0; i < questions.length; i++) {
			add(questions[i].GetLabel());
			add(questions[i].GetTextField());
			if (i == 0) {
				add(chooseTextFile);
			}
			else {
				add(chooseTilesheet);
			}
		}
		
		int result = JOptionPane.showConfirmDialog(null,  this, "Enter in load information", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			running = false;
		}
	}
	
	public boolean IsRunning() {
		return running;
	}
	
	public File getTextFile() {
		return textFile;
	}
	
	public File getTilesheet() {
		return tilesheetFile;
	}
}
