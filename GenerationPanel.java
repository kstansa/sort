import javax.swing.*;
import javax.swing.event.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.lang.Number;
import java.awt.*;
import java.awt.event.*;

/**
 * Class GenerationPanel -
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GenerationPanel extends JPanel implements ActionListener
{
    private static final String[] SORT_TYPES = Sorter.SORT_TYPES;
    private final int DEFAULT_QUANTITY = 250;
    private final int DEFAULT_MAX_VALUE = 1000;

    protected JComboBox sortTypes;
    private NumberFormat numberFormat;
    protected JFormattedTextField quantityBox;
    protected JFormattedTextField maxValueBox;
    protected JButton quantityReset;
    protected JButton maxValueReset;
    protected JButton generateButton;
    protected JButton shuffleButton;
    protected JButton sortButton;
    protected JButton printButton;
    protected Sorter s;
    protected GraphicsPanel g;

    public GenerationPanel(GraphicsPanel g)
    {
        super(new GridLayout(7,2));
        this.g = g;
        //combo box for types
        sortTypes = new JComboBox(SORT_TYPES);
        //create number format
        numberFormat = NumberFormat.getIntegerInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(numberFormat);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        //text box and reset button for quantity and max value
        quantityBox = new JFormattedTextField(formatter);
        quantityBox.setColumns(10);
        quantityBox.setValue(DEFAULT_QUANTITY);
        //quantityBox.addPropertyChangeListener(this);

        quantityReset = new JButton("reset");
        quantityReset.setActionCommand("rq");
        quantityReset.addActionListener(this);

        maxValueBox = new JFormattedTextField(formatter);
        maxValueBox.setColumns(10);
        maxValueBox.setValue(DEFAULT_MAX_VALUE);
        //maxValueBox.addPropertyChangeListener(this);

        maxValueReset = new JButton("reset");
        maxValueReset.setActionCommand("rmv");
        maxValueReset.addActionListener(this);

        //generate button
        generateButton = new JButton("generate");
        generateButton.setActionCommand("g");
        generateButton.addActionListener(this);
        //shuffle button
        shuffleButton = new JButton("shuffle");
        shuffleButton.setEnabled(false);
        shuffleButton.setActionCommand("sh");
        shuffleButton.addActionListener(this);
        //sort button
        sortButton = new JButton("sort");
        sortButton.setEnabled(false);
        sortButton.setActionCommand("s");
        sortButton.addActionListener(this);
        //print button
        printButton = new JButton("print");
        printButton.setEnabled(false);
        printButton.setActionCommand("p");
        printButton.addActionListener(this);

        //add all to panel
        add(new JLabel("Sort Type Options"));
        add(sortTypes);
        add(new JLabel("Quantity: "));
        add(new JLabel(""));
        add(quantityBox);
        add(quantityReset);
        add(new JLabel("Max Value: "));
        add(new JLabel(""));
        add(maxValueBox);
        add(maxValueReset);
        add(generateButton);
        add(shuffleButton);
        add(sortButton);
        add(printButton);
    }

    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if(command.equals("rq"))
        {
            quantityBox.setValue(DEFAULT_QUANTITY);
        }

        else if(command.equals("rmv"))
        {
            maxValueBox.setValue(DEFAULT_MAX_VALUE);
        }
        else if(command.equals("g"))
        {
            //TODO Bounds
            int q = Integer.parseInt(quantityBox.getText());
            int mV = Integer.parseInt(maxValueBox.getText());
            String t = (String)sortTypes.getSelectedItem();
            s = generate(t, q, mV);
            shuffleButton.setEnabled(true);
            sortButton.setEnabled(true);
            printButton.setEnabled(true);
        }
        else if(command.equals("sh"))
        {
            s.shuffle();
        }
        else if(command.equals("s"))
        {
            s.sort();
        }
        else if(command.equals("p"))
        {
            g.setFocusedText(s.toString());
        }
        else
        {
            throw new IllegalArgumentException("Unknown action");
        }
    }

    private static Sorter generate(String t, int q, int mV)
    {
        if(t.equals(SORT_TYPES[0]))
        {
            return new Insertion(q, mV);
        }
        else if(t.equals(SORT_TYPES[1]))
        {
            return new Bubble(q, mV);
        }
        else if(t.equals(SORT_TYPES[2]))
        {
            return new Selection(q, mV);
        }
        else
        {
            throw new IllegalArgumentException("Invalid Type");
        }
    }
}

