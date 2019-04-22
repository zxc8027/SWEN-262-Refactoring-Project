package controller;/* controller.ControlDeskObserver.java
 *
 *  Version
 *  $Id$
 *
 *  Revisions:
 *      $Log$
 *
 */

/**
 * Interface for classes that observe control desk events
 *
 */

public interface ControlDeskObserver {

    /**
     * this method will be implemented so it handles an event received from the control desk.
     *
     * @param ce the event to be received and processed
     */
    public void receiveControlDeskEvent(ControlDeskEvent ce);

}
