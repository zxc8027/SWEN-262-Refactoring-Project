/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.text.*;

/**
 * This class represents the report shown to the user when the game is over. It shows the party members,
 * gives the user the option to print the report, or finish the game.
 */
public class EndGameReport implements ActionListener, ListSelectionListener {

    private JFrame win;
    private JButton printButton, finished;
    private JList memberList;
    private Vector myVector;
    private Vector retVal;

    private int result;

    private String selectedMember;

    /**
     * This creates the visual layout for the end of game report, and processes information for it.
     * Also delegates button responsibility to other methods in this class.
     *
     * @param partyName the name of the party of the most recently completed game
     * @param party the players in the party of the most recently completed game
     */
    public EndGameReport( String partyName, Party party ){

        result =0;
        retVal = new Vector();
        win = new JFrame("End Game Report for " + partyName + "?" );
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout( 1, 2 ));

        // Member Panel
        JPanel partyPanel = new JPanel();
        partyPanel.setLayout(new FlowLayout());
        partyPanel.setBorder(new TitledBorder("Party Members"));

        Vector myVector = new Vector();
        Iterator iter = (party.getMembers()).iterator();
        while (iter.hasNext()) {
            myVector.add( ((Bowler)iter.next()).getNick() );
        }
        memberList = new JList(myVector);
        memberList.setFixedCellWidth(120);
        memberList.setVisibleRowCount(5);
        memberList.addListSelectionListener(this);
        JScrollPane partyPane = new JScrollPane(memberList);
        partyPanel.add(partyPane);

        partyPanel.add( memberList );

        // Button Panel
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        printButton = new JButton("Print Report");
        JPanel printButtonPanel = new JPanel();
        printButtonPanel.setLayout(new FlowLayout());
        printButton.addActionListener(this);
        printButtonPanel.add(printButton);

        finished = new JButton("Finished");
        JPanel finishedPanel = new JPanel();
        finishedPanel.setLayout(new FlowLayout());
        finished.addActionListener(this);
        finishedPanel.add(finished);

        buttonPanel.add(printButton);
        buttonPanel.add(finished);

        // Clean up main panel
        colPanel.add(partyPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
            ((screenSize.width) / 2) - ((win.getSize().width) / 2),
            ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    /**
     * This method is called when an event is performed on the report. Takes the report type
     * and checks to see if either the print or finish button was clicked. If they were, then it sets
     * the result to predetermined values to be fetched later.
     *
     * @param e the event completed
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(printButton)) {
            //Add selected to the vector.
            retVal.add(selectedMember);
        }
        if (e.getSource().equals(finished)) {
            win.hide();
            result = 1;
        }

    }

    /**
     * This method is called when a certain type of event is done, where a value is changed.
     * Updates the selectedMember value with the new value
     *
     * @param e the event that was made
     */
    public void valueChanged(ListSelectionEvent e){
        selectedMember =
            ((String) ((JList) e.getSource()).getSelectedValue());
    }

    /**
     * This method is called to fetch the result of the action events on the screen.
     * If neither button was clicked, then the page stays active, otherwise, the thread returns the
     * value and then is hidden.
     *
     * @return the result of the event
     */
    public Vector getResult(){
        while ( result == 0 ) {
            try {
                Thread.sleep(10);
            } catch ( InterruptedException e ) {
                System.err.println( "Interrupted" );
            }
        }
        return retVal;
    }

    /**
     * This method is used to hide this graphic
     */
    public void destroy(){
        win.hide();
    }
}

