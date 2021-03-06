package ui;/* ui.ControlDeskView.java
 *
 *  Version:
 *			$Id$
 *
 *  Revisions:
 *      $Log$
 *
 */

/**
 * Class for representation of the control desk
 *
 */

import controller.ControlDesk;
import controller.ControlDeskEvent;
import controller.ControlDeskObserver;
import model.lane.Lane;
import model.pinsetter.Pinsetter;
import ui.AddPartyView;
import ui.LaneStatusView;
import ui.eventhandlers.UIEventHandler;
import ui.eventhandlers.controldesk.AddPartyEventHandler;
import ui.eventhandlers.controldesk.ControlDeskFinishedEventHandler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;

/**
 * This class controls the GUI aspect of the control desk. The control desk is how the user sets up the parties
 * and the game types. Uses Java swing and awt to create the visuals
 */

public class ControlDeskView implements ActionListener, ControlDeskObserver {

    private JButton addParty, finished, assign;
    private JFrame win;
    private JList partyList;

    /** The maximum  number of members in a party */
    private int maxMembers;

    private ControlDesk controlDesk;

    private HashMap<JButton, UIEventHandler> eventHandlers;

    /**
     * Displays a GUI representation of the controller.ControlDesk. Also delegates logic from buttons
     * to link over to other classes.
     *
     */

    public ControlDeskView(ControlDesk controlDesk, int maxMembers){

        this.controlDesk = controlDesk;
        this.maxMembers = maxMembers;
        int numLanes = controlDesk.getNumLanes();

        win = new JFrame("Control Desk");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new BorderLayout());

        // Controls Panel
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(3, 1));
        controlsPanel.setBorder(new TitledBorder("Controls"));

        addParty = new JButton("Add Party");
        JPanel addPartyPanel = new JPanel();
        addPartyPanel.setLayout(new FlowLayout());
        addParty.addActionListener(this);
        addPartyPanel.add(addParty);
        controlsPanel.add(addPartyPanel);

        assign = new JButton("Assign Lanes");
        JPanel assignPanel = new JPanel();
        assignPanel.setLayout(new FlowLayout());
        assign.addActionListener(this);
        assignPanel.add(assign);

        finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finished.addActionListener(this);
        finishedPanel.add(finished);
        controlsPanel.add(finishedPanel);

        // model.lane.Lane Status Panel
        JPanel laneStatusPanel = new JPanel();
        laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
        laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

        HashSet lanes=controlDesk.getLanes();
        Iterator it = lanes.iterator();
        int laneCount=0;
        while (it.hasNext()) {
            Lane curLane = (Lane) it.next();
            LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));
            curLane.subscribe(laneStat);
            ((Pinsetter)curLane.getPinsetter()).subscribe(laneStat);
            JPanel lanePanel = laneStat.showLane();
            lanePanel.setBorder(new TitledBorder("Lane" + ++laneCount ));
            laneStatusPanel.add(lanePanel);
        }

        // model.Party model.Queue Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Queue"));

        Vector empty = new Vector();
        empty.add("(Empty)");

        partyList = new JList(empty);
        partyList.setFixedCellWidth(120);
        partyList.setVisibleRowCount(10);
        JScrollPane partyPane = new JScrollPane(partyList);
        partyPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        partyPanel.add(partyPane);
        //		partyPanel.add(partyList);

        // Clean up main panel
        colPanel.add(controlsPanel, "East");
        colPanel.add(laneStatusPanel, "Center");
        colPanel.add(partyPanel, "West");

        win.getContentPane().add("Center", colPanel);

        win.pack();

        /* Close program when this window closes */
        win.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
            ((screenSize.width) / 2) - ((win.getSize().width) / 2),
            ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

        // Create the event handlers.
        this.eventHandlers = new HashMap<>();
        this.eventHandlers.put(this.addParty,new AddPartyEventHandler(this));
        this.eventHandlers.put(this.assign,null);
        this.eventHandlers.put(this.finished,new ControlDeskFinishedEventHandler(this));
    }

    /**
     * Handler for actionEvents
     *
     * @param e	the ActionEvent that triggered the handler
     *
     */

    public void actionPerformed(ActionEvent e){
        // Get and run the event.
        UIEventHandler handler = this.eventHandlers.get(e.getSource());
        handler.handleEvent();
    }

    /**
     * Receive a new party from andPartyView.
     *
     * @param addPartyView	the ui.AddPartyView that is providing a new party
     *
     */

    public void updateAddParty(AddPartyView addPartyView){
        controlDesk.addPartyQueue(addPartyView.getParty());
    }

    /**
     * Receive a broadcast from a controller.ControlDesk
     *
     * @param ce	the controller.ControlDeskEvent that triggered the handler
     *
     */

    public void receiveControlDeskEvent(ControlDeskEvent ce){
        partyList.setListData(((Vector) ce.getPartyQueue()));
    }

    /**
     * Returns the control desk.
     */
    public ControlDesk getControlDesk() {
        return this.controlDesk;
    }

    /**
     * Returns the maximum size.
     */
    public int getMaxMembers() {
        return this.maxMembers;
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
