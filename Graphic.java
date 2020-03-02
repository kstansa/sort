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
public class Graphic extends JLabel
{
    //Numeric constants
    private final double EPSILON = 0.001;
    //Color constants
    private final Color BARS = Color.GRAY;
    private final Color BACKGROUND = Color.WHITE;
    private final Color SELECTOR = Color.RED;
    private final Color GRAY = Color.GRAY;
    //instance vars
    private Sorter sorter;
    private Item[] displayedArr;
    private Item[] currentArr;
    private int selectorIndex = 0;
    private CustomImage image;
    private Image scaledImage;
    public int width = 500;
    public int height = 250;

    /**
     * Sole constructor.
     * 
     * @param sorter sorter object to be linked to the graphic
     */
    public Graphic(Sorter sorter)
    {
        //JLabel constructor
        super(new ImageIcon());

        //set fields
        this.sorter = sorter;

        //create CustomImage object
        image = new CustomImage(sorter.getQuantity(), sorter.getMaxValue());

        //perform first write of sorter data
        initialWrite();
    }

    //image updates
    /**
     * Performs first write of sorter data to image.
     */
    private void initialWrite()
    {
        //set displayed arr
        displayedArr = sorter.getItems().clone();

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

    /**
     * Sets position of the selector bar in the sorter's array.
     * 
     * @param index index to move selector to
     */
    public void setSelector(int index)
    {
        //repaint old nub
        image.paintNub(selectorIndex, BACKGROUND);
        //image.paintColumnTo(selectorIndex, (int)(displayedArr[selectorIndex].getValue()), BARS);

        //paint in new selector
        image.paintNub(index, SELECTOR);
        selectorIndex = index;

        //update display
        updateDisplay();
    }

    /**
     * updates the graphic to represent current state of the sorter's array.
     */
    public void updateBars()
    {
        //find indexes of moved items
        int[] diffIndexes = findDifferences();

        //update displayedArr
        displayedArr = currentArr;

        //update bars at specified indexes
        for(int index : diffIndexes)
        {
            image.paintColumn(index, BACKGROUND);
            image.paintColumnTo(index, (int)(displayedArr[index].getValue()), BARS);
        }

        //update display
        updateDisplay();
    }

    /**
     * Updates the label container to match the current image.
     */
    public void updateDisplay()
    {
        //hide label so that it will reappear with correct image
        //setVisible(false);
        //create scaled version of image
        scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        //update label icon
        setIcon(new ImageIcon(scaledImage));
        //reset visibility to update what user sees
        //setVisible(true);
    }

    /**
     * Finds differences between the sorter's array and what is currently being displayed.
     * 
     * @return int array containing the indexes of all the discrepencies between the two arrays
     */
    private int[] findDifferences()
    {
        currentArr = sorter.getItems().clone();
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
