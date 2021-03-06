package controller;

/**
 *  Class that is the outer container for the bowlingAPI simulator.
 */
public class Alley {
    public ControlDesk controldesk;

    /**
     * Creates the alley.
     *
     * @param numLanes the number of lanes.
     */
    public Alley( int numLanes ){
        this.controldesk = new ControlDesk( numLanes );
    }

    /**
     * Returns the control desk.
     *
     * @return the control desk.
     */
    public ControlDesk getControlDesk(){
        return this.controldesk;
    }

}



