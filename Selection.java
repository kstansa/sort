import java.util.ArrayList;
/**
 * Class Select - sorts using selection sorting
 * The selection sort algorithm sorts an array by repeatedly finding the minimum element (considering ascending order) from unsorted part and putting it at the beginning.
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Selection extends Sorter
{
    public Selection(int quantity, int maxValue)
    {
        super(quantity, maxValue);
        this.shuffle();
    }

    public void sort()
    {
        //for each item
        for(int i = 0; i < items.length; i++)
        {
            //initialize minimums
            double minValue = items[i].getValue();
            int minIndex = i;
            //for each unsorted item
            for(int j = i + 1; j < items.length; j++)
            {
                //update min value if item j is smaller
                if(items[j].getValue() < minValue)
                {
                    minValue = items[j].getValue();
                    minIndex = j;
                }
            }
            //move min item to i
            this.move(minIndex, i);
        }
    }

    public void sort(GUI gui)
    {
        Graphic g = gui.graphic;
        //for each item
        for(int i = 0; i < items.length; i++)
        {
            g.setSelector(i);
            //initialize minimums
            double minValue = items[i].getValue();
            int minIndex = i;
            //for each unsorted item
            for(int j = i + 1; j < items.length; j++)
            {
                g.setSelector(j);
                //update min value if item j is smaller
                if(items[j].getValue() < minValue)
                {
                    minValue = items[j].getValue();
                    minIndex = j;
                }
            }
            //move min item to i
            this.move(minIndex, i);
            g.updateBars();
            //check for abortFlag
            if(gui.abortFlag){gui.abortFlag = false; return;}
        }
        //indicate to gui that process has ended
        gui.toggleProcess();
    }
}
