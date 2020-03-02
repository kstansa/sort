import javax.swing.*;
import javax.swing.event.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.lang.Number;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Class GUI - represents the user interface for sort
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
public class GUI extends JFrame
{
    //Help: https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
    private static final String[] SORT_TYPES = Sorter.SORT_TYPES;
    private final int DEFAULT_QUANTITY = 250;
    private final int DEFAULT_MAX_VALUE = 1000;
    private final int MAX_QUANTITY = 10000;
    private final int MAX_MAX_VALUE = 10000;
    private JLabel threadCount = new JLabel("text");
    private JMenuBar menu;
    private boolean processFlag = false;
    private boolean abortFlag = false;

    private GenerationPanel generationPanel;

    private JPanel graphicsPanel;
    private Graphic graphic;

    private JLabel texts;
    private Sorter sorter;

    public GUI()
    {
        super("Sort Demos");
        //create thread counter
        ThreadCounterRunnable tCRunnable = new ThreadCounterRunnable();
        Thread tCThread = new Thread(tCRunnable);
        tCThread.start();

        //TODO Sizing/proper defaults
        //create panels
        generateGraphics();

        generationPanel = new GenerationPanel();

        //create content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(graphicsPanel, BorderLayout.CENTER);
        contentPane.add(generationPanel, BorderLayout.PAGE_START);
        setContentPane(contentPane);

        //configure frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setVisible(true);
    }

    private void generateGraphics()
    {
        //TODO Proper Layout
        graphicsPanel = new JPanel(new GridLayout(1, 1));

        graphicsPanel.add(new JLabel("Placeholder Text"));
    }

    //graphics events
    /**
     * calls updateBars for the contained graphic object
     */
    public void updateBars()
    {
        graphic.updateBars();
    }

    public void setSelector(int index)
    {
        graphic.setSelector(index);
    }

    private void graphicGeneration()
    {
        //recreate the panel to remove old items
        graphicsPanel.removeAll();
        graphicsPanel.setLayout(new GridLayout(1, 1));
        graphic = new Graphic(sorter);

        graphicsPanel.add(graphic);
        graphic.width = graphicsPanel.getWidth();
        graphic.height = graphicsPanel.getHeight();
        graphic.updateDisplay();
        //keeps graphic at the same size as the panel
        graphicsPanel.addComponentListener
        (
            new ComponentAdapter()
            {
                public void componentResized(ComponentEvent componentEvent)
                {
                    graphic.width = graphicsPanel.getWidth();
                    graphic.height = graphicsPanel.getHeight();
                    graphic.updateDisplay();
                }
            }
        );
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
        else if(t.equals(SORT_TYPES[3]))
        {
            return new Cocktail(q, mV);
        }
        else
        {
            throw new IllegalArgumentException("Invalid Type");
        }
    }

    //public generationPanel methods
    public void toggleProcess()
    {
        generationPanel.toggleProcess();
    }

    public boolean getAbortFlag()
    {
        return abortFlag;
    }

    public void abort()
    {
        abortFlag = false;
        toggleProcess();
    }

    private class GenerationPanel extends JPanel implements ActionListener
    {
        protected JComboBox sortTypes;
        private NumberFormat numberFormat;
        private JFormattedTextField quantityBox;
        private JFormattedTextField maxValueBox;
        private JButton quantityReset;
        private JButton maxValueReset;
        private JButton generateButton;
        private JButton shuffleButton;
        private JButton sortButton;
        private JButton abortButton;

        public GenerationPanel()
        {
            super(new GridLayout(7,2));
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

            quantityReset = new JButton("reset");
            quantityReset.setActionCommand("rq");
            quantityReset.addActionListener(this);

            maxValueBox = new JFormattedTextField(formatter);
            maxValueBox.setColumns(10);
            maxValueBox.setValue(DEFAULT_MAX_VALUE);

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
            //abort button
            abortButton = new JButton("abort");
            abortButton.setEnabled(false);
            abortButton.setActionCommand("a");
            abortButton.addActionListener(this);

            //add all to panel
            add(new JLabel("Sort Type Options"));
            add(sortTypes);
            add(new JLabel("Quantity: "));
            add(new JLabel(""));
            add(quantityBox);
            add(quantityReset);
            add(new JLabel("Max Value: "));
            add(threadCount);
            add(maxValueBox);
            add(maxValueReset);
            add(generateButton);
            add(shuffleButton);
            add(sortButton);
            add(abortButton);

            //Set panel size
            setPreferredSize(new Dimension(500, 250));
        }

        private void toggleProcess()
        {
            //do nothing if no sort object has been generated yet
            if(sorter == null){return;}
            //if a process was occuring, update to ended process state
            if(processFlag)
            {
                //update button states
                shuffleButton.setEnabled(true);
                sortButton.setEnabled(true);
                abortButton.setEnabled(false);

                processFlag = false;
            }
            //else
            else
            {
                //update button states
                shuffleButton.setEnabled(false);
                sortButton.setEnabled(false);
                abortButton.setEnabled(true);

                processFlag = true;
            }
        }

        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            GUIActionRunnable runnable = new GUIActionRunnable(command);
            Thread thread = new Thread(runnable);
            thread.start();
        }

        private class GUIActionRunnable implements Runnable
        {
            private String command;

            public GUIActionRunnable(String command)
            {
                this.command = command;
            }

            public void run()
            {
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
                    //TODO inform user of bounds violation
                    int q = Integer.parseInt(quantityBox.getText());
                    int mV = Integer.parseInt(maxValueBox.getText());
                    //end if vars are out of bounds
                    if(q < 1 || mV < 1 || q > MAX_QUANTITY || mV > MAX_MAX_VALUE)
                    {
                        return;
                    }
                    String t = (String)sortTypes.getSelectedItem();
                    sorter = generate(t, q, mV);
                    shuffleButton.setEnabled(true);
                    sortButton.setEnabled(true);
                    graphicGeneration();
                }
                else if(command.equals("sh"))
                {
                    toggleProcess();
                    sorter.shuffle(GUI.this);
                }
                else if(command.equals("s"))
                {
                    toggleProcess();
                    sorter.sort(GUI.this);
                }
                else if(command.equals("a"))
                {
                    abortFlag = true;
                }
                else
                {
                    throw new IllegalArgumentException("Unknown action");
                }
            }
        }
    }
    private class ThreadCounterRunnable implements Runnable
    {
        public void run()
        {
            ThreadGroup currentTG = Thread.currentThread().getThreadGroup();
            
            while(true)
            {
                threadCount.setText("Thread Count for Thread Group " + currentTG.getName() + ": " + currentTG.activeCount());
            }
        }
    }
}

