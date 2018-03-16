package parsing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

public class ShapeWays extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Shape shape;

	public ShapeWays() {
		DrawWays();
	}

	public void DrawWays() {
		JALDocument call = new JALDocument("singapore.jal"); //CHANGE PATH IF NEED
		try {
			for (String index : call.getElementsDataByType("way")) {
				ArrayList<Double> longi = new ArrayList<Double>();
				ArrayList<Double> latt = new ArrayList<Double>();
				String[] content = index.split("::");
				for (String element : content) {
					if (element.contains("subnode=")) {
						String[] data = element.substring(8).split(",");
						latt.add(Double.parseDouble(data[1]) * 1000);
						longi.add(Double.parseDouble(data[2]) * 1000);
						latt.toArray(new Double[latt.size()]);
						
						// Transform arralist to array to be able to create generalPath
						int[] lattitude = new int[latt.size()];    
						Iterator<Double> iterator = latt.iterator(); 
						for (int i = 0; i < lattitude.length; i++) {

							lattitude[i] = iterator.next().intValue();
						}
						
						
						// Transform arralist to array to be able to create generalPath	
						int[] longitude = new int[longi.size()];
						Iterator<Double> iteratorLongi = longi.iterator();
						for (int i = 0; i < longitude.length; i++) {

							longitude[i] = iteratorLongi.next().intValue();

						}
						
						shape = create(longitude, lattitude);

					}
				}

			}
		} catch (FileNotFoundException | XMLStreamException e) {
			System.out.println(e);
		}

	}

	protected Shape create(int[] longitude, int[] lattitude) {
		GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, longitude.length);
		
		path.moveTo(longitude[0], lattitude[0]); //Starting point 
		for (int i = 1; i < longitude.length; i++) {
			path.lineTo(longitude[i], lattitude[i]);
		}
		
		return path;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setPaint(Color.black);
		g2.draw(shape);
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("Demo");
		ShapeWays tl = new ShapeWays();
		jf.add(tl);
		jf.setSize(1200, 1200);
		jf.setVisible(true);

	}

}
