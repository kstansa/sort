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
public class Graphic extends JPanel
{
    //Numeric constants
    private final double EPSILON = 0.001;
    //Color constants
    private final Color BARS = Color.GRAY;
    private final Color BACKGROUND = Color.WHITE;
    private final Color SELECTOR = Color.RED;
    //instance vars
    private Sorter sorter;
    private Item[] displayedArr;
    private Item[] currentArr;
    private CustomImage image;
    private SelectorImage selectorImage;
    private Image scaledImage;
    private Image scaledSelector;
    private JLabel imageLabel;
    private JLabel selectorLabel;
    /**
     * Width of the main graphic in pixels
     */
    public int width = 500;
    /**
     * Height of the main graphic in pixels
     */
    public int height = 250;

    /**
     * Sole constructor.
     * 
     * @param sorter sorter object to be linked to the graphic
     */
    public Graphic(Sorter sorter)
    {
        //JLabel constructor
        super();
        imageLabel = new JLabel(new ImageIcon());
        selectorLabel = new JLabel(new ImageIcon());

        //set fields
        this.sorter = sorter;

        //create CustomImage object
        image = new CustomImage(sorter.getQuantity(), sorter.getMaxValue());
        selectorImage = new SelectorImage(sorter.getQuantity());

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

        //for each item
        for(int i = 0; i < sorter.getQuantity(); i++)
        {
            //paint bar
            image.paintBar(i, (int)(displayedArr[i].getValue()));
        }

        add(selectorLabel);
        add(imageLabel);

        //update display
        updateBoth();
    }

    /**
     * Sets position of the selector bar in the sorter's array. Currently depreciated due to efficency concerns
     * 
     * @param index index to move selector to
     */
    public void setSelector(int index)
    {
        selectorImage.setSelector(index);
        updateSelector();
    }

    /**
     * Updates the graphic to represent current state of the sorter's array.
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
            image.paintColumn(index);
            image.paintBar(index, (int)(displayedArr[index].getValue()));
        }

        //update display
        updateImage();
    }

    /**
     * Updates the displayed image.
     */
    public void updateImage()
    {
        //create scaled version of image
        scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        //update label icon
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * Updates the displayed selector image
     */
    public void updateSelector()
    {
        scaledSelector = selectorImage.getScaledInstance(width, 5, Image.SCALE_DEFAULT);
        selectorLabel.setIcon(new ImageIcon(scaledSelector));
    }

    /**
     * Updates both the displayed image and the displayed selector image
     */
    public void updateBoth()
    {
        //create scaled version of image
        scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        scaledSelector = selectorImage.getScaledInstance(width, 5, Image.SCALE_DEFAULT);
        //update label icon
        imageLabel.setIcon(new ImageIcon(scaledImage));
        selectorLabel.setIcon(new ImageIcon(scaledSelector));
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

    /**
     * Class CustomImage - extends BufferedImage to include more comprehensive painting tools
     * 
     * @author Liam Geyer
     * @version v1.0.0
     */
    private class CustomImage extends BufferedImage
    {
        /**
         * default constructor
         * 
         * @param width image width
         * @param height image height
         */
        public CustomImage(int width, int height)
        {
            super(width, height, BufferedImage.TYPE_INT_RGB);
            paintBackground();
        }

        /**
         * set the color in the picture to the background color
         * 
         * @param color the color to set to
         */
        public void paintBackground()
        {
            //get color int
            int colorInt = BACKGROUND.getRGB();
            //loop through all x
            for (int x = 0; x < this.getWidth(); x++)
            {
                //loop through all y
                for (int y = 0; y < this.getHeight(); y++)
                {
                    setRGB(x, y, colorInt);
                }
            }
        }

        /**
         * paints column
         * 
         * @param columnIndex x coord of the column
         * @param color color to paint column
         */
        public void paintColumn(int columnIndex)
        {
            //get color int
            int colorInt = BACKGROUND.getRGB();
            //for each y coord
            for(int y = 0; y < this.getHeight(); y++)
            {
                setRGB(columnIndex, y, colorInt);
            }
        }

        /**
         * paints column from bottom to specified height
         * 
         * @param columnIndex x coord of the column
         * @param height height to paint column
         * @param color color to paint column
         */
        public void paintBar(int columnIndex, int height)
        {
            //get color int
            int colorInt = BARS.getRGB();
            //for each y coord starting from bottom to param height
            for(int y = this.getHeight() - 1; y >= this.getHeight() - height; y--)
            {
                setRGB(columnIndex, y, colorInt);
            }
        }
    }

    /**
     * Class SelectorImage -
     * 
     * @author Liam Geyer
     * @version v1.0.0
     */
    private class SelectorImage extends BufferedImage
    {
        private int selectorIndex;

        public SelectorImage(int length)
        {
            super(length, 1, BufferedImage.TYPE_INT_RGB);
            //paint background
            paintBackground();
            //set first selector position
            selectorIndex = 0;
        }

        public void paintBackground()
        {
            //get color int
            int colorInt = BACKGROUND.getRGB();
            //loop through all x
            for(int x = 0; x < this.getWidth(); x++)
            {
                setRGB(x, 0, colorInt);
            }
        }

        public void setSelector(int index)
        {
            setRGB(selectorIndex, 0, BACKGROUND.getRGB());
            selectorIndex = index;
            setRGB(selectorIndex, 0, SELECTOR.getRGB());
        }
    }
}
