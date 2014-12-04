package edu.usu.comd.nonsense.gui;

import javafx.scene.control.ListCell;
import edu.usu.comd.nonsense.Glyph;

public class GlyphCell extends ListCell<Glyph>{
	
	@Override protected void updateItem(Glyph item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null) {
			setText(String.valueOf(item.getChars()));
		}
		if (item == null)
		{
			setText("");
			setTooltip(null);
		}
	}

}
