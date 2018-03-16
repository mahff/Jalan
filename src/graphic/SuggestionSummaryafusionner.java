package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGScrollPane;

public class SuggestionSummaryafusionner {

	static JSVGCanvas image = new JSVGCanvas();
	static JComboBox<String> suggestion = new JComboBox<String>();
	static JLabel summary = new JLabel();

	public static Component SuggestionSummaryFrame() {

		final JSVGCanvas image = new JSVGCanvas();
		image.setURI(new File("SVG_logo.svg").toURI().toString());

		JSVGScrollPane map = new JSVGScrollPane(image);

		image.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent evt) {

				double rotation = evt.getPreciseWheelRotation();
				ActionMap map = image.getActionMap();
				Action action = null;

				if (rotation > 0.0) {
					action = map.get(JSVGCanvas.ZOOM_OUT_ACTION);
				} else {
					action = map.get(JSVGCanvas.ZOOM_IN_ACTION);
				}
				action.actionPerformed(new ActionEvent(this,
						ActionEvent.ACTION_PERFORMED, null));
			}
		});

		//suggestion.setText("Suggestion");
		suggestion.addItem("Quickest");
		suggestion.addItem("Cheapest");
		suggestion.addItem("Touristic");
		summary.setText("Summary");
		map.setToolTipText("Map");

		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				suggestion, summary);
		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				sumSug, map);
		splitMap.setDividerLocation(300);
		sumSug.setDividerLocation(35);
		return splitMap;
	}

}
