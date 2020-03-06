import javax.swing.*;
import javax.swing.event.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;
import java.lang.Number;
import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

/**
 * Class GUI - represents the user interface for sort object
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
    private JLabel threadCount = new JLabel("text", JLabel.CENTER);
    private JTextArea threadList = new JTextArea("");
    private JTextArea sysOut = new JTextArea("");
    private boolean processFlag = false;
    private boolean abortFlag = false;
    private boolean devFlag;
    private boolean verbFlag;

    private GenerationPanel generationPanel;

    private JPanel graphicsPanel;
    private Graphic graphic;

    private Sorter sorter;

    /**
     * default constructor for GUI. Constructs with all params false
     */
    public GUI()
    {
        this(false, false);
    }

    /**
     * sole constructor for the GUI object
     * 
     * @param devFlag set true to enable the dev windows
     * @param verbFlag set true to enable verbose mode
     */
    public GUI(boolean devFlag, boolean verbFlag)
    {
        super("Sort Demos");
        this.devFlag = devFlag;
        this.verbFlag = verbFlag;
        //create thread counter if dev mode
        if(devFlag)
        {
            threadList.setLineWrap(true);
            ThreadCounterRunnable tCRunnable = new ThreadCounterRunnable();
            Thread tCThread = new Thread(tCRunnable);
            tCThread.setName("thread-info-listener");
            tCThread.start();
        }

        //TODO Sizing/proper defaults
        //create panels
        generateGraphics();
        generationPanel = new GenerationPanel();

        //create content pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(graphicsPanel, BorderLayout.CENTER);
        JPanel tempPane = new JPanel(new BorderLayout());
        tempPane.add(generationPanel, BorderLayout.LINE_START);
        if(devFlag)
        {
            tempPane.add(threadList, BorderLayout.CENTER);
            if(verbFlag){tempPane.add(sysOut, BorderLayout.LINE_END);}
        }
        else if(verbFlag)
        {
            tempPane.add(sysOut, BorderLayout.CENTER);
        }
        if(verbFlag)
        {
            PrintStream printStream = new PrintStream(new CustomOutputStream(sysOut));
            System.setOut(printStream);
            System.setErr(printStream);
            System.out.println("Verbose Mode Active");
        }
        contentPane.add(tempPane, BorderLayout.PAGE_START);
        setContentPane(contentPane);

        //configure frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO set size to user's display size
        setMinimumSize(new Dimension(500, 500));
        setVisible(true);
    }

    //graphics events
    /**
     * generates the graphics panel
     */
    private void generateGraphics()
    {
        //TODO Proper Layout
        graphicsPanel = new JPanel(new BorderLayout());

        graphicsPanel.add(new JLabel("No Sorter To Display", JLabel.CENTER), BorderLayout.CENTER);
    }

    /**
     * calls updateBars for the contained graphic object
     */
    public void updateBars()
    {
        graphic.updateBars();
    }

    /**
     * calls setSelector for the contained graphic object
     */
    public void setSelector(int index)
    {
        graphic.setSelector(index);
    }

    /**
     * generate and add graphic to graphics panel
     */
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

    public boolean getAbortFlag()
    {
        return abortFlag;
    }

    //public generationPanel methods
    public void toggleProcess()
    {
        generationPanel.toggleProcess();
    }

    public void abort()
    {
        abortFlag = false;
        toggleProcess();
    }

    /**
     * GenerationPanel Class - represents a panel containing the controls for generating and modifying sort objects
     */
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
        private GridBagLayout gridBag = new GridBagLayout();
        private GridBagConstraints constraints = new GridBagConstraints();

        public GenerationPanel()
        {
            super();

            setLayout(gridBag);

            /*
             * prepare components
             */

            //combo box for types
            sortTypes = new JComboBox(SORT_TYPES);
            sortTypes.setActionCommand("type_change");
            sortTypes.addActionListener(this);

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
            quantityReset.setActionCommand("reset_quantity");
            quantityReset.addActionListener(this);

            maxValueBox = new JFormattedTextField(formatter);
            maxValueBox.setColumns(10);
            maxValueBox.setValue(DEFAULT_MAX_VALUE);

            maxValueReset = new JButton("reset");
            maxValueReset.setActionCommand("reset_max_value");
            maxValueReset.addActionListener(this);

            //generate button
            generateButton = new JButton("generate");
            generateButton.setActionCommand("generate");
            generateButton.addActionListener(this);
            //shuffle button
            shuffleButton = new JButton("shuffle");
            shuffleButton.setEnabled(false);
            shuffleButton.setActionCommand("shuffle");
            shuffleButton.addActionListener(this);
            //sort button
            sortButton = new JButton("sort");
            sortButton.setEnabled(false);
            sortButton.setActionCommand("sort");
            sortButton.addActionListener(this);
            //abort button
            abortButton = new JButton("abort");
            abortButton.setEnabled(false);
            abortButton.setActionCommand("abort");
            abortButton.addActionListener(this);

            /*
             * add all components to panel
             */

            //add header
            constraints.fill = GridBagConstraints.BOTH; //size component to fill box
            constraints.gridwidth = GridBagConstraints.REMAINDER; //Make next component take up rest of row
            if(devFlag)
            {
                gridBag.setConstraints(threadCount, constraints);
                add(threadCount);
            }
            else
            {
                JLabel header = new JLabel("Options", JLabel.CENTER);
                gridBag.setConstraints(header, constraints);
                add(header);
            }

            //add label for method selector
            constraints.gridwidth = GridBagConstraints.RELATIVE; //next-to-last in row
            JLabel sortMethodLabel = new JLabel("Sort Method: ");
            gridBag.setConstraints(sortMethodLabel, constraints);
            add(sortMethodLabel);

            //add method selector
            constraints.gridwidth = GridBagConstraints.REMAINDER; //Make next component take up rest of row
            gridBag.setConstraints(sortTypes, constraints);
            add(sortTypes);

            /*
             * New Line
             */

            //add quantity label
            constraints.gridwidth = 1;
            JLabel quantityLabel = new JLabel("Quantity: ");
            gridBag.setConstraints(quantityLabel, constraints);
            add(quantityLabel);

            //add quantity box
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(quantityBox, constraints);
            add(quantityBox);

            //add reset button for quantity
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            gridBag.setConstraints(quantityReset, constraints);
            add(quantityReset);

            /*
             * New Line
             */

            //add max value label
            constraints.gridwidth = 1;
            JLabel maxValueLabel = new JLabel("Max Value: ");
            gridBag.setConstraints(maxValueLabel, constraints);
            add(maxValueLabel);

            //add max value box
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(maxValueBox, constraints);
            add(maxValueBox);

            //add reset button for max value
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            gridBag.setConstraints(maxValueReset, constraints);
            add(maxValueReset);

            /*
             * New Line
             */

            //add generate button
            gridBag.setConstraints(generateButton, constraints);
            add(generateButton);

            /*
             * New Line
             */

            //add shuffleButton
            constraints.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(shuffleButton, constraints);
            add(shuffleButton);

            //add sort button
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            gridBag.setConstraints(sortButton, constraints);
            add(sortButton);

            /*
             * New Line
             */

            //add abort button
            gridBag.setConstraints(abortButton, constraints);
            add(abortButton);

            //Set panel size
            //setPreferredSize(new Dimension(500, 250));
        }

        private void toggleProcess()
        {
            //do nothing if no sort object has been generated yet
            if(sorter == null){return;}
            //if a process was occuring, update to ended process state
            if(processFlag)
            {
                //update button states
                generateButton.setEnabled(true);
                shuffleButton.setEnabled(true);
                sortButton.setEnabled(true);
                abortButton.setEnabled(false);

                processFlag = false;
            }
            //else
            else
            {
                //update button states
                generateButton.setEnabled(false);
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
            thread.setName("control-panel-action");
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
                if(command.equals("reset_quantity"))
                {
                    quantityBox.setValue(DEFAULT_QUANTITY);
                }

                else if(command.equals("reset_max_value"))
                {
                    maxValueBox.setValue(DEFAULT_MAX_VALUE);
                }
                else if(command.equals("generate"))
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
                    sorter = new Sorter(q, mV, t);
                    shuffleButton.setEnabled(true);
                    sortButton.setEnabled(true);
                    graphicGeneration();
                }
                else if(command.equals("shuffle"))
                {
                    toggleProcess();
                    sorter.shuffle(GUI.this);
                }
                else if(command.equals("sort"))
                {
                    toggleProcess();
                    sorter.sort(GUI.this);
                }
                else if(command.equals("abort"))
                {
                    abortFlag = true;
                }
                else if(command.equals("type_change"))
                {
                    if(sorter != null)
                    {
                        sorter.type = (String)sortTypes.getSelectedItem();
                    }
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
                Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                threadList.setText(threadSet.toString());
            }
        }
    }

    private class CustomOutputStream extends OutputStream
    {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea)
        {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException
        {
            // redirects data to the text area
            textArea.append(String.valueOf((char)b));
            // scrolls the text area to the end of data
            textArea.setCaretPosition(textArea.getDocument().getLength());
            // keeps the textArea up to date
            textArea.update(textArea.getGraphics());
        }
    }
}

