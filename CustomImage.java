import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Class CustomImage - extends BufferedImage to include more comprehensive painting tools
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class CustomImage extends BufferedImage
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
    }
    
    /**
     * set the color in the picture to the passed color
     * 
     * @param color the color to set to
     */
    public void paintAllPixels(Color color)
    {
        //get color int
        int colorInt = color.getRGB();
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
     * set color of a pixel in the image
     * 
     * @param x x coord of the pixel
     * @param y y coord of the pixel
     * @param color the color to set the pixel
     */
    public void paintPixel(int x, int y, Color color)
    {
        //get color int
        int colorInt = color.getRGB();
        //set pixel
        setRGB(x, y, colorInt);
    }
    
    /**
     * paints column
     * 
     * @param columnIndex x coord of the column
     * @param color color to paint column
     */
    public void paintColumn(int columnIndex, Color color)
    {
        //get color int
        int colorInt = color.getRGB();
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
    public void paintColumnTo(int columnIndex, int height, Color color)
    {
        //get color int
        int colorInt = color.getRGB();
        //for each y coord starting from bottom to param height
        for(int y = this.getHeight() - 1; y >= this.getHeight() - height; y--)
        {
            setRGB(columnIndex, y, colorInt);
        }
    }
    
    public void paintNub(int columnIndex, Color color)
    {
        //paint top pixel of row
        setRGB(columnIndex, 0, color.getRGB());
    }
}
