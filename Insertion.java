
/**
 * Class Insertion - Find out of place item, then insert it into correct position
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Insertion extends Sorter
{
    public Insertion(int quantity, int maxValue)
    {
        super(quantity, maxValue);
        this.shuffle();
    }

    public void sort()
    {
        //for each item
        for(int i = 1; i < items.length; i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && items[i].getValue() < items[j].getValue())
            {
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j != i - 1)
            {
                this.move(i, j + 1);
            }
        }
    }

    public void sort(GUI gui)
    {
        Graphic g = gui.graphic;
        //for each item
        for(int i = 1; i < items.length; i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && items[i].getValue() < items[j].getValue())
            {
                g.setSelector(j);
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j != i - 1)
            {
                this.move(i, j + 1);
                g.updateBars();
            }
        }
    }
}