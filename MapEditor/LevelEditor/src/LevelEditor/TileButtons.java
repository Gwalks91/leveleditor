package LevelEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;



public class TileButtons extends JFrame implements ActionListener, MouseListener
{
	private final int SCREEN_WIDTH = 1200;
	private final int SCREEN_HEIGHT = 650;
	private DrawPanel drwpnl;
	private JButton[] objectButtons;
	private JPanel buttonPanel, mainPanel;
	private MenuBar menu;
	private JPopupMenu tileMenu;
	private JFileChooser fc;
	
	GridLayout buttonLayout;
	
	private int bColNum;
	private int bRowNum;
	private int mouseX;
	private int mouseY;
	private int mouseOffSetY, mouseOffsetX;
	private boolean isOdd;
	private Point lastTilePressed;
	
	public TileButtons(int numTileRows, int numTileCols, int numOfTextures, String texturePath,
			int textSheetRows, int textSheetCols, int textWidth, int textHeight)
	{
		
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
		drwpnl = new DrawPanel(numTileRows, numTileCols, SCREEN_WIDTH, SCREEN_HEIGHT, 
				texturePath, textSheetRows, textSheetCols, textWidth, textHeight);
		
		initUI();
	}
	
	private void initUI()
	{
		buttonPanel = new JPanel();
		
		buttonLayout = new GridLayout(bRowNum, bColNum, 5, 5);
		buttonPanel.setLayout(buttonLayout);
		
		SetUpButtons();
		
		menu.setPreferredSize(new Dimension((SCREEN_WIDTH), (int)(SCREEN_HEIGHT * 0.03)));
		SetUpMenuButtons();
		
		mainPanel.add(drwpnl, BorderLayout.WEST);
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		mainPanel.add(menu, BorderLayout.NORTH);
		add(mainPanel);
		addMouseListener(this);
		
		mouseOffsetX = 10;
		mouseOffSetY = (int)(SCREEN_HEIGHT * 0.03) + 36;
		
		tileMenu = new JPopupMenu();
		JMenuItem setStart = new JMenuItem("Set to Start");
		JMenuItem setEnd = new JMenuItem("Set to End");
		
		setStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				drwpnl.ChangeToStart(lastTilePressed);
			}
		});
		
		setEnd.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				drwpnl.ChangeToEnd(lastTilePressed);
			}
		});
		
		tileMenu.add(setStart);
		tileMenu.add(setEnd);
		
		setTitle("Two Buttons");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		  
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
			objectButtons[i] = new JButton("Texture " + (i + 1), img);
			buttonPanel.add(objectButtons[i]);
			objectButtons[i].addActionListener(this);
			objectButtons[i].setActionCommand("Object " + i);
			objectButtons[i].setHorizontalTextPosition(JButton.CENTER);
			objectButtons[i].setVerticalTextPosition(JButton.CENTER);
			buttonPanel.add(objectButtons[i]);
		}
		
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
		
		for(int i = 0; i <num; i++)
		{
			if(objectButtons[i].getActionCommand().equals(evt.getActionCommand()))
			{
				//System.out.println("You pressed button: " + objectButtons[i].getText());
				drwpnl.SetTexture(i);
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
		mouseX = evt.getX() - mouseOffsetX;
		mouseY = evt.getY() - mouseOffSetY;
		
		lastTilePressed = drwpnl.CheckTiles(mouseX,  mouseY, evt.getButton());
		
		if(lastTilePressed.x != -1 && lastTilePressed.y != -1)
		{
			if(evt.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("got in the if statment");
				tileMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				
			}
			repaint();
		}
		
		//System.out.println("Mouse Pressed at: " + mouseX + ", " + mouseY);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}
	
	private void SetUpMenuButtons()
	{
		menu.newFile.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				drwpnl.ResetTiles();
				repaint();
			}
		});
		
		
		menu.save.addActionListener(new ActionListener() 
		{
			//Output the tiles to a file
			@Override
			public void actionPerformed(ActionEvent event) 
			{
				PrintWriter writer;
				try 
				{
					writer = new PrintWriter("Level.txt", "UTF-8");
					drwpnl.PrintTileNumbers(writer);
					writer.close();
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
				
			}
		});
		
		menu.load.addActionListener(new ActionListener() 
		{
			//Need to come back and let people load in a file they were working on
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				//Bring up file window.
				/*
				BufferedReader reader = new BufferedReader(new FileReader("/path/to/file.txt"));
				String line = null;
				while ((line = reader.readLine()) != null) {
				    // ...
				}
				*/
				int returnVal = fc.showSaveDialog(mainPanel);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//String[] fileName = file.getName().
					
				}
			}
		});
	}

}
