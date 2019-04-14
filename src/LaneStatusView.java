/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * The graphical view of the laneStatus window that the user is viewing. This creates and updates the view whenever a new game is
 * started. Receives lane events. This is the class that acts as a reciever for action events, lane events
 * and pinsetter events.
 */
public class LaneStatusView implements ActionListener, LaneObserver, PinsetterObserver {

    private JPanel jp;

    private JLabel curBowler, foul, pinsDown;
    private JButton viewLane;
    private JButton viewPinSetter, maintenance;

    private PinSetterView psv;
    private LaneView lv;
    private Lane lane;
    int laneNum;

    boolean laneShowing;
    boolean psShowing;

    /**
     * This method stores the lane and lane number that this object will represent, and creates
     * the GUI for the lane. Lane name and players will be updated when an event created for this.
     * Delegates button functionality as well
     * @param lane the lane object this object is subscribed to
     * @param laneNum the lane number this object is subscribed to
     */
    public LaneStatusView(Lane lane, int laneNum ){

        this.lane = lane;
        this.laneNum = laneNum;

        laneShowing=false;
        psShowing=false;

        psv = new PinSetterView( laneNum );
        Pinsetter ps = lane.getPinsetter();
        ps.subscribe(psv);

        lv = new LaneView( lane, laneNum );
        lane.subscribe(lv);


        jp = new JPanel();
        jp.setLayout(new FlowLayout());
        JLabel cLabel = new JLabel( "Now Bowling: " );
        curBowler = new JLabel( "(no one)" );
        JLabel fLabel = new JLabel( "Foul: " );
        foul = new JLabel( " " );
        JLabel pdLabel = new JLabel( "Pins Down: " );
        pinsDown = new JLabel( "0" );

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        viewLane = new JButton("View Lane");
        JPanel viewLanePanel = new JPanel();
        viewLanePanel.setLayout(new FlowLayout());
        viewLane.addActionListener(this);
        viewLanePanel.add(viewLane);

        viewPinSetter = new JButton("Pinsetter");
        JPanel viewPinSetterPanel = new JPanel();
        viewPinSetterPanel.setLayout(new FlowLayout());
        viewPinSetter.addActionListener(this);
        viewPinSetterPanel.add(viewPinSetter);

        maintenance = new JButton("     ");
        maintenance.setBackground( Color.GREEN );
        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setLayout(new FlowLayout());
        maintenance.addActionListener(this);
        maintenancePanel.add(maintenance);

        viewLane.setEnabled( false );
        viewPinSetter.setEnabled( false );


        buttonPanel.add(viewLanePanel);
        buttonPanel.add(viewPinSetterPanel);
        buttonPanel.add(maintenancePanel);

        jp.add( cLabel );
        jp.add( curBowler );
//		jp.add( fLabel );
//		jp.add( foul );
        jp.add( pdLabel );
        jp.add( pinsDown );

        jp.add(buttonPanel);

    }

    /**
     * Shows the collection of jswing objects that depict the gui of this lane
     * @return JPane created above
     */
    public JPanel showLane(){
        return jp;
    }

    /**
     * Recieves events on the pane, if any match certain determined events, such as
     * clicking on the viewpinsetter button, or view lane, then this method shows, hides, or
     * determines functionality to be done.
     * @param e the action event to be recieved
     */
    public void actionPerformed( ActionEvent e ){
        if ( lane.isPartyAssigned() ) {
            if (e.getSource().equals(viewPinSetter)) {
                if ( psShowing == false ) {
                    psv.show();
                    psShowing=true;
                } else if ( psShowing == true ) {
                    psv.hide();
                    psShowing=false;
                }
            }
        }
        if (e.getSource().equals(viewLane)) {
            if ( lane.isPartyAssigned() ) {
                if ( laneShowing == false ) {
                    lv.show();
                    laneShowing=true;
                } else if ( laneShowing == true ) {
                    lv.hide();
                    laneShowing=false;
                }
            }
        }
        if (e.getSource().equals(maintenance)) {
            if ( lane.isPartyAssigned() ) {
                lane.unPauseGame();
                maintenance.setBackground( Color.GREEN );
            }
        }
    }

    /**
     * This method controls what happens when this class recieves a lane event. If this lane has no party before
     * the event, then this lane is named and enabled.
     * @param le the lane event to be handled
     */
    public void receiveLaneEvent(LaneEvent le){
        curBowler.setText( ( (Bowler)le.getBowler()).getNickName() );
        if ( le.isMechanicalProblem() ) {
            maintenance.setBackground( Color.RED );
        }
        if ( lane.isPartyAssigned() == false ) {
            viewLane.setEnabled( false );
            viewPinSetter.setEnabled( false );
        } else {
            viewLane.setEnabled( true );
            viewPinSetter.setEnabled( true );
        }
    }

    /**
     * When a pinsetter event is received, it updates the pinsdown variable of this class
     * @param pe the pinsetter event received
     */
    public void receivePinsetterEvent(PinsetterEvent pe){
        pinsDown.setText( ( new Integer(pe.totalPinsDown()) ).toString() );
//		foul.setText( ( new Boolean(pe.isFoulCommited()) ).toString() );

    }

}
