package LevelEditor;

public class Editor 
{
	public static void main(String [] args)
	{
		QuestionPanel q = new QuestionPanel();

		if(!q.IsRunning())
		{	
			TileButtons ex = new TileButtons(
					Integer.parseInt(q.GetAnswer(0)), 
					Integer.parseInt(q.GetAnswer(1)), 
					Integer.parseInt(q.GetAnswer(2)),
					q.GetAnswer(3), 
					Integer.parseInt(q.GetAnswer(4)),
					Integer.parseInt(q.GetAnswer(5)),
					Integer.parseInt(q.GetAnswer(6)),
					Integer.parseInt(q.GetAnswer(7)));
			
			ex.setVisible(true);
		}
	}
	//"TileSheet.png"
	//"3x3Tiles.png"
}
