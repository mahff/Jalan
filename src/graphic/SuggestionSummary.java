package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.xml.stream.XMLStreamException;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGScrollPane;

import parsing.ShapeWays;

public class SuggestionSummary {

	static JSVGCanvas image = new JSVGCanvas();
	static JComboBox<String> suggestion = new JComboBox<String>();
	static JLabel summary = new JLabel();
	static int zoomlevel = 3;

	public static Component SuggestionSummaryFrame() {

		try {
			ShapeWays.generateMap();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}

		final JSVGCanvas image = new JSVGCanvas();
		image.setURI(new File("singapore.svg").toURI().toString());

		JSVGScrollPane map = new JSVGScrollPane(image);
		image.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent evt) {

				double rotation = evt.getPreciseWheelRotation();
				ActionMap map = image.getActionMap();
				Action action = null;

				zoomlevel += rotation;

				if (zoomlevel > 0 && zoomlevel < 4) {
					if (rotation > 0.0) {
						action = map.get(JSVGCanvas.ZOOM_OUT_ACTION);
					} else {
						action = map.get(JSVGCanvas.ZOOM_IN_ACTION);
					}
					action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
				} else if (zoomlevel <= 0) {
					setZoomlevel(1);
				} else {
					setZoomlevel(3);
				}
			}
		});

		suggestion.addItem("Quickest");
		suggestion.addItem("Cheapest");
		suggestion.addItem("Touristic");
		summary.setText("Summary");
		summary.setText("Summary");
		map.setToolTipText("Map");

		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT, suggestion, summary);
		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sumSug, map);
		splitMap.setDividerLocation(250);
		sumSug.setDividerLocation(35);
		return splitMap;
	}

	public static int getZoomlevel() {
		return zoomlevel;
	}

	public static void setZoomlevel(int zoomlevel) {
		SuggestionSummary.zoomlevel = zoomlevel;
	}

}
