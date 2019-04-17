import java.util.Vector;

/**
 * Handles an event for handling removing a patron.
 *
 * @author Zachary Cook
 */
public class RemovePatronEventHandler implements UIEventHandler {
    private AddPartyView addPartyView;

    /**
     * Creates the event.
     *
     * @param addPartyView the add party view to reference to.
     */
    public RemovePatronEventHandler(AddPartyView addPartyView) {
        this.addPartyView = addPartyView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        // Remove the selected member.
        Vector party = this.addPartyView.getParty();
        String selectedMember = this.addPartyView.getSelectedMember();
        if (selectedMember != null) {
            party.removeElement(selectedMember);
            this.addPartyView.getPartyList().setListData(party);
        }
    }
}
