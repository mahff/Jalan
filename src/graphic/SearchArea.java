package graphic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import parsing.CSVDocument;

public class SearchArea {

	private static JComboBox<String> departureField = new JComboBox<String>();
	private static JComboBox<String> arrivalField = new JComboBox<String>();
	private static CSVDocument document;


	public SearchArea() throws IOException {
		departureField.setEditable(true);
		arrivalField.setEditable(true);
		document = new CSVDocument("singapore.sug");

	}

	public static Component SearchFrame() {
		Container pane = new Container();
		pane.setLayout(new GridLayout(0, 1));
		JLabel departure = new JLabel("Departure : ");
		JLabel arrival = new JLabel("Arrival : ");
		
		ActionListener suggestionDeparture = new ActionListener() {
			Pattern pattern = Pattern.compile((String) departureField.getEditor().getItem());
			
			public void actionPerformed(ActionEvent e) {
				for (String key : document.getNodes().keySet()) {
					Matcher matcher = pattern.matcher(key);
					if (matcher.matches() == true) {
						departureField.addItem(key);
					}
			
			
				}
			}
		};
		ActionListener suggestionArrival = new ActionListener() {
			Pattern pattern = Pattern.compile((String) arrivalField.getEditor().getItem());
			
			public void actionPerformed(ActionEvent e) {
				for (String key : document.getNodes().keySet()) {
					Matcher matcher = pattern.matcher(key);
					if (matcher.matches() == true) {
						departureField.addItem(key);
					}
			
			
				}
			}
		};
		
		departureField.addActionListener(suggestionDeparture);
		arrivalField.addActionListener(suggestionArrival);
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
