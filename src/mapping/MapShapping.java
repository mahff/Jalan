package mapping;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;

import graphic.SearchArea;
import parsing.JALDocument;

public class MapShapping {
	/**
	 * 
	 */
	BufferedWriter writer;
	JALDocument document;
	ArrayList<String> meta = new ArrayList<String>();
	double maxLon;
	double maxLat;

	public MapShapping() {

		try {

			writer = new BufferedWriter(new FileWriter("data/svg/singapore.svg"));
			document = new JALDocument("data/maps/singapore.jal");
			meta = document.getElementsDataByType("meta");
			maxLon = Double.parseDouble(meta.get(1).split("::")[1].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[3].substring(7));
			maxLat = Double.parseDouble(meta.get(1).split("::")[2].substring(7))
					- Double.parseDouble(meta.get(1).split("::")[4].substring(7));
			writer.write(
					"<svg style=\"background-color: #95c8db\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink= \"http://www.w3.org/1999/xlink\" width=\""
							+ maxLon * 2000 + "\" height=\"" + maxLat * 2000 + "\">\n");
			shapeArea();
			shapeWays();
			shapeIsland();

			if (!(SearchArea.departureField.getSelectedItem().toString().equals(""))
					&& !(SearchArea.arrivalField.getSelectedItem().toString().equals(""))) {
				if (SearchArea.departureField.getSelectedItem().toString().contains("::")
						&& (SearchArea.arrivalField.getSelectedItem().toString().contains("::"))) {
					writer.write("<image xlink:href=\"departure.svg\" x=\""
							+ SearchArea.splitSearchData(SearchArea.departureField.getSelectedItem().toString(), 1)
							+ "\" y=\""
							+ SearchArea.splitSearchData(SearchArea.departureField.getSelectedItem().toString(), 2)
							+ "\" height=\"50px\" width=\"50px\"/>");
					writer.write("<image xlink:href=\"arrival.svg\" x=\""
							+ SearchArea.splitSearchData(SearchArea.arrivalField.getSelectedItem().toString(), 1)
							+ "\" y=\""
							+ SearchArea.splitSearchData(SearchArea.arrivalField.getSelectedItem().toString(), 2)
							+ "\" height=\"50px\" width=\"50px\"/>");
				}

				else {

					System.out.println("I'm herrreerereeeree");
					writer.write("<image xlink:href=\"arrival.svg\" x=\""
							+ Double.parseDouble(SearchArea.getCoordByKey(SearchArea.departureField, 1)) * 2000
							+ "\" y=\""
							+ ((Double.parseDouble(SearchArea.getCoordByKey(SearchArea.departureField, 2)) * 2000))
							+ "\" height=\"50px\" width=\"50px\"/>");
					writer.write("<image xlink:href=\"departure.svg\" x=\""
							+ Double.parseDouble(SearchArea.getCoordByKey(SearchArea.arrivalField, 1)) * 2000
							+ "\" y=\""
							+ ((Double.parseDouble(SearchArea.getCoordByKey(SearchArea.arrivalField, 2)) * 2000))
							+ "\" height=\"50px\" width=\"50px\"/>");
				}

			}

			writer.write("</svg>");
			writer.close();
		} catch (XMLStreamException | IOException e) {
			e.printStackTrace();
		}

	}

	public void shapeArea() {

		try {
			for (String area : document.getElementsDataByType("area")) {

				if (area.indexOf("building=hospital") != -1) {
					writer.write("\t<polygon style=\"fill: #995252; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=commercial") != -1) {
					writer.write("\t<polygon  style=\"fill: #529952; stroke: none; stroke-width: 0.1\"  points=\"");
					for (String data : area.split("::")) {
						if (data.startsWith("subnode=")) {

							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (area.indexOf("building=hotel") != -1) {
					writer.write("\t<polygon  style=\"fill: #525299; stroke: none; stroke-width: 0.1\"  points=\"");
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

	public void shapeWays() {
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
					writer.write("\t<polyline  style=\"fill: none; stroke: #00DEDE; stroke-width: 0.7\"  points=\"");
					for (String data : way.split("::")) {
						if (data.startsWith("subnode=")) {
							writer.write(Double.parseDouble(data.split(",")[2]) * 2000 + ","
									+ (maxLat - Double.parseDouble(data.split(",")[1])) * 2000 + " ");
						}
					}
					writer.write("\" />\n");
				}
				if (way.indexOf("road=tertiary") != -1) {
					writer.write("\t<polyline  style=\"fill: none; stroke: #9E0052; stroke-width: 0.4\"  points=\"");
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

}
