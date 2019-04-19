package ui.EventHandlers.ControlDesk;

import ui.ControlDeskView;
import ui.EventHandlers.UIEventHandler;

/**
 * Handles an event for finishing the control desk view.
 *
 * @author Zachary Cook
 */
public class ControlDeskFinishedEventHandler implements UIEventHandler {
    private ControlDeskView controlDeskView;

    /**
     * Creates the event.
     *
     * @param controlDeskView the control desk view to reference to.
     */
    public ControlDeskFinishedEventHandler(ControlDeskView controlDeskView) {
        this.controlDeskView = controlDeskView;
    }

    /**
     * Handles the event.
     */
    public void handleEvent() {
        this.controlDeskView.hideWindow();
        System.exit(0);
    }
}