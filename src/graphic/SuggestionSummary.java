package graphic;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.xml.stream.XMLStreamException;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.JSVGScrollPane;

import mapping.ShapeWays;
import mapping.FirstZoomLevel;
import mapping.SecondZoomLevel;
import mapping.ThirdZoomLevel;

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
		image.setAutoscrolls(true);
		JSVGScrollPane map = new JSVGScrollPane(image);
			
			 MouseAdapter adapter = new MouseAdapter() {

                 private Point origin;

                 public void mousePressed(MouseEvent e) {
                     origin = new Point(e.getPoint());
                 }

                 public void mouseReleased(MouseEvent e) {
                 }

                 public void mouseDragged(MouseEvent e) {
                     if (origin != null) {
                         JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, map);
                         if (viewPort != null) {
                             int deltaX = origin.x - e.getX();
                             int deltaY = origin.y - e.getY();

                             Rectangle view = viewPort.getViewRect();
                             view.x += deltaX;
                             view.y += deltaY;

                             map.scrollRectToVisible(view);
                         }
                     }
                 }
                 
                 
                 public void mouseWheelMoved(MouseWheelEvent e) {

     				double rotation = e.getPreciseWheelRotation();
     				ActionMap map = image.getActionMap();
     				Action action = null;

     				zoomlevel += rotation;

     				if (zoomlevel > 0 && zoomlevel < 4) {

     					if (zoomlevel == 3) {
     						new FirstZoomLevel();
     						image.setURI(new File("singapore.svg").toURI().toString());
     					} else if (zoomlevel == 2) {
     						new SecondZoomLevel();
     						image.setURI(new File("singapore.svg").toURI().toString());
     					} else if (zoomlevel == 1) {
     						new ThirdZoomLevel();
     						image.setURI(new File("singapore.svg").toURI().toString());
     					}
     					action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
     				} else if (zoomlevel <= 0) {
     					setZoomlevel(1);
     				} else {
     					setZoomlevel(3);
     				}
     			}
			 };

		map.addMouseListener(adapter);
        map.addMouseMotionListener(adapter);
        image.addMouseWheelListener(adapter);
        
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
