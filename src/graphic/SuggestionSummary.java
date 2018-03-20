package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.xml.stream.XMLStreamException;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGScrollPane;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import parsing.ShapeWays;

public class SuggestionSummary {

	static JSVGCanvas image = new JSVGCanvas();
	static JComboBox<String> suggestion = new JComboBox<String>();
	static JLabel summary = new JLabel();

	public static Component SuggestionSummaryFrame() {

		final JSVGCanvas image = new JSVGCanvas();
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
		SVGDocument document;
		try {
			document = factory.createSVGDocument("", new ByteArrayInputStream(ShapeWays.generateMap().getBytes("UTF-8")));
			image.setSVGDocument(document);
		} catch (IOException | XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		JSVGScrollPane map = new JSVGScrollPane(image);

		image.addMouseWheelListener(new MouseWheelListener() {

			int zoomlevel = 3;

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
				} else if (zoomlevel <= 0){
					zoomlevel = 1;
				} else {
					zoomlevel = 3;
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
		splitMap.setDividerLocation(300);
		sumSug.setDividerLocation(35);
		return splitMap;
	}

}
