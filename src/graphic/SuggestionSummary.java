package graphic;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

public class SuggestionSummary {

	static JComboBox<String> suggestion = new JComboBox<String>();
	static JLabel summary = new JLabel();

	public static Component SuggestionSummaryFrame() {
		
		suggestion.addItem("Quickest");
		suggestion.addItem("Cheapest");
		suggestion.addItem("Touristic");
		summary.setText("Summary");
		summary.setText("Summary");

		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT, suggestion, summary);

		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sumSug, MapArea.MapFrame());
		splitMap.setDividerLocation(250);
		sumSug.setDividerLocation(35);

		return splitMap;
	}

}
