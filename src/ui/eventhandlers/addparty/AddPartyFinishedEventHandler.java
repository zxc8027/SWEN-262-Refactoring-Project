package ui.eventhandlers.addparty;

import ui.AddPartyView;
import ui.eventhandlers.UIEventHandler;

import java.util.Vector;

/**
 * Handles an event for handling being done.
 *
 * @author Zachary Cook
 */
public class AddPartyFinishedEventHandler implements UIEventHandler {
    private AddPartyView addPartyView;

    /**
     * Creates the event.
     *
     * @param addPartyView the add party view to reference to.
     */
    public AddPartyFinishedEventHandler(AddPartyView addPartyView) {
        this.addPartyView = addPartyView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        // Update the party.
        Vector party = this.addPartyView.getParty();
        if (party != null && party.size() > 0) {
            this.addPartyView.getControlDeskView().updateAddParty(this.addPartyView);
        }

        // Close the window.
        this.addPartyView.hideWindow();
    }
}