import java.util.Vector;

/**
 * Handles an event for handling adding a patron.
 *
 * @author Zachary Cook
 */
public class AddPatronEventHandler implements UIEventHandler {
    private AddPartyView addPartyView;

    /**
     * Creates the event.
     *
     * @param addPartyView the add party view to reference to.
     */
    public AddPatronEventHandler(AddPartyView addPartyView) {
        this.addPartyView = addPartyView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        // Add the patron a nickname is selected.
        Vector party = this.addPartyView.getParty();
        String selectedNickName = this.addPartyView.getSelectedNickName();
        if (selectedNickName != null && party.size() < this.addPartyView.getMaxSize()) {
            if (party.contains(selectedNickName)) {
                System.err.println("Member already in Party");
            } else {
                party.add(selectedNickName);
                this.addPartyView.getPartyList().setListData(party);
            }
        }
    }
}
