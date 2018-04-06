package graphic;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import org.apache.batik.swing.JSVGCanvas;

import mapping.MapShapping;
import route.Route;
import threading.PathFinder;
import threading.QueueHandler;


public class Options {
	public static JButton okButton = new JButton(" Search ");
	static JSVGCanvas image = new JSVGCanvas(); 
	public static Component OptionArea(){
		new MapShapping("first"); 
		image.setURI(new File("data/svg/singapore.svg").toURI().toString());
		image.setAutoscrolls(true);
		
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

		ActionListener searchListener = new ActionListener() {
			private String vehicle = "";
			private String searchinfo = "";

			public void actionPerformed(ActionEvent e) {
				if (walking.isSelected() == true) {
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
				SuggestionSummary.summary.setText("<html> Departure : " + SearchArea.departure.getText()
						+ "<br/> <br/> " + "Arrival :" + SearchArea.arrival.getText() + "<br/> <br/> " + "By : "
						+ vehicle + "<br/> <br/>  " + model1.getValue() + "<br/> <br/> " + "</html>");
				setSearchinfo(getSearchinfo() + SearchArea.departure.getText() + ","
						+ SearchArea.arrival.getText() + ",");
				new MapShapping(MapShapping.getZoomLevel()); 
				image.setURI(new File("data/svg/singapore.svg").toURI().toString());
				image.setAutoscrolls(true);
				
		    	//ThreadEventListener threadingListener = new ThreadEventListener();
		    	/*PathFinder finder = new PathFinder();
		    	//finder.registerThreadEventListener(threadingListener);
		    	finder.run();
		    	try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	ArrayList<Route> route = QueueHandler.dequeue();
		    	if(route!=null) System.out.println(route.get(1).getNodes().get(1));
				 //ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
				 //final ScheduledFuture<?> senderHandle = scheduler.scheduleAtFixedRate(finder.getInstance(), 1000, 500, TimeUnit.MILLISECONDS);*/
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
