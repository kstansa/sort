import javax.swing.*;
/**
 * Class SortDemo - Demo for the sort program
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class SortDemo
{
    private static final int MAX_QUANTITY = 1000;
    private static final int MAX_SIZE = 900;

    public static void main(String[] args)
    {
        //         //start GUI
        //         GUI gui = new GUI();
        //         Sorter sorter;
        //         while(gui.finalType == null)
        //         {
        //             System.out.print("");
        //         }
        //         sorter = gui.finalType;
        //Show options
        String[] types = Sorter.SORT_TYPES;
        String[] options = new String[types.length + 2];
        for(int i = 0; i < types.length; i++)
        {
            options[i] = types[i];
        }
        int response = 0;
        int quantity = 250;
        int size = 250;
        boolean done = false;
        while(!done)
        {
            options[options.length - 2] = "Set Quantity (" + quantity + ")";
            options[options.length - 1] = "Set Max Size (" + size + ")";
            response = JOptionPane.showOptionDialog(null, "Launch Options", "Select an option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            done = true;
            //check for quantity change
            if(response == options.length - 2)
            {
                quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter Quantity (max = " + MAX_QUANTITY + "): "));
                done = false;
            }
            //check for max size change
            else if(response == options.length - 1)
            {
                quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter Max Size (max = " + MAX_SIZE + "): "));
                done = false;
            }
            //check for invalid values
            if(quantity < 1 || quantity > MAX_QUANTITY)
            {
                JOptionPane.showMessageDialog(null, "Invalid quantity");
                done = false;
            }
            if(size < 1 || size > MAX_SIZE)
            {
                JOptionPane.showMessageDialog(null, "Invalid size");
                done = false;
            }
        }
        Sorter sorter = parseResponse(response, quantity, size);
        String[] displayTypes = {"Print", "Graphical"};
        int style = JOptionPane.showOptionDialog(null, "Choose display style", "Select an option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, displayTypes, displayTypes[0]);
        if(style == 0)printDemo(sorter);
        else graphicDemo(sorter);
    }

    private static void printDemo(Sorter sorter)
    {
        System.out.println("Sort Object Created\n\nObject Contents:\n");
        sorter.print();
        System.out.println("\nShuffeling Object\n");
        sorter.shuffle();
        sorter.print();
        System.out.println("\nSorting...\n...");
        sorter.sort();
        System.out.println("Done\n");
        sorter.print();
    }

    private static void graphicDemo(Sorter sorter)
    {
        GUI g = new GUI(sorter);
        g.updateInitial(sorter);
        sorter.shuffle();
        g.updateShuffle(sorter);
        sorter.sort(g);
    }

    private static Sorter parseResponse(int response, int n, int size)
    {
        //"Selection", "Bubble", "Insertion"
        if(response == 0)return new Selection(n, size);
        else if(response == 1)return new Bubble(n, size);
        else if(response == 2)return new Insertion(n, size);
        throw new IllegalArgumentException("e");
    }
}
