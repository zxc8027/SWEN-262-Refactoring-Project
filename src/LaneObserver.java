/* $Id$
 *
 * Revisions:
 *   $Log: LaneObserver.java,v $
 *   Revision 1.2  2003/01/30 21:44:25  ???
 *   Fixed speling of received in may places.
 *
 *   Revision 1.1  2003/01/19 22:12:40  ???
 *   created laneevent and laneobserver
 *
 *
 */

/**
 * This interface gives each lane observer the method to recieve a lane event.
 */
public interface LaneObserver {
    /**
     * This method forces functionality to be built in to each class that implements this interface
     * @param le the lane event to be handled
     */
    public void receiveLaneEvent(LaneEvent le);
};

