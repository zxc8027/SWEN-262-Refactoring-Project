/**
 * Handles an event for handling creating a patron.
 *
 * @author Zachary Cook
 */
public class NewPatronEventHandler implements UIEventHandler {
    private AddPartyView addPartyView;

    /**
     * Creates the event.
     *
     * @param addPartyView the add party view to reference to.
     */
    public NewPatronEventHandler(AddPartyView addPartyView) {
        this.addPartyView = addPartyView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        // Create the window.
        new NewPatronView(this.addPartyView);
    }
}
