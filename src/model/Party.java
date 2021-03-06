package model;/*
 * model.Party.java
 *
 * Version:
 *   $Id$
 *
 * Revisions:
 *   $Log: model.Party.java,v $
 *   Revision 1.3  2003/02/09 21:21:31  ???
 *   Added lots of comments
 *
 *   Revision 1.2  2003/01/12 22:23:32  ???
 *   *** empty log message ***
 *
 *   Revision 1.1  2003/01/12 19:09:12  ???
 *   Adding model.Party, model.lane.Lane, model.Bowler, and controller.Alley.
 *
 */

/**
 *  Container that holds bowlers
 *
 */

import java.util.*;

/**
 * This class represents a party of bowlers.
 */
public class Party {

    /** Vector of bowlers in this party */
    private Vector myBowlers;

    /**
     * Constructor for a model.Party
     *
     * @param bowlers	Vector of bowlers that are in this party
     */

    public Party( Vector bowlers ){
        myBowlers = new Vector(bowlers);
    }

    /**
     * Accessor for members in this party
     *
     * @return  A vector of the bowlers in this party
     */

    public Vector getMembers(){
        return myBowlers;
    }

}
