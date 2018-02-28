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

		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SuggestionSummary.summary.setText("<html> Departure : " + SearchArea.departureField.getText() + "<br/> " + "Arrival :"
						+ SearchArea.arrivalField.getText() + "<br/> </html>");

			}

		};
		okButton.addActionListener(actionListener);

		topBox.setBorder(BorderFactory.createTitledBorder("Option"));
		topBox.add(okButton);
		return topBox;
	}
}