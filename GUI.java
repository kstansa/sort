import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

/**
 * Class GUI - Object representing the user interface
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GUI extends JFrame
{
    private JPanel p;
    private JLabel[] graphicLabels = new JLabel[3];
    private ImageIcon[] imageIcons = new ImageIcon[3];
    //private ArrayList<JLabel> graphicLabels = new ArrayList<JLabel>();
    //private ArrayList<ImageIcon> imageIcons = new ArrayList<ImageIcon>();
    
    public GUI(Sorter sorter)
    {
        super("GUI");
        this.setVisible(false);
        this.setMinimumSize(new Dimension((sorter.getQuantity() + 50) * 3, sorter.getMaxSize() + 100));
        this.setSize(1,1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        p = new JPanel();
        
        this.add(p);
    }
    
    public void updateInitial(Sorter sorter)
    {
        int graphicalIndex = 0;
        BufferedImage image = sorter.display();
        ImageIcon icon = new ImageIcon(image);
        imageIcons[graphicalIndex] = icon;
        //imageIcons.add(icon);
        JLabel label = new JLabel(icon);
        graphicLabels[graphicalIndex] = label;
        //graphicLabels.add(label);
        this.updatePanel();
    }
    
    public void updateShuffle(Sorter sorter)
    {
        int graphicalIndex = 1;
        BufferedImage image = sorter.display();
        ImageIcon icon = new ImageIcon(image);
        imageIcons[graphicalIndex] = icon;
        //imageIcons.add(icon);
        JLabel label = new JLabel(icon);
        graphicLabels[graphicalIndex] = label;
        //graphicLabels.add(label);
        this.updatePanel();
    }
    
    public void updateFinal(Item[] items, int maxSize)
    {
        int graphicalIndex = 2;
        BufferedImage image = Sorter.display(items, maxSize);
        ImageIcon icon = new ImageIcon(image);
        imageIcons[graphicalIndex] = icon;
        //imageIcons.add(icon);
        JLabel label = new JLabel(icon);
        graphicLabels[graphicalIndex] = label;
        //graphicLabels.add(label);
        this.updatePanel();
    }
    
    public void updateFinal(ArrayList<Item> items, int maxSize)
    {
        int graphicalIndex = 2;
        BufferedImage image = Sorter.display(items, maxSize);
        ImageIcon icon = new ImageIcon(image);
        imageIcons[graphicalIndex] = icon;
        //imageIcons.add(icon);
        JLabel label = new JLabel(icon);
        graphicLabels[graphicalIndex] = label;
        //graphicLabels.add(label);
        this.updatePanel();
    }
    
    private void updatePanel()
    {
        this.setVisible(false);
        //reconstruct p
        this.remove(p);
        p = new JPanel();
        //for each panel object, add in labels
        for(int i = 0; i < graphicLabels.length; i++)
        {
            if(graphicLabels[i] != null)
            {
                p.add(graphicLabels[i]);
            }
        }
        //add p back
        this.add(p);
        this.setVisible(true);
    }
}

