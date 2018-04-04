package mapping;

import java.io.BufferedWriter;

import parsing.JALDocument;

public interface ShapeMap {

	public void generateMap();

	public void shapeArea(JALDocument document, double maxLon, double maxLat, BufferedWriter writer);

	public void shapeWays(JALDocument document, double maxLon, double maxLat, BufferedWriter writer);

	public void shapeIsland(JALDocument document, double maxLon, double maxLat, BufferedWriter writer);
}
