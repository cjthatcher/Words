package edu.usu.comd.nonsense.gui;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import edu.usu.comd.nonsense.Phoneme;

public class PhonemeCell extends ListCell<Phoneme> {

	@Override protected void updateItem(Phoneme item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null) {
			if (item.isHighestPriority()) 
			{
				setText(item.getName() + "*");
			}
			else
			{
				setText(item.getName());
			}
			setTooltip(new Tooltip(item.getTooltipText()));
		}
		if (item == null)
		{
			setText("");
			setTooltip(null);
		}
	}
}
