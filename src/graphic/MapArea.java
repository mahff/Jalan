package graphic;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import org.apache.batik.swing.JSVGCanvas;

public class MapArea {
	static double zoom = 1.0;
	static JSVGCanvas image = Options.returnImage(); 
	private static AffineTransform transformation = new AffineTransform();

	public static Component mapFrame() {
		image.setToolTipText("Map");
		dragAndZoom();
		return image;
	}

	public static void dragAndZoom() {
		
		MouseAdapter adapter = new MouseAdapter() { 
			final double SCALE_STEP = 0.25d;
			private Point origin;

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (zoom >= 1.0 && zoom < 2.6) {
					AffineTransform at = new AffineTransform();
					transformation = at;
					double zoomFactor = -SCALE_STEP * e.getPreciseWheelRotation();
					zoom = Math.abs(zoom + zoomFactor);
					System.out.println(zoom);
					transformation.scale(zoom, zoom);
					image.setRenderingTransform(transformation, true);
				}
				 else if (zoom < 1) {
					zoom = 1.0;
				} else if (zoom > 2.6) {
					zoom = 2.5;
				}

			}

			@Override
			public void mouseDragged(MouseEvent e) {

				AffineTransform at = new AffineTransform();
				transformation = at;
				int deltaX = origin.x - e.getX();
				int deltaY = origin.y - e.getY();
				image.setEnableImageZoomInteractor(true);
				transformation.translate(-deltaX, -deltaY);
				transformation.scale(zoom, zoom);
				image.setRenderingTransform(transformation, true);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				origin = new Point(e.getPoint());
			}
		};
		
		image.addMouseListener(adapter);
		image.addMouseMotionListener(adapter);
		image.addMouseWheelListener(adapter);
	}
	
	public static JSVGCanvas returnImage() {
		return image;
	}

	
}
