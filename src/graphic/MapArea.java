package graphic;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.io.File;

import org.apache.batik.swing.JSVGCanvas;

public class MapArea {
	static JSVGCanvas image = new JSVGCanvas();
	private static AffineTransform transformation = new AffineTransform();

	
	public static Component MapFrame() {
		
		image = Options.returnImage();
		image.setURI(new File("singapore.svg").toURI().toString());
		image.setAutoscrolls(true);
		DragAndZoom();
		
		image.setToolTipText("Map");
		
		return image;
	}
	
	public static void DragAndZoom() {
		MouseAdapter adapter = new MouseAdapter() {
			double zoom = 1.0;
			final double SCALE_STEP = 0.25d;

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (zoom >= 1.0 && zoom < 2.6) {
					AffineTransform at = new AffineTransform();
					transformation = at;
					double zoomFactor = -SCALE_STEP * e.getPreciseWheelRotation();
					//System.out.println("speed : " + e.getPreciseWheelRotation());
					//System.out.println("zoomFactor: " + zoomFactor);
					zoom = Math.abs(zoom + zoomFactor);
					transformation.scale(zoom, zoom);
					image.setRenderingTransform(transformation, true);
					System.out.println("zoom : " + zoom);
				} else if (zoom < 1) {
					zoom = 1.0;
				} else if (zoom > 2.6) {
					zoom = 2.5;
				}

			}

			private Point origin;

			@Override
			public void mouseDragged(MouseEvent e) {

				AffineTransform at = new AffineTransform();
				transformation = at;
				int deltaX = origin.x - e.getX();
				int deltaY = origin.y - e.getY();
				// System.out.println("x=" + deltaX);
				// System.out.println("y=" + deltaY);
				if ((deltaX >= 0 && deltaX <= 1030) && (deltaY >= 0 && deltaY <= 622)) {
					image.setEnableImageZoomInteractor(true);
					transformation.translate(-deltaX, -deltaY);
					transformation.scale(zoom, zoom);
					image.setRenderingTransform(transformation, true);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				origin = new Point(e.getPoint());
				//System.out.println(origin);
			}
		};
		image.addMouseListener(adapter);
		image.addMouseMotionListener(adapter);
		image.addMouseWheelListener(adapter);
	}
}
