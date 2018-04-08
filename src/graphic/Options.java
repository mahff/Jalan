package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import org.apache.batik.swing.JSVGCanvas;

import mapping.MapShapping;

public class Options {
	public static JButton okButton = new JButton(" Search ");
	static JSVGCanvas image = new JSVGCanvas();

	public static Component OptionArea() {
		Box topBox = Box.createHorizontalBox();
		final JCheckBox walking = new JCheckBox("Walking");
		final JCheckBox bicyling = new JCheckBox("Bicycling");
		final JCheckBox car = new JCheckBox("Car");
		final JCheckBox ferry = new JCheckBox("Ferry");
		final JCheckBox metro = new JCheckBox("Metro");
		final JCheckBox bus = new JCheckBox("Bus");
		final SpinnerModel model1 = new SpinnerDateModel();
		JSpinner spinner1 = new JSpinner(model1);
		topBox.add(walking);
		topBox.add(bicyling);
		topBox.add(ferry);
		topBox.add(car);
		topBox.add(metro);
		topBox.add(bus);
		topBox.add(spinner1);
		new MapShapping();

		image.setURI(new File("data/svg/singapore.svg").toURI().toString());
		image.setAutoscrolls(true);

		ActionListener searchListener = new ActionListener() {
			private String vehicle = "";
			private String searchinfo = "";

			public void actionPerformed(ActionEvent e) {

				if (walking.isSelected() == true) {
					if (!(vehicle.contains("Marching ")))
						vehicle += "Marching ";
					setSearchinfo(getSearchinfo() + "marching,");
				}
				if (bicyling.isSelected() == true) {
					vehicle += "Bicycle ";
					setSearchinfo(getSearchinfo() + "bicycling,");
				}
				if (ferry.isSelected() == true) {
					if (!(vehicle.contains("Ferry ")))
						vehicle += "Ferry ";
					setSearchinfo(getSearchinfo() + "ferry,");
				}
				if (car.isSelected() == true) {
					if (!(vehicle.contains("Car ")))
						vehicle += "Car ";
					setSearchinfo(getSearchinfo() + "car,");
				}
				if (metro.isSelected() == true) {
					if (!(vehicle.contains("Metro ")))
						vehicle += "Metro ";
					setSearchinfo(getSearchinfo() + "metro,");
				}
				if (bus.isSelected() == true) {
					if (!(vehicle.contains("Bus ")))
						vehicle += "Bus ";
					setSearchinfo(getSearchinfo() + "bus,");
				}
				SuggestionSummary.summary.setText("<html> Departure : " + SearchArea.departureField.getSelectedItem()
						+ "<br/> <br/> " + "Arrival :" + SearchArea.arrivalField.getSelectedItem() + "<br/> <br/> "
						+ "By : " + vehicle + "<br/> <br/>  " + model1.getValue() + "<br/> <br/> " + "</html>");
				new MapShapping();
				SearchArea.getCoordByKey(1);
				SearchArea.getCoordByKey(2); 
				
				image.setURI(new File("data/svg/singapore.svg").toURI().toString());
				image.setAutoscrolls(true);
			}

			public String getSearchinfo() {
				return searchinfo;
			}

			public void setSearchinfo(String searchinfo) {
				this.searchinfo = searchinfo;
			}

		};
		okButton.addActionListener(searchListener);

		topBox.setBorder(BorderFactory.createTitledBorder("Option"));
		topBox.add(okButton);
		return topBox;
	}

	public static JSVGCanvas returnImage() {

		return image;
	}

}
