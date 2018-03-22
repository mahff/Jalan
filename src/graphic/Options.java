package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

public class Options {

	public static Component OptionArea() {
		JButton okButton = new JButton(" Search ");
		Box topBox = Box.createHorizontalBox();
		final JCheckBox marching = new JCheckBox("Marching");
		final JCheckBox bicyling = new JCheckBox("Bicycling");
		final JCheckBox car = new JCheckBox("Car");
		final JCheckBox ferry = new JCheckBox("Ferry");
		final JCheckBox metro = new JCheckBox("Metro");
		final JCheckBox bus = new JCheckBox("Bus");
		final SpinnerModel model1 = new SpinnerDateModel();
		JSpinner spinner1 = new JSpinner(model1);
		topBox.add(marching);
		topBox.add(bicyling);
		topBox.add(ferry);
		topBox.add(car);
		topBox.add(metro);
		topBox.add(bus);
		topBox.add(spinner1);

		ActionListener searchListener = new ActionListener() {
			private String vehicle = "";
			private String searchinfo = "";

			public void actionPerformed(ActionEvent e) {
				if (marching.isSelected() == true) {
					vehicle += "Marching ";
					setSearchinfo(getSearchinfo() + "marching,");
				}
				if (bicyling.isSelected() == true) {
					vehicle += "Bicycle ";
					setSearchinfo(getSearchinfo() + "bicycling,");
				}
				if (ferry.isSelected() == true) {
					vehicle += "Ferry ";
					setSearchinfo(getSearchinfo() + "ferry,");
				}
				if (car.isSelected() == true) {
					vehicle += "Car ";
					setSearchinfo(getSearchinfo() + "car,");
				}
				if (metro.isSelected() == true) {
					vehicle += "Metro ";
					setSearchinfo(getSearchinfo() + "metro,");
				}
				if (bus.isSelected() == true) {
					vehicle += "Bus ";
					setSearchinfo(getSearchinfo() + "bus,");
				}
				SuggestionSummary.summary.setText("<html> Departure : " + SearchArea.departureField.getText()
						+ "<br/> <br/> " + "Arrival :" + SearchArea.arrivalField.getText() + "<br/> <br/> " + "By : "
						+ vehicle + "<br/> <br/>  " + model1.getValue() + "<br/> <br/> " + "</html>");
				setSearchinfo(getSearchinfo() + SearchArea.departureField.getText() + ","
						+ SearchArea.arrivalField.getText() + ",");

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
}
