package graphic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchArea {

	static JTextField departureField = new JTextField(60), arrivalField = new JTextField(60);
	
	public SearchArea () {
	}

	public static Component SearchFrame() {
		Container pane = new Container();
		pane.setLayout(new GridLayout(0, 1));
		JLabel departure = new JLabel("Departure : ");
		JLabel arrival = new JLabel("Arrival : ");
		
		pane.add(departure);
		pane.add(departureField);
		pane.add(arrival);
		pane.add(arrivalField);

		return pane;
	}
}
