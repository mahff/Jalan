package program;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import graphic.Options;
import graphic.SearchArea;
import graphic.SuggestionSummary;
import route.Route;
import threading.PathFinder;

public class Jalan extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public  Jalan() {
    	JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, SearchArea.SearchFrame(), Options.OptionArea());
		JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, SuggestionSummary.SuggestionSummaryFrame());
		sp.setDividerLocation(120);
		sp2.setDividerLocation(200);
		JFrame frame = new JFrame();
		frame.add(sp2);
		frame.setSize(1312,883);
		frame.setVisible(true);
	}
    
    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run() {
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						
						e.printStackTrace();
					}
				new Jalan();
			}
		});
	}

}
