package LevelEditor;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Editor 
{
	public static void main(String [] args)
	{
		InitializePanel ip = new InitializePanel();
		
		if (!ip.IsRunning()) {
			InitializePanel.State state = ip.GetMode();
			if (state == InitializePanel.State.LoadFile) {
				LoadPanel loadPrompt = new LoadPanel();
				if (!loadPrompt.IsRunning()) {
					File textFile = loadPrompt.getTextFile();
					String[] fileName = textFile.getName().split("\\.");
					
					if (fileName[1].equals("txt")) {
						FileReader fr;
						BufferedReader br;
						try {
							fr = new FileReader(textFile);
							br = new BufferedReader(fr);

							//Will the file always be 2 lines??
							String currentLine = br.readLine();
							ArrayList<String> listOfStrings = new ArrayList<String>();

							while (currentLine != null) {
								listOfStrings.add(currentLine);
								currentLine = br.readLine();
							}

							String[] firstLine = listOfStrings.get(0)
									.split(",\\s");
							String[] tilePlaces = listOfStrings.get(1)
									.split("\\|\\|");
							for (String s : firstLine) {
								System.out.println(s);
							}
							
							TileButtons ex = new TileButtons(Integer.parseInt(firstLine[0]), Integer.parseInt(firstLine[1]),20, 
									loadPrompt.getTilesheet(),4,5,100,100, loadPrompt.getTextFile(), tilePlaces);
							ex.setVisible(true);
							
							br.close();
							fr.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			else if (state == InitializePanel.State.NewFile) {
				QuestionPanel q = new QuestionPanel();
				
				if(!q.IsRunning())
				{	
					TileButtons ex = new TileButtons(Integer.parseInt(q.GetAnswer(0)), Integer.parseInt(q.GetAnswer(1)), 20,
						q.GetSpritesheetFile(), 4, 5, 100, 100, null, null);
					ex.setVisible(true);
				}
			}
		}
//		QuestionPanel q = new QuestionPanel();
//
//		if(!q.IsRunning())
//		{	
//			TileButtons ex = new TileButtons(22, Integer.parseInt(q.GetAnswer(0)), 20,
//				q.GetSpritesheetFile(), 4, 5, 100, 100);
//			
//			ex.setVisible(true);
//		}
	}
	//"TileSheet.png"
	//"3x3Tiles.png"
}
