package LevelEditor;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.logging.Logger;
//import java.util.logging.Level;





















import javax.imageio.ImageIO;
//import javax.swing.SwingUtilities;

class DrawPanel extends JPanel implements MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int TILE_SIZE = 64;
	
	private int textRows;
	private int textCols;
	private int textWidth;
	private int textHeight;
	
	private BufferedImage startImg;
	private BufferedImage enemyImg;
	private BufferedImage bigImg;
	private BufferedImage lineImg;
	private BufferedImage defaultImg;
	private BufferedImage[] sprites;
    
    TexturePaint[] textureList;
    TexturePaint linePaint; 
    TexturePaint defaultPaint;
    TexturePaint startTile;
    TexturePaint endTile;
    TexturePaint enemyTile;
    
    int screenHeight = 720;
    int screenWidth = 1280; 
    int tileRow; 
    int tileCol;
    int tileWidth;
    int tileHeight;
    int tileCount;
    int numOfTiles;
    
    int tilePaintNum;
    
    Tile[][] tiles;
    
    private boolean deleteMode;
    private File tilesheetFile;
	private TexturePaint currentPaint;
	private String fileName;
	private JPopupMenu tileMenu;
	private Point lastTilePressed;
	private Point startTilePoint;
	private boolean dragOn = false;
	private TexturePaint savedPaint;

    public DrawPanel(int tileRow, int tileCol, int screenWidth, int screenHeight, 
    		String file, int textureRows, int textureCols, int tWidth, int tHeight, String[] tilePlaces, int filledTiles) 
    {	
    	deleteMode = false;
    	startTilePoint = null;
    	tileMenu = new JPopupMenu();
		JMenuItem setStart = new JMenuItem("Set to Start");
		JMenuItem insertEnemy = new JMenuItem("Put enemy here");
		JMenuItem zoom = new JMenuItem("Zoom out");
		
		zoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
			
		});
		
		setStart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ChangeToStart(lastTilePressed);
			}
		});
		
		insertEnemy.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeToEnemy(lastTilePressed);
			}
		});
		
		tileMenu.add(setStart);
		tileMenu.add(insertEnemy);
		tileMenu.add(zoom);
		
    	this.tileRow = tileRow;
    	this.tileCol = tileCol;
    	
    	tileCount = tileRow * tileCol;
    	
        tileWidth = TILE_SIZE;
        tileHeight = TILE_SIZE; 
    	
        tiles = new Tile[tileRow][tileCol];
        
        fileName = file;
        //tilesheetFile = file;
        
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(this.tileWidth * this.tileCol,this.tileHeight * this.tileRow));
        addMouseListener(this);
        addMouseMotionListener(this);
        try 
        {
        	bigImg = ImageIO.read(this.getClass().getResource(fileName));
			defaultImg = ImageIO.read(this.getClass().getResource("Images/exitButton.png"));
			lineImg = ImageIO.read(this.getClass().getResource("Images/open.png"));
			startImg = ImageIO.read(this.getClass().getResource("Images/startTile.png"));
			enemyImg = ImageIO.read(this.getClass().getResource("Images/enemyTile.png"));
		} 
        catch (IOException e) 
		{
			//System.out.println("File for the sprite sheet not opened!");
        	ConfirmPanel cp = new ConfirmPanel("File for the sprite sheet not found!", this);
		}
        
        textRows =  textureRows;
        textCols = textureCols;
        textWidth = tWidth;
        textHeight = tHeight;
        numOfTiles = 0;
        
        sprites = new BufferedImage[textRows * textCols];
        
        textureList = new TexturePaint[textureRows * textureCols];
        	
        loadImages();
        CreateTexturePaints();
        
        currentPaint = textureList[0];
        CreateTiles();
        if (tilePlaces != null)
        	FillTiles(tilePlaces);
    }

	private void loadImages() 
    {
    	for (int i = 0; i < textRows; i++)
    	{
    		for (int j = 0; j < textCols; j++)
    		{
    			sprites[(i * textCols) + j] = 
    					bigImg.getSubimage(j * textWidth, i * textHeight, textWidth, textHeight);
    		}
    	}
    }
    
	private void CreateTexturePaints() 
	{
		for (int i = 0;  i < textRows; i++)
		{
			for (int j = 0; j < textCols; j++)
			{
				//This could be a problem
				textureList[(i * textCols) + j] = new TexturePaint(sprites[(i * textCols) + j],
						new Rectangle(0, 0, tileWidth, tileHeight));
			}
		}
		linePaint = new TexturePaint(lineImg, new Rectangle(0,0,100,100));
		defaultPaint = new TexturePaint(defaultImg, new Rectangle(0,0,100,100));
		startTile = new TexturePaint(startImg, new Rectangle(0,0,TILE_SIZE,TILE_SIZE));
		enemyTile = new TexturePaint(enemyImg, new Rectangle(0,0, TILE_SIZE, TILE_SIZE));
		
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        PaintTiles(g2d);
        
        DrawGridLines(g2d);
    }   
    
    public Point CheckTiles(int mouseX, int mouseY, int mButton)
    {
    	int row = FindTileRow(mouseY);
    	int col = FindTileCol(mouseX);
    	
    	Point p = new Point(row, col);
    	
    	if(row != -1 && col != -1)
    	{
		   if (mButton == MouseEvent.BUTTON1)
		   {
			   ChangeTile(row, col);
		   }
    	}
    	
    	return p;	
    }
    
    private int FindTileRow(int mouseY)
    {
    	for (int i = 0; i < tileRow; i++)
    	{
    		if(mouseY > i * tileHeight && mouseY < (i + 1) * tileHeight)
    		{
    			return i;
    		}
    	}
    	
    	return -1;
    }
    
    private int FindTileCol(int mouseX)
    {
    	for (int i = 0; i < tileCol; i++)
    	{
    		if(mouseX > i * tileWidth && mouseX < (i + 1) * tileWidth)
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    private void ChangeTile(int row, int col)
    {
    	Tile t = tiles[row][col];
    	t.SetTileTexture(currentPaint);
    	if (deleteMode)
    		numOfTiles--;
    	if (t.GetTileNum() == -999)
    	{
    		numOfTiles++;
    	}
    	t.SetTileNumber(tilePaintNum);
    }
    
    public void ChangeToStart(Point p)
    {
    	if (startTilePoint != null) {
    		Tile t = tiles[startTilePoint.x][startTilePoint.y];
    		if (t.GetTileNum() == -1) {
    			t.SetTileTexture(defaultPaint);
    			t.SetTileNumber(-999);    		
			}
    	}
    	
    	startTilePoint = p;
    	tiles[p.x][p.y].SetTileTexture(startTile);
    	tiles[p.x][p.y].SetTileNumber(-1);
    	
    	repaint();
    }
    
    public void ChangeToEnd(Point p)
    {
    	tiles[p.x][p.y].SetTileNumber(-2);
    }
    
    public void ChangeToEnemy(Point p) 
    {
    	tiles[p.x][p.y].SetTileTexture(enemyTile);
    	tiles[p.x][p.y].SetTileNumber(-3);
    	repaint();
    	
    }
    
    public void SetTexture(int textureNum)
    {
    	if (textureNum == -1){
    		currentPaint = startTile;
    	}
    	else if (textureNum == -3) {
    		currentPaint = enemyTile;
    	}
    	else if (textureNum == -999) {
    		currentPaint = defaultPaint;
    	}
    	else {
    		currentPaint = textureList[textureNum];
    	}
    	tilePaintNum = textureNum;
    }
    
    
    private void CreateTiles()
    {
        for (int i = 0; i < tileRow; i++)
        {
        	for (int j = 0; j < tileCol; j++)
        	{
        		tiles[i][j] = new Tile(j * tileWidth, i * tileHeight, tileWidth, tileHeight, defaultPaint);
        	}
        }
        
    }
    
    private void FillTiles(String[] tilePlaces) {
        for (String s : tilePlaces) {
        	String[] line = s.split(",");
        	SetTexture(Integer.parseInt(line[0]));
        	ChangeTile(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
        }
        SetTexture(0);
	}
    
    private void DrawGridLines(Graphics2D g)
    {
    	for (int i = 0; i <= tileRow; i++)
        {
        	for (int j = 0; j <= tileCol; j++)
        	{
        		Line2D l = new Line2D.Float(j * tileWidth, i * tileHeight, j * tileWidth,  tileRow * tileHeight);
        		Line2D l2 = new Line2D.Float(j * tileWidth, i * tileHeight, tileCol * tileHeight,  i * tileHeight);

        		g.setPaint(linePaint);
        		g.fill(l);
        		g.draw(l);
        		g.draw(l2);
        	}
        	
        }
        
    }
    
    private void PaintTiles(Graphics2D g2d)
    {
    	for (int i = 0; i < tileRow; i++)
        {
        	for (int j = 0; j < tileCol; j++)
        	{
        		g2d.setPaint(tiles[i][j].GetTileTexture());
                g2d.fill(tiles[i][j].GetRect());
        	}
        	
        }
    }
    
    
    
    public void PrintTileNumbers(PrintWriter w)
    {
    	w.println(tileRow + ", " + tileCol + ", " + numOfTiles + ", " + fileName);
    	for (int i = 0; i < tileRow; i++)
    	{
    		for (int j = 0; j< tileCol; j++)
    		{
    			Tile temp = tiles[i][j];
    			if(temp.GetTileNum() != -999)
    			{
    				w.print(temp.GetTileNum() + "," + i + "," + j + "||");
    			}
    		}
    	}
    }
    
    public void ResetTiles()
    {
    	currentPaint = textureList[0];
    	CreateTiles();
    }
    
    public BufferedImage GetSprite(int index)
    {
    	return sprites[index];
    }
    
	@Override
	public void mouseClicked(MouseEvent evt) {
		if (evt.getButton() == MouseEvent.BUTTON3) {
			System.out.println("Testing Zoom");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		int mouseX = evt.getX();
		int mouseY = evt.getY();
		
		lastTilePressed = CheckTiles(mouseX, mouseY, evt.getButton());
		if(lastTilePressed.x != -1 && lastTilePressed.y != -1)
		{
			if(evt.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("got in the if statment");
				tileMenu.show(evt.getComponent(), evt.getX(), evt.getY());
			}
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
	}
	
	public void setDeleteMode(boolean bool) {
		deleteMode = bool;
		if (deleteMode) {
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			savedPaint = currentPaint;
			SetTexture(-999);
		}
		else {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			currentPaint = savedPaint;
		}
		repaint();
	}
	
	public boolean getDeleteMode() {
		return deleteMode;
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		int mouseX = evt.getX();
		int mouseY = evt.getY();
		
    	int row = FindTileRow(mouseY);
    	int col = FindTileCol(mouseX);
    	
    	Point p = new Point(row, col);
		
    	if (p != lastTilePressed) {
			if (row != -1 && col != -1) {
				lastTilePressed = p;
				ChangeTile(row, col);
				repaint();
			}
    	}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
    
}
