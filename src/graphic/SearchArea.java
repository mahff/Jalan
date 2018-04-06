package graphic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchArea {

	public static JTextField departureField = new JTextField(60);
	public static JTextField arrivalField = new JTextField(60);

	public SearchArea() {
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
	public static String splitSearchData(String textField, int index) {
		String temp = ""; 
		String[] splitData = textField.split("::");
		String longi = splitData[0];
		String latt = splitData[1];
		switch(index) {
		case 1 : temp =  longi; System.out.println("longi : "+temp); break;  
		case 2 : temp =  latt; System.out.println("latt : "+temp); break; 
		}
		return temp;
	}
}
