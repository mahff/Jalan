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
import javax.swing.JTextField;

import parsing.CSVDocument;

public class SearchArea {

	public static JComboBox<String> departureField = new JComboBox<String>();
	public static JTextField departure = new JTextField(); 
	public static JTextField arrival = new JTextField();
	public static JComboBox<String> arrivalField = new JComboBox<String>();
	private static CSVDocument document;


	public SearchArea() throws IOException {
		departureField.setEditable(true);
		arrivalField.setEditable(true);
		document = new CSVDocument("data/indexation/singapore.sug");

	}

	public static Component SearchFrame() {
		departureField = new JComboBox<String>();
		departureField.setEditable(true);
		departureField.setSelectedIndex(-1);
        JTextField departure = (JTextField) departureField.getEditor().getEditorComponent();
        departure.setText("");
        //departure.addKeyListener(new ComboKeyHandler(departureField));
        
        arrivalField = new JComboBox<String>();
        arrivalField.setEditable(true);
        arrivalField.setSelectedIndex(-1);
        JTextField arrival = (JTextField) arrivalField.getEditor().getEditorComponent();
        arrival.setText("");
        //arrival.addKeyListener(new ComboKeyHandler(arrivalField));
        
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