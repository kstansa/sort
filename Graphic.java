import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

/**
 * Class Graphic - A graphical representation of a sorter's items array
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
//JLabel <-- ImageIcon <-- BufferedImage (which is subclass of image)
public class Graphic extends ImageIcon
{
    //Numeric constants
    private final double EPSILON = 1E14;
    //Color constants
    private final Color BARS = Color.BLACK;
    private final Color BACKGROUND = Color.WHITE;
    private final Color SELECTOR = Color.RED;
    private final Color GRAY = Color.GRAY;
    //instance vars
    private Sorter sorter;
    private Item[] displayedArr;
    private int selectorIndex = 0;
    private CustomImage image;
    private Image scaledImage;
    public int width = 500;
    public int height = 250;
    
    public Graphic(Sorter sorter)
    {
        //set fields
        this.sorter = sorter;
        
        //create image object
        image = new CustomImage(sorter.getQuantity(), sorter.getMaxValue());
        initialWrite();
    }
    
    //image updates
    private void initialWrite()
    {
        //set displayed arr
        displayedArr = sorter.getItems();
        
        //paint background
        image.paintAllPixels(BACKGROUND);
        
        //for each item
        for(int i = 0; i < sorter.getQuantity(); i++)
        {
            //paint bar
            image.paintColumnTo(i, (int)(displayedArr[i].getValue()), BARS);
        }
        
        //update display
        updateDisplay();
    }
    
    public void setSelector(int index)
    {
        //repaint column of old selector location
        image.paintColumn(selectorIndex, BACKGROUND);
        image.paintColumnTo(selectorIndex, (int)(displayedArr[selectorIndex].getValue()), BARS);
        
        //paint in new selector
        image.paintColumn(index, SELECTOR);
        selectorIndex = index;
        
        //update display
        updateDisplay();
    }
    
    public void updateBars()
    {
        //find indexes of moved items
        int[] diffIndexes = findDifferences();
        //update displayedArr
        displayedArr = sorter.getItems();
        //update bars at specified indexes
        for(int index : diffIndexes)
        {
            image.paintColumn(index, BACKGROUND);
            image.paintColumnTo(index, (int)(displayedArr[index].getValue()), BARS);
        }
        //update display
        updateDisplay();
    }
    
    public void updateDisplay()
    {
        scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        setImage(scaledImage);
    }
    
    private int[] findDifferences()
    {
        Item[] currentArr = sorter.getItems();
        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for(int i = 0; i < currentArr.length; i++)
        {
            if(Math.abs(displayedArr[i].getValue() - currentArr[i].getValue()) > EPSILON)
            {
                indexList.add(new Integer(i));
            }
        }
        int[] output = new int[indexList.size()];
        for(int i = 0; i < output.length; i++)
        {
            output[i] = indexList.get(i);
        }
        return output;
    }
}
