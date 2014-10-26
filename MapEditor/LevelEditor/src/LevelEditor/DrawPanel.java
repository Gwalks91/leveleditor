package LevelEditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

class DrawPanel extends JPanel 
{
	int textRows;
	int textCols;
	int textWidth;
	int textHeight;
	
	BufferedImage bigImg;
	BufferedImage lineImg;
	BufferedImage defaultImg;
    BufferedImage[] sprites;
    
    TexturePaint[] textureList;
    TexturePaint linePaint; 
    TexturePaint defaultPaint;
    
    int screenHeight;
    int screenWidth; 
    int tileRow; 
    int tileCol;
    int tileWidth;
    int tileHeight;
    int tileCount;
    
    int tilePaintNum;
    
    Tile[][] tiles;
    
	private TexturePaint currentPaint;
	private String fileName;

    public DrawPanel(int tileRow, int tileCol, int screenWidth, int screenHeight, 
    		String filePath, int textureRows, int textureCols, int tWidth, int tHeight) 
    {
    	this.tileRow = tileRow;
    	this.tileCol = tileCol;
    	this.screenWidth = screenWidth;
    	this.screenHeight = screenHeight;
    	
    	tileCount = tileRow * tileCol;
    	
    	tileWidth = (int)((screenWidth * 0.75) / tileCol);
        tileHeight = (int)(screenHeight / tileRow);
        
        tiles = new Tile[tileRow][tileCol];
        
        fileName = filePath;
           
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension((int)(screenWidth * 0.75), screenHeight));
        
        try 
        {
			bigImg = ImageIO.read(this.getClass().getResource("/Images/" + fileName));
			defaultImg = ImageIO.read(this.getClass().getResource("/Images/exitButton.png"));
			lineImg = ImageIO.read(this.getClass().getResource("/Images/open.png"));
		
		} 
        catch (IOException e) 
		{
			System.out.println("File for the sprite sheet not opened!");
		}
        
        textRows =  textureRows;
        textCols = textureCols;
        textWidth = tWidth;
        textHeight = tHeight;
        
        sprites = new BufferedImage[textRows * textCols];
        
        textureList = new TexturePaint[textureRows * textureCols];
        	
        loadImages();
        CreateTexturePaints();
        
        currentPaint = textureList[0];
        
        CreateTiles();
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
    	tiles[row][col].SetTileTexture(currentPaint);
    	tiles[row][col].SetTileNumber(tilePaintNum);
    }
    
    public void ChangeToStart(Point p)
    {
    	tiles[p.x][p.y].SetTileNumber(-1);
    }
    
    public void ChangeToEnd(Point p)
    {
    	tiles[p.x][p.y].SetTileNumber(-2);
    }
    
    public void SetTexture(int textureNum)
    {
    	currentPaint = textureList[textureNum];
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
    	w.println(tileRow + ", " + tileCol + ", " + textWidth 
    			+ ", " + textHeight + ", " + textRows + ", " + textCols + ", " +fileName);
    	for (int i = 0; i < tileRow; i++)
    	{
    		for (int j = 0; j< tileCol; j++)
    		{
    			w.print(tiles[i][j].GetTileNum() + " ");
    		}
    		w.println();
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

    
}