package graphic;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import parsing.CSVDocument;

public class SearchArea {
	public static JComboBox<String> departureField = new JComboBox<String>();
	public static JTextField departure = new JTextField();
	public static JTextField arrival = new JTextField();
	public static JComboBox<String> arrivalField = new JComboBox<String>();
	public static CSVDocument document;

	public static Component SearchFrame() {
		try {
			document = new CSVDocument("data/indexation/singapore.sug");
		} catch (IOException e) {
			e.printStackTrace();
		}
		departureField = new JComboBox<String>();
		departureField.setEditable(true);
		departureField.setSelectedIndex(-1);

		JTextField departure = (JTextField) departureField.getEditor().getEditorComponent();
		departure.setText("");

		arrivalField = new JComboBox<String>();
		arrivalField.setEditable(true);
		arrivalField.setSelectedIndex(-1);
		departureField.addItem("");
		arrivalField.addItem("");
		for (String key : document.getNodes().keySet()) {
			departureField.addItem(key);
			arrivalField.addItem(key);
		}

		JTextField arrival = (JTextField) arrivalField.getEditor().getEditorComponent();
		arrival.setText("");

		Container pane = new Container();
		pane.setLayout(new GridLayout(0, 1));
		JLabel departureLabel = new JLabel("Departure : ");
		JLabel arrivalLabel = new JLabel("Arrival : ");

		pane.add(departureLabel);
		pane.add(departureField);
		pane.add(arrivalLabel);
		pane.add(arrivalField);

		return pane;
	}
	
	public static String getCoordByKey(JComboBox<String> field, int index) {
		String temp = "";
		String longi = "";
		String latt = "";
		String dataSplit[] = new String[3];
		for (String key : document.getNodes().keySet()) {
			if (field.getSelectedItem().equals(key)) {
				temp = document.getNodes().get(key);
				dataSplit = temp.split(",");
				if (dataSplit.length >= 3) {
					longi = dataSplit[1];
					latt = dataSplit[2];
				}
				if (dataSplit.length == 2) {
					longi = dataSplit[0];
					latt = dataSplit[1];
				}
			}
		}
		switch (index) {
		case 1:
			temp = longi;
			System.out.println("longi : " + temp);
			break;
		case 2:
			temp = latt;
			System.out.println("longi : " + temp);
			break;
		}
		return temp;
	}

	public static String splitSearchData(String textField, int index) {
		String temp = "";
		String[] splitData = textField.split("::");
		String longi = splitData[0];
		String latt = splitData[1];
		switch (index) {
		case 1:
			temp = longi;
			break;
		case 2:
			temp = latt;
			break;
		}
		return temp;
	}
}
