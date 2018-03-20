package parsing;

import java.awt.Shape;
import java.io.IOException;
import javax.swing.JPanel;
import javax.xml.stream.XMLStreamException;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String generateMap() throws XMLStreamException, IOException {
		String file = "";

		JALDocument document = new JALDocument("/home/mahff/Téléchargements/singapore (1).jal");
		// file += "<svg width=\"800\"
		// height=\"800\"xmlns=\"http://www.w3.org/2000/svg\">\n";
		file +="<svg style=\"background-color: #D4EFEF\" xmlns=\"http://www.w3.org/2000/svg\">\n";

		for (String island : document.getElementsDataByType("island")) {
			file +="\t<polygon style=\"fill: #eee; stroke: none; stroke-width: 0.1\" points=\"";
			for (String data : island.split("::")) {
				if (data.startsWith("subnode=")) {
					// file +=
					// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
					// ";
					file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
							+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
				}
			}
			// file += "\" />\n";
			file +="\" />\n";
		}

		for (String area : document.getElementsDataByType("area")) {
			if (area.indexOf("building=default") != -1) {
				file +="\t<polygon style=\"fill: #999; stroke: none; stroke-width: 0.1\" points=\"";
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (area.indexOf("building=hospital") != -1) {
				file +="\t<polygon style=\"fill: #995252; stroke: none; stroke-width: 0.1\" points=\"";
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (area.indexOf("building=commercial") != -1) {
				file +="\t<polygon style=\"fill: #529952; stroke: none; stroke-width: 0.1\" points=\"";
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (area.indexOf("building=hotel") != -1) {
				file +="\t<polygon style=\"fill: #525299; stroke: none; stroke-width: 0.1\" points=\"";
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (area.indexOf("landuse") != -1) {
				file +="\t<polygon style=\"fill: #ffcccc; stroke: none; stroke-width: 0.1\" points=\"";
				for (String data : area.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
		}

		for (String way : document.getElementsDataByType("way")) {
			// file += "\t<polyline fill=\"none\" stroke=\"black\" points=\"";
			if (way.indexOf("road=motorway") != -1) {
				file +="\t<polyline style=\"fill: none; stroke: #009405; stroke-width: 1\" points=\"";
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (way.indexOf("road=primary") != -1) {
				file +="\t<polyline style=\"fill: none; stroke: #00C49A; stroke-width: 0.8\" points=\"";
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (way.indexOf("road=secondary") != -1) {
				file +="\t<polyline style=\"fill: none; stroke: #00DEDE; stroke-width: 0.7\" points=\"";
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";
						file +=Double.parseDouble(data.split(",")[2]) * 5000 + ","
								+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file +="\" />\n";
			}
			if (way.indexOf("road=tertiary") != -1) {
				file += "\t<polyline style=\"fill: none; stroke: #9E0052; stroke-width: 0.4\" points=\"";
				for (String data : way.split("::")) {
					if (data.startsWith("subnode=")) {
						// file +=
						// Double.parseDouble(data.split(",")[1])*100+","+Double.parseDouble(data.split(",")[2])*100+"
						// ";

						if (data.length() == 3)
							file += Double.parseDouble(data.split(",")[2]) * 5000 + ","
									+ Double.parseDouble(data.split(",")[1]) * 5000 + " ";
					}
				}
				// file += "\" />\n";
				file += "\" />\n";
			}
		}

		// file += "</svg>";
		file +="</svg>";
		return file;
	}


}
