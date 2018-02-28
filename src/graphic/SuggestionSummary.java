package graphic;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;

import org.apache.batik.swing.JSVGCanvas;

public class SuggestionSummary {
	
	static JSVGCanvas image = new JSVGCanvas();
	static JLabel suggestion = new JLabel();
	static JLabel summary = new JLabel();
	
	public static Component SuggestionSummaryFrame() {
		int minimum = 0;
		int maximum = 4;
		int initValue = 1;

		JSVGCanvas image = new JSVGCanvas();
		image.setURI(new File("SVG_logo.svg").toURI().toString());

		
		JPanel map = new JPanel();
		JSlider slider = new JSlider(minimum, maximum, initValue);

		slider.setLayout(new FlowLayout(FlowLayout.CENTER));
		slider.setSize(100, 100);
		map.add(image);
		map.add(slider);

		suggestion.setText("Suggestion");
		summary.setText("Summary");
		map.setToolTipText("Map");

		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT, suggestion, summary);
		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sumSug, map);
		splitMap.setDividerLocation(300);
		sumSug.setDividerLocation(200);
		return splitMap;
	}

}
