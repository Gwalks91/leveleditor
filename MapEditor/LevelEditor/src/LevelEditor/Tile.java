package LevelEditor;

import java.awt.Rectangle;
import java.awt.TexturePaint;

public class Tile
{
	private Rectangle r;
	private int tileNumber;
	private TexturePaint tileTexture;
	
	public Tile(int xCord, int yCord, int width, int height, TexturePaint tileTexture)
	{
		r = new Rectangle(xCord, yCord, width, height);
		tileNumber = -999;
		this.tileTexture = tileTexture;
	}
	
	public Rectangle GetRect()
	{
		return r;
	}
	
	public void SetTileNumber(int tileNum)
	{
		tileNumber = tileNum;
	}
	
	public void SetTileTexture(TexturePaint t)
	{
		tileTexture = t;
	}
	
	public TexturePaint GetTileTexture()
	{
		return tileTexture;
	}
	
	public int GetTileNum()
	{
		return tileNumber;
	}

}
