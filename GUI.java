import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

/**
 * Class GUI - represents the user interface for sort
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GUI extends JFrame
{
    //Help: https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
    private JMenuBar menu;
    private GenerationPanel generationPanel;
    private GraphicsPanel graphicsPanel;
    private JLabel texts;
    
    public GUI()
    {
        super("Sort Demos");
        //TODO Sizing/proper defaults
        graphicsPanel = new GraphicsPanel();
        generationPanel = new GenerationPanel(graphicsPanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, generationPanel, graphicsPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        add(splitPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setVisible(true);
    } 
    
    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable(){public void run(){GUI gui = new GUI();}});
    }
}
