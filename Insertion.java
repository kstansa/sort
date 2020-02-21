import java.util.ArrayList;
/**
 * Class Insertion - Find out of place item, then insert it into correct position
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class Insertion extends Sorter
{
    public Insertion(int n)
    {
        super(n);
        this.shuffle();
    }

    public Insertion(int n, int size)
    {
        super(n, size);
        this.shuffle();
    }

    public void sort()
    {
        //convert to arraylist
        ArrayList<Item> items = this.getItemsArrayList();
        //for each item
        for(int i = 1; i < items.size(); i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && items.get(i).size < items.get(j).size)
            {
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j + 1 != i)
            {
                items.add(j + 1, items.remove(i));
            }
        }
        //set items
        this.setItems(items);
    }

    public void sort(GUI g)
    {
        //convert to arraylist
        ArrayList<Item> items = this.getItemsArrayList();
        //for each item
        for(int i = 1; i < items.size(); i++)
        {
            int j = i - 1;
            //while the previous item is greater than the current item and j is an index, decrement j
            while(j != -1 && items.get(i).size < items.get(j).size)
            {
                j--;
            }
            //if j changed, move item at i to j + 1
            if(j + 1 != i)
            {
                items.add(j + 1, items.remove(i));
                //update graphics
                g.updateFinal(items, this.maxSize);
            }
        }
        //set items
        this.setItems(items);
    }
}