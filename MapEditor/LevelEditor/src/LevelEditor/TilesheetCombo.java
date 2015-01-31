package LevelEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;

public class TilesheetCombo {
	
	private JComboBox box;
	private String selected;
	private String selectedFile;
	private Map<String, String> tilesheetMap;
	
	public TilesheetCombo() {
		//box.addItem("Default Tilesheet");
		tilesheetMap = new HashMap<String, String>();
		
		box = new JComboBox();
		tilesheetMap.put("Bandit Tile", "Images/BanditTile.png");
		selected = "Bandit Tile";
		setUp();
	}
	
	public String getSelectedName() {
		return selected;
	}
	
	public String getSelectedFile() {
		return selectedFile;
	}
	
	public void setUp() {
		for (Entry<String, String> kv : tilesheetMap.entrySet()) {
			box.addItem(kv.getKey());
		}
		
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				selectedFile = tilesheetMap.get(selected);
			}
		});
		
		selectedFile = tilesheetMap.get(selected);
	}
	public JComboBox getComboBox() {
		return box;
	}
}
