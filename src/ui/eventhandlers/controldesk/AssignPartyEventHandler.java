package ui.eventhandlers.controldesk;

import ui.ControlDeskView;
import ui.eventhandlers.UIEventHandler;

/**
 * Handles an event for assigning a party.
 *
 * @author Zachary Cook
 */
public class AssignPartyEventHandler implements UIEventHandler {
    private ControlDeskView controlDeskView;

    /**
     * Creates the event.
     *
     * @param controlDeskView the control desk view to reference to.
     */
    public AssignPartyEventHandler(ControlDeskView controlDeskView) {
        this.controlDeskView = controlDeskView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        this.controlDeskView.getControlDesk().assignLane();
    }
}