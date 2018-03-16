package graphic; 
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GeneralPathExample{
  public static void main(String[] args) {
    JFrame jf = new JFrame("Demo");
    Container cp = jf.getContentPane();
    MyCanvas tl = new MyCanvas();
    cp.add(tl);
    jf.setSize(300, 200);
    jf.setVisible(true);
  }
}

class MyCanvas extends JComponent {
  Shape shape;

  public MyCanvas() {
    shape = create();
  }

  protected Shape create() {
    GeneralPath path = new GeneralPath();
    int [] longitude = {100,50,300,120,140}; 
    int [] lattitude = {50,23,65,95,78,95}; 
    path.moveTo(longitude[0], lattitude[0]);
	for (int i = 1; i < longitude.length; i++) {
		path.lineTo(longitude[i], lattitude[i]);
	}
	return path;
  }


  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    g2.setPaint(Color.black);
    g2.draw(shape);
  }
}