package graphic;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.UIManager;

public class MainWindow extends JFrame {
   
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 600;

    public MainWindow() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(dim.width/2-FRAME_WIDTH/2, dim.height/2-FRAME_HEIGHT/2, FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
    
        JPanel westPanel = new JPanel(new BorderLayout());
        JPanel eastPanel = new JPanel(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(eastPanel);
        splitPane.setRightComponent(westPanel);
        splitPane.setDividerLocation(100);
        splitPane.setContinuousLayout(true);
        this.add(splitPane, BorderLayout.CENTER);
        //
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab ("Road ", new JPanel());
        tabbedPane.addTab("Places", new JPanel());
        
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setAutoscrolls(false);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        westPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
    }
}