package LevelEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;



public class TileButtons extends JFrame implements ActionListener, MouseListener
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int SCREEN_WIDTH = 1000;
	private int SCREEN_HEIGHT = 720;
	private DrawPanel drwpnl;
	private JButton[] objectButtons;
	private JPanel buttonPanel, mainPanel;
	private MenuBar menu;
	//private JPopupMenu tileMenu;
	private JFileChooser fc;
	private JScrollPane scrollPanel;
	
	GridLayout buttonLayout;
	
	private File loadedFile;
	private String loadedLevelName;
	private int bColNum;
	private int bRowNum;
	private boolean isOdd;
	private boolean hideButtons;
	//private Point lastTilePressed;
	private File spritesheetFile;
	private JButton currentButton;
	private Border unselectedBorder;
	private Border selectedBorder;
	
	public TileButtons(int numTileRows, int numTileCols, int numOfTextures, File textureFile,
			int textSheetRows, int textSheetCols, int textWidth, int textHeight, File tilePlaces, String[] tiles)
	{
		hideButtons = false;
		spritesheetFile = textureFile;
		if (tilePlaces == null) {
			loadedFile = null;
			loadedLevelName = null;
		}
		else {
			loadedFile = tilePlaces;
			loadedLevelName = tilePlaces.getName();
		}
		
		if(numOfTextures % 2 == 0)
		{
			bColNum = 2;
			bRowNum = numOfTextures / 2;
			isOdd = false;
		}
		else
		{
			bColNum = 2;
			bRowNum = (numOfTextures / 2) + 1;
			isOdd = true;
		}
		
		mainPanel = new JPanel(new BorderLayout());
		objectButtons = new JButton[bColNum * bRowNum];
		menu = new MenuBar();
		fc = new JFileChooser();
		TextFileFilter test = new TextFileFilter("Text Files only", ".txt");
		fc.setFileFilter(test);
		drwpnl = new DrawPanel(numTileRows, numTileCols, SCREEN_WIDTH, SCREEN_HEIGHT, 
				spritesheetFile, textSheetRows, textSheetCols, textWidth, textHeight, tiles, 0);
		scrollPanel = new JScrollPane(drwpnl);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setPreferredSize(new Dimension(800,600));
		scrollPanel.getHorizontalScrollBar().setUnitIncrement(35);
		scrollPanel.getVerticalScrollBar().setUnitIncrement(35);
		drwpnl = (DrawPanel)scrollPanel.getViewport().getView();  
		initUI();
	}
	
	private void initUI()
	{
		buttonPanel = new JPanel();
		
		buttonLayout = new GridLayout(bRowNum, bColNum, 5, 5);
		buttonPanel.setLayout(buttonLayout);
		
		SetUpButtons();
		menu.setPreferredSize(new Dimension(SCREEN_WIDTH, (int)(SCREEN_HEIGHT * 0.03)));
		SetUpMenuButtons();
		
		mainPanel.add(scrollPanel, BorderLayout.WEST);
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		mainPanel.add(menu, BorderLayout.NORTH);	
		
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("D"), "Delete");
		mainPanel.getActionMap().put("Delete", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(drwpnl.getDeleteMode())
				drwpnl.setDeleteMode(false);
			else
				drwpnl.setDeleteMode(true);
			}
			
		});
		
		mainPanel.getInputMap().put(KeyStroke.getKeyStroke("S"), "Hide");
		mainPanel.getActionMap().put("Hide", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hideButtons = !hideButtons;
				int tempWidth = buttonPanel.getWidth();
				if (hideButtons) {
					scrollPanel.setPreferredSize(new Dimension(tempWidth + scrollPanel.getWidth(), scrollPanel.getHeight()));
					buttonPanel.setVisible(false);
					menu.hide.setText("Show buttons");
				}
				else {
					scrollPanel.setPreferredSize(new Dimension(scrollPanel.getWidth() - tempWidth, scrollPanel.getHeight()));
					buttonPanel.setVisible(true);
					menu.hide.setText("Hide buttons");
				}
				repaint();
			}
		});
		mainPanel.addMouseListener(this);
		
