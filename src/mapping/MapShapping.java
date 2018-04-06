package mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

import graphic.SearchArea;
import parsing.JALDocument;

public class MapShapping extends JPanel {
	/**
	 * 
	 */
	static String zoomLevel = ""; 
	private static final long serialVersionUID = 1L;
	BufferedWriter writer;
	JALDocument document;
	ArrayList<String> meta = new ArrayList<String>();
	double maxLon;
	double maxLat;

	public MapShapping(String zoom) {
		
		try {
			
			writer = new BufferedWriter(new FileWriter("singapore.svg"));
			document = new JALDocument("singapore.jal");
			meta = document.getElementsDataByType("meta");
			maxLon = Double.parseDouble(meta.get(1).split("::")[1].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[3].substring(7));
			maxLat = Double.parseDouble(meta.get(1).split("::")[2].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[4].substring(7));
			writer.write(
					"<svg style=\"background-color: #D4EFEF\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink= \"http://www.w3.org/1999/xlink\" width=\""
							+ maxLon * 2000 + "\" height=\"" + maxLat * 2000 + "\">\n");
			
			if (zoom == "first") {
				shapeArea("display = \"none\"" ,"display = \"none\"" );
				shapeWays("display = \"none\"", "display = \"none\"");
				writer.write("<text x=\"325\" y=\"225\"\r\n" + "style=\"fill: RED; stroke: none; font-size: 48;\">\n"
						+ "    SINGAPORE\n" + "</text>");
			}
			if (zoom == "second") {
				shapeArea("","display = \"none\"");
				shapeWays("", "display = \"none\"");
			}
			if (zoom == "third") {
				shapeArea("","");
				shapeWays("","");
			}
			shapeIsland();
			if (!(SearchArea.departure.getText().isEmpty()) && !(SearchArea.arrival.getText().isEmpty())) {

				writer.write("<image xlink:href=\"locale.svg\" x=\""
						+ SearchArea.splitSearchData(SearchArea.departure.getText(), 1) + "\" y=\""
						+ SearchArea.splitSearchData(SearchArea.departure.getText(), 2)
						+ "\" height=\"50px\" width=\"50px\"/>");
				writer.write("<image xlink:href=\"locale.svg\" x=\""
						+ SearchArea.splitSearchData(SearchArea.arrival.getText(), 1) + "\" y=\""
						+ SearchArea.splitSearchData(SearchArea.arrival.getText(), 2)
						+ "\" height=\"50px\" width=\"50px\"/>");
			}
			
			
			writer.write("</svg>");
			writer.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}

	}

	public void shapeArea(String zoom2, String zoom3) {

		try {
			for (String area : document.getElementsDataByType("area")) {
				if (area.indexOf("building=default") != -1) {
					writer.write("\t<polygon " + zoom3
							+ " style=\"fill: #999; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {

							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}

					writer.write("\" />\n");
				}
				if (area.indexOf("building=hospital") != -1) {
					writer.write("\t<polygon " + zoom2
							+ " style=\"fill: #995252; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=commercial") != -1) {
					writer.write("\t<polygon " + zoom2
							+ " style=\"fill: #529952; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {

							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=hotel") != -1) {
					writer.write("\t<polygon " + zoom2
							+ "  style=\"fill: #525299; stroke: none; stroke-width: 0.1\"  points=\"");
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

	public void shapeWays(String zoom2, String zoom3) {
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
					writer.write("\t<polyline  style=\"fill: none; stroke: #00C49A; stroke-width: 0.8\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (way.indexOf("road=secondary") != -1) {
					writer.write("\t<polyline " + zoom2
							+ "  style=\"fill: none; stroke: #00DEDE; stroke-width: 0.7\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (way.indexOf("road=tertiary") != -1) {
					writer.write("\t<polyline " + zoom3
							+ "  style=\"fill: none; stroke: #9E0052; stroke-width: 0.4\"  points=\"");
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

	public void shapeIsland() {
		try {
			for (String island : document.getElementsDataByType("island")) {
				writer.write("\t<polygon  style=\"fill: #eee; stroke: none; stroke-width: 0.1\"  points=\"");
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

	public static String getZoomLevel() {
		System.out.println(zoomLevel);
		return zoomLevel; 
		
	}

}