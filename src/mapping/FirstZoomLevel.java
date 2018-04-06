package mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

import graphic.SearchArea;
import parsing.JALDocument;

public class FirstZoomLevel extends JPanel implements ShapeMap {
	BufferedWriter writer;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FirstZoomLevel() {

		generateMap();

	}

	public void generateMap() {

		try {
			writer = new BufferedWriter(new FileWriter("singapore.svg"));
			JALDocument document;
			document = new JALDocument("singapore.jal");
			ArrayList<String> meta = document.getElementsDataByType("meta");
			double maxLon = Double.parseDouble(meta.get(1).split("::")[1].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[3].substring(7));
			double maxLat = Double.parseDouble(meta.get(1).split("::")[2].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[4].substring(7));
			writer.write(
					"<svg style=\"background-color: #D4EFEF\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink= \"http://www.w3.org/1999/xlink\" width=\""
							+ maxLon * 2000 + "\" height=\"" + maxLat * 2000 + "\">\n");
			shapeArea(document, maxLon, maxLat, writer);
			shapeWays(document, maxLon, maxLat, writer);
			shapeIsland(document, maxLon, maxLat, writer);
			writer.write("<text x=\"325\" y=\"225\"\r\n" + "style=\"fill: RED; stroke: none; font-size: 48;\">\n"
					+ "    SINGAPORE\n" + "</text>");
			if (!(SearchArea.departureField.getText().isEmpty()) && !(SearchArea.arrivalField.getText().isEmpty())) {

				writer.write(
						"<image xlink:href=\"locale.svg\" x=\"" + SearchArea.splitSearchData(SearchArea.departureField.getText(), 1)
								+ "\" y=\"" + SearchArea.splitSearchData(SearchArea.departureField.getText(), 2) + "\" height=\"50px\" width=\"50px\"/>");
				writer.write("<image xlink:href=\"locale.svg\" x=\"" + SearchArea.splitSearchData(SearchArea.arrivalField.getText(), 1)
						+ "\" y=\"" + SearchArea.splitSearchData(SearchArea.arrivalField.getText(), 2) + "\" height=\"50px\" width=\"50px\"/>");
			}

			
				

			writer.write("</svg>");

			writer.close();
		} catch (XMLStreamException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void shapeArea(JALDocument document, double maxLon, double maxLat, BufferedWriter writer) {

		try {
			for (String area : document.getElementsDataByType("area")) {
				if (area.indexOf("building=default") != -1) {
					writer.write(
							"\t<polygon display =\"none\" style=\"fill: #999; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {

							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}

					writer.write("\" />\n");
				}
				if (area.indexOf("building=hospital") != -1) {
					writer.write(
							"\t<polygon display =\"none\" style=\"fill: #995252; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=commercial") != -1) {
					writer.write(
							"\t<polygon display =\"none\" style=\"fill: #529952; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {

							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=hotel") != -1) {
					writer.write(
							"\t<polygon display =\"none\"  style=\"fill: #525299; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("landuse") != -1) {
					writer.write("\t<polygon style=\"fill: #ffcccc; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
			}
		} catch (NumberFormatException | XMLStreamException | IOException e) {
			e.printStackTrace();
		}

	}

	public void shapeWays(JALDocument document, double maxLon, double maxLat, BufferedWriter writer) {
		try {
			for (String way : document.getElementsDataByType("way")) {
				if (way.indexOf("road=motorway") != -1) {
					writer.write("\t<polyline style=\"fill: none; stroke: #009405; stroke-width: 1\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}

				if (way.indexOf("road=primary") != -1) {
					writer.write("\t<polyline style=\"fill: none; stroke: #00C49A; stroke-width: 0.8\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (way.indexOf("road=secondary") != -1) {
					writer.write(
							"\t<polyline display =\"none\"  style=\"fill: none; stroke: #00DEDE; stroke-width: 0.7\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (way.indexOf("road=tertiary") != -1) {
					writer.write(
							"\t<polyline display =\"none\"  style=\"fill: none; stroke: #9E0052; stroke-width: 0.4\"  points=\"");
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
		} catch (NumberFormatException | XMLStreamException | IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void shapeIsland(JALDocument document, double maxLon, double maxLat, BufferedWriter writer) {
		try {
			for (String island : document.getElementsDataByType("island")) {
				writer.write("\t<polygon style=\"fill: #eee; stroke: none; stroke-width: 0.1\"  points=\"");
				for (String data : island.split("::")) {
					if (data.startsWith("subnode=")) {

						writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
								+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
					}
				}

				writer.write("\"/>\n");
			}
		} catch (NumberFormatException | XMLStreamException | IOException e) {
			e.printStackTrace();
		}
	}

	
}
