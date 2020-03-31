package sort;
import javax.swing.*;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.*;

/**
 * Represents the user interface for sort object
 * 
 * @author Liam Geyer
 * @version v1.0.0
 */
@SuppressWarnings("serial")
public class GUI extends JFrame
{
    private static final String[] SORT_TYPES = Sorter.SORT_TYPES;
    private final int DEFAULT_QUANTITY = 250;
    private final int DEFAULT_MAX_VALUE = 1000;
    private final int MAX_QUANTITY = 10000;
    private final int MAX_MAX_VALUE = 10000;
    private boolean processFlag;
    private boolean abortFlag;
    //private boolean devFlag;
    //private boolean verbFlag;

    private GenerationPanel generationPanel;
    private JButton minimizeButton;

    private JPanel graphicsPanel;
    private Graphic graphic;
    private ImageIcon expandIcon;
    private ImageIcon minimizeIcon;
    private ImageIcon windowIcon;

    private Sorter sorter;

    /**
     * Default constructor for GUI. Constructs with all params false
     */
    public GUI()
    {
        this(false, false);
    }

    /**
     * Constructor containing flags for debug and verb windows
     * 
     * @param devFlag set true to enable the dev windows
     * @param verbFlag set true to enable verbose mode
     */
    public GUI(boolean devFlag, boolean verbFlag)
    {
        super("Sorting Algorithm Visualizer");
        //this.devFlag = devFlag;
        //this.verbFlag = verbFlag;
        this.processFlag = false;
        this.abortFlag = false;

        //create panels
        graphicsPanel = new JPanel(new BorderLayout());
        graphicsPanel.add(new JLabel("No Sorter To Display", JLabel.CENTER), BorderLayout.CENTER);
        generationPanel = new GenerationPanel();

        //load icons
        expandIcon = new ImageIcon("resources/expand.jpg");
        minimizeIcon = new ImageIcon("resources/minimize.jpg");
        windowIcon = new ImageIcon("resources/sort.jpg");

        //minimize generation panel button
        minimizeButton = new JButton(minimizeIcon);
        minimizeButton.setActionCommand("minimize");
        minimizeButton.addActionListener(generationPanel);

        //set content pane
        setContentPane(graphicsPanel);
        JLayeredPane layeredPane = getLayeredPane();
        generationPanel.setSize(200, 150);
        generationPanel.setLocation(0, 0);
        layeredPane.add(generationPanel, JLayeredPane.PALETTE_LAYER);

        minimizeButton.setBounds(new Rectangle(new Point(0, 0), new Dimension(20, 20)));//temporary magic numbers
        layeredPane.add(minimizeButton, JLayeredPane.MODAL_LAYER);

        //configure frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setIconImage(windowIcon.getImage());
        //start at maximum size
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Constructor that does nothing. Used by the GhostGUI
     * 
     * @param GhostFlag exists to differentiate from other constructors. value of GhostFlag has no effect on the constructor
     */
    protected GUI(boolean GhostFlag)
    {
        //does nothing
    }

    //graphics events

    /**
     * Calls updateBars for the contained graphic object
     */
    public void updateBars()
    {
        graphic.updateBars();
    }

    /**
     * Calls setSelector for the contained graphic object
     * 
     * @param index index to set selector to
     */
    public void setSelector(int index)
    {
        final int INDEX = index;
        javax.swing.SwingUtilities.invokeLater
        (
            new Runnable()
            {
                public void run()
                {
                    graphic.setSelector(INDEX);
                }
            }
        );
    }

    /**
     * Generate and add graphic to graphics panel
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
        graphic.updateBoth();
        //keeps graphic at the same size as the panel
        graphicsPanel.addComponentListener
        (
            new ComponentAdapter()
            {
                public void componentResized(ComponentEvent componentEvent)
                {
                    graphic.width = graphicsPanel.getWidth();
                    graphic.height = graphicsPanel.getHeight();
                    graphic.updateBoth();
                }
            }
        );
    }

    /**
     * Returns the state of the abort flag, which indicates if the current process has been requested to abort
     * @return the abort flag
     */
    public boolean getAbortFlag()
    {
        return abortFlag;
    }

    //public generationPanel methods
    /**
     * Lets the GUI know that a process has either begun or ended
     */
    public void toggleProcess()
    {
        generationPanel.toggleProcess();
    }

    /**
     * Lets the GUI know that the current process has been aborted
     */
    public void abort()
    {
        abortFlag = false;
        generationPanel.toggleProcess();
    }

    /**
     * GenerationPanel Class - represents a panel containing the controls for generating and modifying sort objects
     */
    private class GenerationPanel extends JPanel implements ActionListener
    {
		protected JComboBox<String> sortTypes;
		private JComboBox<String> genMethods;
        private NumberFormat numberFormat;
        private JFormattedTextField quantityBox;
        private JFormattedTextField maxValueBox;
        //private JFormattedTextField delayBox;
        private JButton quantityReset;
        private JButton maxValueReset;
        private JButton generateButton;
        private JButton shuffleButton;
        private JButton sortButton;
        private JButton abortButton;
        private GridLayout layout;

        public GenerationPanel()
        {
            super();

            layout = new GridLayout(6, 2, 1, 1);
            setLayout(layout);
            

            /*
             * prepare components
             */

            //combo box for types
            sortTypes = new JComboBox<String>(SORT_TYPES);
            sortTypes.setActionCommand("type_change");
            sortTypes.addActionListener(this);

            //create number format
            numberFormat = NumberFormat.getIntegerInstance();
            numberFormat.setGroupingUsed(false);

            //text box and reset button for quantity and max value
            NumberFormatter quantityFormatter = new NumberFormatter(numberFormat);
            quantityFormatter.setAllowsInvalid(false);
            quantityFormatter.setCommitsOnValidEdit(true);
            quantityFormatter.setMaximum(MAX_QUANTITY);
            quantityBox = new JFormattedTextField(quantityFormatter);
            quantityBox.setColumns(10);
            quantityBox.setValue(DEFAULT_QUANTITY);

            quantityReset = new JButton("reset");
            quantityReset.setActionCommand("reset_quantity");
            quantityReset.addActionListener(this);

            NumberFormatter maxValueFormatter = new NumberFormatter(numberFormat);
            maxValueFormatter.setAllowsInvalid(false);
            maxValueFormatter.setCommitsOnValidEdit(true);
            maxValueFormatter.setMaximum(MAX_MAX_VALUE);
            maxValueBox = new JFormattedTextField(maxValueFormatter);
            maxValueBox.setColumns(10);
            maxValueBox.setValue(DEFAULT_MAX_VALUE);

            maxValueReset = new JButton("reset");
            maxValueReset.setActionCommand("reset_max_value");
            maxValueReset.addActionListener(this);
            
            //generation method selector
            genMethods = new JComboBox<String>(ValuesArray.GENERATION_METHODS);

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

            //delay input box
            //             delayBox = new JFormattedTextField(formatter);
            //             delayBox.setColumns(10);
            //             delayBox.setValue(0);
            //             delayBox.setActionCommand("change_delay");
            //             delayBox.addActionListener(this);

            /*
             * add all components to panel
             */

            //add header
            //JLabel header = new JLabel("Opti", JLabel.RIGHT);
            //add(header);
            //add(new JLabel("ons"));

            //add label for method selector
            JLabel selectorLabel = new JLabel("Sort Method:", JLabel.CENTER);
            add(selectorLabel);

            //add method selector
            add(sortTypes);

            /*
             * New Line
             */

            //add quantity label
            JLabel quantityLabel = new JLabel("Quantity:", JLabel.CENTER);
            add(quantityLabel);

            //add quantity box
            add(quantityBox);

            /*
             * New Line
             */

            //add max value label
            JLabel mvLabel = new JLabel("Max Value:", JLabel.CENTER);
            add(mvLabel);

            //add max value box
            add(maxValueBox);


            /*
             * New Line
             */
            
            add(new JLabel("test", JLabel.CENTER));
            add(genMethods);

            //add generate button
            //JLabel generateLabel = new JLabel("Generate:", JLabel.CENTER);
            //add(generateLabel);
            add(generateButton);

            /*
             * New Line
             */

            //add shuffle button
            add(shuffleButton);

            //add sort button
            add(sortButton);

            /*
             * New Line
             */

            //add abort button
            add(abortButton);

            /*
             * New Line
             */

            //add delay box
            
            //misc panel config
            setBorder(BorderFactory.createLineBorder(Color.RED));
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
                //hide the selector
                graphic.hideSelector();

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

        /**
         * Runnable for GUI actions
         */
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
                    int quantity = Integer.parseInt(quantityBox.getText());
                    int maxValue = Integer.parseInt(maxValueBox.getText());
                    //end if vars are out of bounds
                    if(quantity < 1 || maxValue < 1 || quantity > MAX_QUANTITY || maxValue > MAX_MAX_VALUE)
                    {
                        System.err.print("oh no");
                        return;
                    }
                    String type = (String)sortTypes.getSelectedItem();
                    sorter = new Sorter(quantity, maxValue, type, genMethods.getSelectedIndex());
                    sorter.setGUI(GUI.this);
                    shuffleButton.setEnabled(true);
                    sortButton.setEnabled(true);
                    graphicGeneration();
                }
                else if(command.equals("shuffle"))
                {
                    toggleProcess();
                    sorter.shuffle();
                }
		        else if(command.equals("sort"))
                {
                    toggleProcess();
                    sorter.sort();
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
                else if(command.equals("minimize"))
                {
                    minimizeButton.setIcon(expandIcon);
                    minimizeButton.setActionCommand("expand");
                    setVisible(false);
                }
                else if(command.equals("expand"))
                {
                    minimizeButton.setIcon(minimizeIcon);
                    minimizeButton.setActionCommand("minimize");
                    setVisible(true);
                }
                else
                {
                    throw new IllegalArgumentException("Unknown action \"" + command + "\"");
                }
            }
        }
    }
}
