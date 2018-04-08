package program;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import graphic.Options;
import graphic.SearchArea;
import graphic.SuggestionSummary;

public class Jalan extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public  Jalan() {
    	JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, SearchArea.SearchFrame(), Options.OptionArea());
		JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, SuggestionSummary.SuggestionSummaryFrame());
		sp.setDividerLocation(120);
		sp2.setDividerLocation(200);
		sp.setEnabled(false); 
		sp2.setEnabled(false);
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.add(sp2);
		frame.setSize(1312,883);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});
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
