package graphic;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import org.apache.batik.swing.JSVGCanvas;

public class SuggestionSummary {
	AffineTransform at;
	double rapechelle;
	static JSVGCanvas image = new JSVGCanvas();
	static JComboBox<String> suggestion = new JComboBox<String>();
	static JLabel summary = new JLabel();

	public static Component SuggestionSummaryFrame() {

		final JSVGCanvas image = new JSVGCanvas();
		image.setURI(new File("singapore.svg").toURI().toString());
		image.setAutoscrolls(true);
		image.addMouseWheelListener(new MouseAdapter() {

			private double zoom = 1.0;
			public static final double SCALE_STEP = 0.1d;

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				double zoomFactor = -SCALE_STEP * e.getPreciseWheelRotation() * zoom;
				zoom = Math.abs(zoom + zoomFactor);
				AffineTransform at = new AffineTransform();
				at.scale(zoom, zoom);
				image.setRenderingTransform(at, true);
				System.out.println("Zooom");
			}

		});

		MouseAdapter adapter = new MouseAdapter() {

			private Point origin;

			@Override
			public void mouseMoved(MouseEvent e) {
				origin = new Point(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				AffineTransform at = new AffineTransform();
				int deltaX = origin.x - e.getX();
				int deltaY = origin.y - e.getY();
				image.setEnableImageZoomInteractor(true);
				at.translate(deltaX, deltaY);
				image.setRenderingTransform(at, true);
				System.out.println(deltaX);
				System.out.println(deltaY);

			}
		};

		image.addMouseListener(adapter);
		image.addMouseMotionListener(adapter);

		suggestion.addItem("Quickest");
		suggestion.addItem("Cheapest");
		suggestion.addItem("Touristic");
		summary.setText("Summary");
		summary.setText("Summary");
		image.setToolTipText("Map");

		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT, suggestion, summary);

		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sumSug, image);
		splitMap.setDividerLocation(250);
		sumSug.setDividerLocation(35);

		return splitMap;
	}
}
