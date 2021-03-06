package ui.eventhandlers.controldesk;

import ui.AddPartyView;
import ui.ControlDeskView;
import ui.eventhandlers.UIEventHandler;

/**
 * Handles an event for adding a party.
 *
 * @author Zachary Cook
 */
public class AddPartyEventHandler implements UIEventHandler {
    private ControlDeskView controlDeskView;

    /**
     * Creates the event.
     *
     * @param controlDeskView the control desk view to reference to.
     */
    public AddPartyEventHandler(ControlDeskView controlDeskView) {
        this.controlDeskView = controlDeskView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        new AddPartyView(this.controlDeskView,this.controlDeskView.getMaxMembers());
    }
}