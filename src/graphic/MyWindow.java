package graphic;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;

import org.apache.batik.swing.JSVGCanvas;

public class MyWindow {

	static JSVGCanvas image = new JSVGCanvas();

	public MyWindow() {
		JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, MyWindow.SearchArea(), MyWindow.OptionArea());
		JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, MyWindow.suggestionSummary());
		sp.setDividerLocation(80);
		sp2.setDividerLocation(275);
		JFrame frame = new JFrame();
		
		frame.add(sp2);
		frame.setSize(800, 700);
		frame.setVisible(true);

	}

	public static Component SearchArea() {
		Container pane = new Container();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel departure = new JLabel("Departure : ");
		JLabel arrival = new JLabel("    Arrival    : ");
		JButton okButton = new JButton(" Search ");
		JTextField departureField = new JTextField(60), arrivalField = new JTextField(60);
		pane.add(departure);
		pane.add(departureField);
		pane.add(arrival);
		pane.add(arrivalField);
		pane.add(okButton);

		return pane;
	}

	public static Component OptionArea() {

		Box topBox = Box.createVerticalBox();
		JCheckBox marching = new JCheckBox("Marching");
		JCheckBox bicyling = new JCheckBox("Bicycling");
		JCheckBox car = new JCheckBox("Car");
		JCheckBox ferry = new JCheckBox("Ferry");
		JCheckBox metro = new JCheckBox("Metro");
		JCheckBox bus = new JCheckBox("Bus");
		SpinnerModel model1 = new SpinnerDateModel();
		JSpinner spinner1 = new JSpinner(model1);
		topBox.add(marching);
		topBox.add(bicyling);
		topBox.add(ferry);
		topBox.add(car);
		topBox.add(metro);
		topBox.add(bus);
		topBox.add(spinner1);
		
		topBox.setBorder(BorderFactory.createTitledBorder("Option"));
	
		

		return topBox;
	}
	
	public static Component suggestionSummary() {
		int minimum = 0;
		int maximum = 4;
		int initValue = 1 ;
		
		JSVGCanvas image = new JSVGCanvas();
		image.setURI(new File("SVG_logo.svg").toURI().toString());
		
		JLabel suggestion = new JLabel();
		JLabel summary = new JLabel();
		JPanel map = new JPanel(); 
		JSlider slider = new JSlider(minimum, maximum, initValue);
		
		
		slider.setLayout(new FlowLayout(FlowLayout.CENTER) );
		slider.setSize(100,100);
		map.add(image);
		map.add(slider);
		
		suggestion.setText("Suggestion");
		summary.setText("Summary");
		map.setToolTipText("Map");
		
		JSplitPane sumSug = new JSplitPane(JSplitPane.VERTICAL_SPLIT,suggestion , summary);
		JSplitPane splitMap = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sumSug , map);
		splitMap.setDividerLocation(300);
		sumSug.setDividerLocation(200);
		return splitMap; 
	}
	
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MyWindow();
			}
		});
	}
}