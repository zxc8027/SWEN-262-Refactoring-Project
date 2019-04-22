package ui; /**
 * Class for GUI components need to add a party.
 */

import model.Bowler;
import model.BowlerFile;
import ui.eventhandlers.UIEventHandler;
import ui.eventhandlers.addparty.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class AddPartyView implements ActionListener, ListSelectionListener {
    private int maxSize;

    private JFrame win;
    private JButton addPatron, newPatron, remPatron, finished;
    private JList partyList, allBowlers;
    private Vector party, bowlerdb;
    private Integer lock;

    private ControlDeskView controlDesk;

    private String selectedNick, selectedMember;

    private HashMap<JButton, UIEventHandler> eventHandlers;

    /**
     * Constructor of the ui.AddPartyView class.
     *
     * @param controlDesk the main control desk view.
     * @param max the max size of the party.
     */
    public AddPartyView(ControlDeskView controlDesk, int max){
        this.controlDesk = controlDesk;
        this.maxSize = max;

        // Create the frame.
        this.win = new JFrame("Add Party");
        this.win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) this.win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(1, 3));

        // Create the party Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Your Party"));

        this.party = new Vector();
        Vector empty = new Vector();
        empty.add("(Empty)");

        // Create the list.
        this.partyList = new JList(empty);
        this.partyList.setFixedCellWidth(120);
        this.partyList.setVisibleRowCount(5);
        this.partyList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(partyList);
        partyPanel.add(partyPane);

        // Create the bowler data view.
        JPanel bowlerPanel = new JPanel();
        bowlerPanel.setLayout(new FlowLayout());
        bowlerPanel.setBorder(new TitledBorder("Bowler Database"));

        try {
            bowlerdb = new Vector(BowlerFile.getBowlers());
        } catch (Exception e) {
            System.err.println("File Error");
            bowlerdb = new Vector();
        }
        this.allBowlers = new JList(this.bowlerdb);
        this.allBowlers.setVisibleRowCount(8);
        this.allBowlers.setFixedCellWidth(120);
        JScrollPane bowlerPane = new JScrollPane(this.allBowlers);
        bowlerPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.allBowlers.addListSelectionListener(this);
        bowlerPanel.add(bowlerPane);

        // BCreate the button panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        this.addPatron = new JButton("Add to Party");
        JPanel addPatronPanel = new JPanel();
        addPatronPanel.setLayout(new FlowLayout());
        this.addPatron.addActionListener(this);
        addPatronPanel.add(this.addPatron);

        this.remPatron = new JButton("Remove Member");
        JPanel remPatronPanel = new JPanel();
        remPatronPanel.setLayout(new FlowLayout());
        this.remPatron.addActionListener(this);
        remPatronPanel.add(this.remPatron);

        this.newPatron = new JButton("New Patron");
        JPanel newPatronPanel = new JPanel();
        newPatronPanel.setLayout(new FlowLayout());
        this.newPatron.addActionListener(this);
        newPatronPanel.add(this.newPatron);

        this.finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        this.finished.addActionListener(this);
        finishedPanel.add(this.finished);

        buttonPanel.add(addPatronPanel);
        buttonPanel.add(remPatronPanel);
        buttonPanel.add(newPatronPanel);
        buttonPanel.add(finishedPanel);

        // Clean up main panel.
        colPanel.add(partyPanel);
        colPanel.add(bowlerPanel);
        colPanel.add(buttonPanel);

        this.win.getContentPane().add("Center", colPanel);
        this.win.pack();

        // Center  the window on the screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        this.win.setLocation(
            ((screenSize.width) / 2) - ((win.getSize().width) / 2),
            ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        this.win.show();

        // Create the event handlers.
        this.eventHandlers = new HashMap<>();
        this.eventHandlers.put(this.addPatron,new AddPatronEventHandler(this));
        this.eventHandlers.put(this.remPatron,new RemovePatronEventHandler(this));
        this.eventHandlers.put(this.newPatron,new NewPatronEventHandler(this));
        this.eventHandlers.put(this.finished,new AddPartyFinishedEventHandler(this));
    }

    /**
     * Performs an action when a button is pressed.
     *
     * @param e the event that occurred.
     */
    public void actionPerformed(ActionEvent e) {
        // Get and run the event.
        UIEventHandler handler = this.eventHandlers.get(e.getSource());
        handler.handleEvent();
    }

    /**
     * Handler for List actions.
     *
     * @param e the ListActionEvent that triggered the handler.
     */
    public void valueChanged(ListSelectionEvent e){
        if (e.getSource().equals(this.allBowlers)) {
            this.selectedNick = ((String) ((JList) e.getSource()).getSelectedValue());
        }
        if (e.getSource().equals(this.partyList)) {
            this.selectedMember = ((String) ((JList) e.getSource()).getSelectedValue());
        }
    }

    /**
     * Returns the names part of the party.
     */
    public Vector getNames(){
        return this.party;
    }

    /**
     * Called by ui.NewPatronView to notify ui.AddPartyView to update.
     *
     * @param newPatron the ui.NewPatronView that called this method.
     */
    public void updateNewPatron(NewPatronView newPatron){
        try {
            Bowler checkBowler = BowlerFile.getBowlerInfo( newPatron.getNick() );
            if ( checkBowler == null ) {
                // Add the bowler info.
                BowlerFile.putBowlerInfo(
                    newPatron.getNick(),
                    newPatron.getFull(),
                    newPatron.getEmail());
                this.bowlerdb = new Vector(BowlerFile.getBowlers());
                this.allBowlers.setListData(this.bowlerdb);
                this.party.add(newPatron.getNick());
                this.partyList.setListData(this.party);
            } else {
                System.err.println( "A Bowler with that name already exists." );
            }
        } catch (Exception e2) {
            System.err.println("File I/O Error");
        }
    }

    /**
     * Returns the maximum size.
     */
    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * Returns the selected nickname.
     */
    public String getSelectedNickName() {
        return this.selectedNick;
    }

    /**
     * Returns the selected member.
     */
    public String getSelectedMember() {
        return this.selectedMember;
    }

    /**
     * Returns the party.
     */
    public Vector getParty(){
        return party;
    }

    /**
     * Returns the control desk view.
     */
    public ControlDeskView getControlDeskView() {
        return this.controlDesk;
    }

    /**
     * Returns the party list.
     */
    public JList getPartyList() {
        return this.partyList;
    }

    /**
     * Shows the window.
     */
    public void showWindow() {
        this.showWindow();
    }

    /**
     * Hides the window.
     */
    public void hideWindow() {
        this.win.hide();
    }
}