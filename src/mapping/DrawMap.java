package parsing;

import java.awt.Shape;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

import parsing.JALDocument;

public class ShapeWays extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Shape shape;

	public ShapeWays() {
		try {
			generateMap();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateMap() throws IOException, XMLStreamException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("singapore.svg"));

		JALDocument document = new JALDocument("singapore.jal");
		ArrayList<String> meta = document.getElementsDataByType("meta");
		double maxLon = Double.parseDouble(meta.get(1).split("::")[1].substring(7))
				- Double.parseDouble(meta.get(1).split("::")[3].substring(7));
		double maxLat = Double.parseDouble(meta.get(1).split("::")[2].substring(7))
				- Double.parseDouble(meta.get(1).split("::")[4].substring(7));
		writer.write("<svg style=\"background-color: #D4EFEF\" xmlns=\"http://www.w3.org/2000/svg\" width=\""
				+ maxLon * 2000 + "\" height=\"" + maxLat * 2000 + "\">\n");
		shapeArea(document, maxLon, maxLat, writer); 
		shapeWays(document, maxLon, maxLat, writer);
		shapeIsland(document, maxLon, maxLat, writer);

		writer.write("</svg>");
		writer.close();
	}

	public static void shapeArea(JALDocument document, double maxLon, double maxLat, BufferedWriter writer)
			throws IOException, NumberFormatException, XMLStreamException {

		for (String area : document.getElementsDataByType("area")) {
			if (area.indexOf("building=default") != -1) {
				writer.write("\t<polygon style=\"fill: #999; stroke: none; stroke-width: 0.1\" points=\"");
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {

						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}

				writer.write("\" />\n");
			}
			if (area.indexOf("building=hospital") != -1) {
				writer.write("\t<polygon style=\"fill: #995252; stroke: none; stroke-width: 0.1\" points=\"");
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
			if (area.indexOf("building=commercial") != -1) {
				writer.write("\t<polygon style=\"fill: #529952; stroke: none; stroke-width: 0.1\" points=\"");
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {

						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
			if (area.indexOf("building=hotel") != -1) {
				writer.write("\t<polygon style=\"fill: #525299; stroke: none; stroke-width: 0.1\" points=\"");
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
			if (area.indexOf("landuse") != -1) {
				writer.write("\t<polygon style=\"fill: #ffcccc; stroke: none; stroke-width: 0.1\" points=\"");
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
		}

	}

	public static void shapeWays(JALDocument document, double maxLon, double maxLat, BufferedWriter writer)
			throws IOException, NumberFormatException, XMLStreamException {
		for (String way : document.getElementsDataByType("way")) {
			if (way.indexOf("road=motorway") != -1) {
				writer.write("\t<polyline style=\"fill: none; stroke: #009405; stroke-width: 1\" points=\"");
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}

			if (way.indexOf("road=primary") != -1) {
				writer.write("\t<polyline style=\"fill: none; stroke: #00C49A; stroke-width: 0.8\" points=\"");
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
			if (way.indexOf("road=secondary") != -1) {
				writer.write("\t<polyline style=\"fill: none; stroke: #00DEDE; stroke-width: 0.7\" points=\"");
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}
				writer.write("\" />\n");
			}
			if (way.indexOf("road=tertiary") != -1) {
				writer.write("\t<polyline style=\"fill: none; stroke: #9E0052; stroke-width: 0.4\" points=\"");
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {

						if (data.split(",").length == 3)
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}

				writer.write("\" />\n");
			}
		}

	}

	public static void shapeIsland(JALDocument document, double maxLon, double maxLat, BufferedWriter writer)
			throws IOException, NumberFormatException, XMLStreamException {
		for (String island : document.getElementsDataByType("island")) {
			writer.write("\t<polygon style=\"fill: #eee; stroke: none; stroke-width: 0.1\" points=\"");
			for (String data : island.split("::")) {
				if (data.startsWith("subnode=")) {

					writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
							+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
				}
			}

			writer.write("\"/>\n");
		}

	}

	public static void main(String[] args) {
		new ShapeWays();

	}

}
