package controller; /**
 * Class that represents control desk
 */
import java.util.*;
import java.io.*;

import model.Bowler;
import model.BowlerFile;
import model.Party;
import model.Queue;
import model.Lane.Lane;

public class ControlDesk extends Thread {
    /** The collection of Lanes */
    private HashSet lanes;

    /** The party wait queue */
    private Queue partyQueue;

    /** The number of lanes represented */
    private int numLanes;

    /** The collection of subscribers */
    private Vector subscribers;

    /**
     * Constructor for the controller.ControlDesk class.
     *
     * @param numLanes the numbber of lanes to be represented.
     *
     */
    public ControlDesk(int numLanes){
        this.numLanes = numLanes;
        this.lanes = new HashSet(numLanes);
        this.partyQueue = new Queue();
        this.subscribers = new Vector();

        // Create the lanes.
        for (int i = 0; i < numLanes; i++) {
            lanes.add(new Lane());
        }

        // Start the thread.
        this.start();

    }

    /**
     * Main loop for controller.ControlDesk's thread.
     */
    public void run(){
        while (true) {
            assignLane();
            try {
                sleep(250);
            } catch (Exception e) {}
        }
    }


    /**
     * Retrieves a matching model.Bowler from the bowler database.
     *
     * @param nickName The NickName of the model.Bowler
     * @return a model.Bowler object.
     */
    private Bowler registerPatron(String nickName){
        Bowler patron = null;

        try {
            // Get the patron.
            patron = BowlerFile.getBowlerInfo(nickName);
        } catch (FileNotFoundException e) {
            System.err.println("Error..." + e);
        } catch (IOException e) {
            System.err.println("Error..." + e);
        }

        // Return the patron.
        return patron;
    }

    /**
     * Iterate through the available lanes and assign the parties in the wait queue if lanes are available.
     */
    public void assignLane(){
        Iterator it = lanes.iterator();

        // Assign the lane.
        while (it.hasNext() && partyQueue.hasMoreElements()) {
            Lane curLane = (Lane) it.next();

            if (curLane.isPartyAssigned() == false) {
                System.out.println("ok... assigning this party");
                curLane.assignParty(((Party) partyQueue.next()));
            }
        }

        // Publish an event.
        publish(new ControlDeskEvent(getPartyQueue()));
    }

    /**
     * Views the scores of the given lane.
     *
     * @param ln the lane to view.
     */
    public void viewScores(Lane ln){
        // TODO: attach a LaneScoreView object to that lane
    }

    /**
     * Creates a party from a Vector of nickNames and adds them to the wait queue.
     *
     * @param partyNicks	A Vector of NickNames.
     */
    public void addPartyQueue(Vector partyNicks){
        // Create the names.
        Vector partyBowlers = new Vector();
        for (int i = 0; i < partyNicks.size(); i++) {
            Bowler newBowler = registerPatron(((String) partyNicks.get(i)));
            partyBowlers.add(newBowler);
        }

        // Create the party and add it to the queue.
        Party newParty = new Party(partyBowlers);
        this.partyQueue.add(newParty);
        publish(new ControlDeskEvent(getPartyQueue()));
    }

    /**
     * Returns a Vector of party names to be displayed in the GUI representation of the wait queue.
     *
     * @return a Vector of Strings.
     */
    public Vector getPartyQueue(){
        // Add the parties to the queue.
        Vector displayPartyQueue = new Vector();
        for ( int i=0; i < ((Vector) this.partyQueue.asVector()).size(); i++ ) {
            String nextParty = ((Bowler) ((Vector) ((Party) this.partyQueue.asVector().get( i ) ).getMembers()).get(0)).getNickName() + "'s model.Party";
            displayPartyQueue.addElement(nextParty);
        }

        // Return the queue.
        return displayPartyQueue;
    }

    /**
     * Accessor for the number of lanes represented by the controller.ControlDesk.
     *
     * @return an int containing the number of lanes represented.
     */
    public int getNumLanes(){
        return this.numLanes;
    }

    /**
     * Allows objects to subscribe as observers.
     *
     * @param adding	the controller.ControlDeskObserver that will be subscribed.
     */
    public void subscribe(ControlDeskObserver adding){
        this.subscribers.add(adding);
    }

    /**
     * Broadcast an event to subscribing objects.
     *
     * @param event	the controller.ControlDeskEvent to broadcast.
     */
    public void publish(ControlDeskEvent event){
        Iterator eventIterator = subscribers.iterator();
        while (eventIterator.hasNext()) {
            ((ControlDeskObserver) eventIterator.next()).receiveControlDeskEvent(event);
        }
    }

    /**
     * Returns the lanes.
     *
     * @return the lanes.
     *
     */
    public HashSet getLanes(){
        return lanes;
    }
}
