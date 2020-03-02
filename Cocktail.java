import java.util.ArrayList;
/**
 * Class Cocktail - sorts using cocktail sorting
 * Each iteration of the algorithm is broken up into 2 stages:

 * The first stage loops through the array from left to right, just like the Bubble Sort. 
 * During the loop, adjacent items are compared and if value on the left is greater than the value on the right, then values are swapped. 
 * At the end of first iteration, largest number will reside at the end of the array.

 * The second stage loops through the array in opposite direction- starting from the item just before the most recently sorted item, and moving back to the start of the array. 
 * Here also, adjacent items are compared and are swapped if required.
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Cocktail extends Sorter
{
    public Cocktail(int quantity, int maxValue)
    {
        super(quantity, maxValue);
        this.shuffle();
    }

    public void sort()
    {
        boolean sorted = false;
        int start = 0;
        int end = this.items.length - 1;

        while(!sorted)
        {
            sorted = true;
            //forward pass
            for(int i = start; i < end; i++)
            {
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                }
            }
            //end if sorted flag was not set
            if(sorted){return;}
            //decrease end
            end--;
            //reverse pass
            for(int i = end - 1; i >= start; i--)
            {
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                }
            }
            //increase start
            start++;
        }
    }

    public void sort(GUI gui)
    {
        Graphic g = gui.graphic;
        boolean sorted = false;
        int start = 0;
        int end = this.items.length - 1;

        while(!sorted)
        {
            sorted = true;
            //forward pass
            for(int i = start; i < end; i++)
            {
                g.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    g.updateBars();
                }
            }
            //end if sorted flag was not set
            if(sorted){return;}
            //decrease end
            end--;
            //reverse pass
            for(int i = end - 1; i >= start; i--)
            {
                g.setSelector(i);
                //if items are not in order, swap them and update sorted
                if(this.items[i].getValue() > this.items[i + 1].getValue())
                {
                    Item temp = this.items[i];
                    this.items[i] = this.items[i + 1];
                    this.items[i + 1] = temp;
                    sorted = false;
                    g.updateBars();
                }
            }
            //increase start
            start++;
        }
    }
}
