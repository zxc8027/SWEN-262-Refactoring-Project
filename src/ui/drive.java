package ui;

import controller.Alley;
import controller.ControlDesk;

/**
 * This class is the main launching class. This is how you run the main program. As far as we know
 * there are no special console commands. This will generate the control desk to begin and plan games of
 * bowling. Static number of lanes and people per party. Generates alleys.
 */
public class drive {

    /**
     * Main execution function, takes a string of command line arguments, still unclear to us.
     * Creates an alley object with 3 lanes, and grabs the control desk from it.
     * Uses the control desk to create the graphical display that will be shown to the user,
     * and "subscribes" that ui to the control desk. This means the view will be updated with
     * that controldesks' operations
     *
     * @param args arguments to be passed in from command line
     */
    public static void main(String[] args){

        int numLanes = 3;
        int maxPatronsPerParty=5;

        Alley a = new Alley( numLanes );
        ControlDesk controlDesk = a.getControlDesk();

        ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
        controlDesk.subscribe( cdv );

    }
}