//		mouseOffsetX = 10;
//		mouseOffSetY = (int)(SCREEN_HEIGHT * 0.03) + 36;
		
		//tileMenu = new JPopupMenu();
    	
		if (loadedLevelName == null)
			setTitle("New Level");
		else
			setTitle(loadedLevelName);
		//setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setSize(SCREEN_WIDTH, SCREEN_WIDTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		add(mainPanel);
		
		pack();
		repaint();
	}
	
	private void SetUpButtons()
	{
		buttonPanel.setLayout(buttonLayout);
		int num = bRowNum * bColNum;
		if(isOdd)
			num--;

		for(int i = 0; i < num; i++)
		{
			ImageIcon img =  new ImageIcon(drwpnl.GetSprite(i));
			objectButtons[i] = new JButton("", img);
			buttonPanel.add(objectButtons[i]);
			//System.out.println("Inside SetUpButtons: " + i);
			objectButtons[i].setActionCommand(String.valueOf(i));
			objectButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//CheckButtons(e);
					drwpnl.SetTexture(Integer.parseInt(e.getActionCommand()));
					objectButtons[Integer.parseInt(e.getActionCommand())].setBorder(selectedBorder);
					currentButton.setBorder(unselectedBorder);
					currentButton = objectButtons[Integer.parseInt(e.getActionCommand())];
					mainPanel.requestFocusInWindow();
				}
				
			});
			objectButtons[i].setHorizontalTextPosition(JButton.CENTER);
			objectButtons[i].setVerticalTextPosition(JButton.CENTER);
			buttonPanel.add(objectButtons[i]);
		}
		
		unselectedBorder = objectButtons[0].getBorder();
		objectButtons[0].setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
		selectedBorder = objectButtons[0].getBorder();
		currentButton = objectButtons[0];
		
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
		buttonPanel.setPreferredSize(new Dimension((int)(SCREEN_WIDTH * 0.25), SCREEN_HEIGHT));
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		CheckButtons(e);
	}
	
	private void CheckButtons(ActionEvent evt)
	{
		int num = bRowNum * bColNum;
		if(isOdd)
			num--;
		//System.out.println(evt.getActionCommand());
		for(int i = 0; i <num; i++)
		{
			if(objectButtons[i].getActionCommand().equals(evt.getActionCommand()))
			{
				//System.out.println("You pressed button: " + objectButtons[i].getText());
//				DrawPanel test = (DrawPanel)scrollPanel.getViewport().getView();
//				if (test.getDeleteMode())
//					test.setDeleteMode(false);
//				test.SetTexture(i);
//				if (drwpnl.getDeleteMode())
//					drwpnl.setDeleteMode(false);
				drwpnl.SetTexture(i);
				mainPanel.setFocusable(true);
				//drwpnl.SetTexture(i);
			}
		}
	}

	
	@Override
	public void mouseClicked(MouseEvent evt) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}
	
	//Mouse has some weird shit that happens with it because the mouse is offset by 9 pixels 
	//on the X and 36 pixels on the y
	@Override
	public void mousePressed(MouseEvent evt)
	{
//		mouseX = evt.getX() - mouseOffsetX;
//		mouseY = evt.getY() - mouseOffSetY;
//		
//		DrawPanel panel = (DrawPanel) scrollPanel.getViewport().getView();
//		lastTilePressed = panel.CheckTiles(mouseX,  mouseY, evt.getButton());
//		System.out.println("yes");
//		//JPanel panel = (JPanel)scrollPanel.getViewport().getView();
//		if(lastTilePressed.x != -1 && lastTilePressed.y != -1)
//		{
//			if(evt.getButton() == MouseEvent.BUTTON3)
//			{
//				System.out.println("got in the if statment");
//				tileMenu.show(evt.getComponent(), evt.getX(), evt.getY());
//				
//			}
//			repaint();
//		}
		
		//System.out.println("Mouse Pressed at: " + mouseX + ", " + mouseY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}
	
	public void bacon() {
		
	}
	
	private void SetUpMenuButtons()
	{
		menu.hide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				hideButtons = !hideButtons;
				int tempWidth = buttonPanel.getWidth();
				if (hideButtons) {
					scrollPanel.setPreferredSize(new Dimension(tempWidth + scrollPanel.getWidth(), scrollPanel.getHeight()));
					buttonPanel.setVisible(false);
					menu.hide.setText("Show buttons");
				}
				else {
					scrollPanel.setPreferredSize(new Dimension(scrollPanel.getWidth() - tempWidth, scrollPanel.getHeight()));
					buttonPanel.setVisible(true);
					menu.hide.setText("Hide buttons");
				}
				repaint();
			}
			
		});
		
		
		menu.delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(drwpnl.getDeleteMode())
					drwpnl.setDeleteMode(false);
				else
					drwpnl.setDeleteMode(true);
				
			}
			
		});
		
		menu.newFile.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				drwpnl.ResetTiles();
				revalidate();
				repaint();
			}
		});
		
		
		menu.save.addActionListener(new ActionListener() 
		{
			//Output the tiles to a file
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				PrintWriter writer = null;
				String directory = "";
				File newFile = null;
				DrawPanel panel = (DrawPanel)scrollPanel.getViewport().getView();
				
				if (loadedFile != null) {
					directory = loadedFile.getParentFile().getAbsolutePath();
				}
				else {
					SavePanel sp = new SavePanel();
					String fileName = sp.GetAnswer();
					fc.setDialogTitle("Select where to save file");
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = fc.showSaveDialog(mainPanel);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						directory = fc.getSelectedFile().getAbsolutePath();
						newFile = new File(directory + "/" + fileName + ".txt");
					}
					loadedFile = newFile;
				}
				
				try 
				{
					if (loadedFile == null)
						writer = new PrintWriter(newFile);
					else
						writer = new PrintWriter(loadedFile);
					panel.PrintTileNumbers(writer);
					writer.close();
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				ConfirmPanel cp = new ConfirmPanel("File is saved!", mainPanel);
			}
		});
		
		menu.load.addActionListener(new ActionListener() 
		{
			//Need to come back and let people load in a file they were working on
			@Override
			public void actionPerformed(ActionEvent evt)
			{
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

							mainPanel.removeAll();
							int numOfTextures = 22;

							if (numOfTextures % 2 == 0) {
								bColNum = 2;
								bRowNum = numOfTextures / 2;
								isOdd = false;
							} else {
								bColNum = 2;
								bRowNum = (numOfTextures / 2) + 1;
								isOdd = true;
							}
							menu = new MenuBar();
							objectButtons = new JButton[bColNum * bRowNum];
							File loadingTextureSheet = loadPrompt.getTilesheet();
							drwpnl = new DrawPanel(
									Integer.parseInt(firstLine[0]),
									Integer.parseInt(firstLine[1]),
									SCREEN_WIDTH, SCREEN_HEIGHT,
									loadingTextureSheet, 5, 5, 100, 100,
									tilePlaces,
									Integer.parseInt(firstLine[2]));
							scrollPanel = new JScrollPane(drwpnl);
							scrollPanel
									.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							scrollPanel
									.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
							scrollPanel.setPreferredSize(new Dimension(800,
									600));

							initUI();

							mainPanel.revalidate();
							mainPanel.repaint();

							loadedLevelName = fileName[0];
							spritesheetFile = loadingTextureSheet;
							loadedFile = textFile;
							setTitle(loadedLevelName);

							br.close();
							fr.close();
						} catch (FileNotFoundException e) {
							ConfirmPanel cp = new ConfirmPanel(
									"File not found!", mainPanel);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						ConfirmPanel cp = new ConfirmPanel(
								"File is invalid!", mainPanel);
					}
				}
			}
		});
	}

}
