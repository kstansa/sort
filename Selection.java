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
    public Selection(int n)
    {
        super(n);
        this.shuffle();
    }
    
    public void sort()
    {
        ArrayList<Item> unsorted = this.getItemsArrayList();
        Item[] sorted = new Item[this.items.length];
        //for each item
        for(int i = 0; i < sorted.length; i++)
        {
            double smallestSize = unsorted.get(0).size;
            int smallestIndex = 0;
            //for each unsorted item
            for(int j = 0; j < unsorted.size(); j++)
            {
                if(unsorted.get(j).size < smallestSize)
                {
                    smallestSize = unsorted.get(j).size;
                    smallestIndex = j;
                }
            }
            //add new item
            sorted[i] = unsorted.remove(smallestIndex);
        }
        //update items
        this.items = sorted;
    }
}
