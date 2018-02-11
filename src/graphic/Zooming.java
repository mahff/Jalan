package graphic;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;



public class Zooming extends Application {

    private ImageView imageView = new ImageView(); 
    private ScrollPane scrollPane = new ScrollPane();
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);
    Slider slider = new Slider();
   
    SVGPath svgpath = new SVGPath();
    String path = "M 100 100 L 300 100 L 200 300 z";
    
    

    @Override
    public void start(Stage stage) throws Exception {
    	 slider.setMin(0);
    	 slider.setMax(100);
    	 slider.setValue(100);
    	 slider.setShowTickLabels(true);
         slider.setShowTickMarks(true);
         slider.setBlockIncrement(10);
        zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });

        imageView = new ImageView(new Image(new FileInputStream("/home/mahff/Bureau/génie_Logiciel/Jalan-Uml.png"), 1800, 1500, true, true));
        imageView.preserveRatioProperty().set(true);
        scrollPane.setContent(imageView);
        
        stage.setScene(new Scene(scrollPane, 600, 400));
        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
    
  