package ui; /**
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
 * This is a class to represent the popup that shows when a game has ended. This gives the user
 * ability to continue bowlingAPI, or end the game and go back to the control desk.
 */
public class EndGamePrompt implements ActionListener {

    private JFrame win;
    private JButton yesButton, noButton;

    private int result;

    private String selectedNick, selectedMember;

    /**
     * This constructor creates the visual display and delegates what the buttons on the
     * prompt do.
     *
     * @param partyName the name of the party whose game has ended
     */
    public EndGamePrompt( String partyName ){

        result =0;

        win = new JFrame("Another Game for " + partyName + "?" );
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout( 2, 1 ));

        // Label Panel
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout());

        JLabel message = new JLabel( "Party " + partyName
                                     + " has finished bowling.\nWould they like to bowl another game?" );

        labelPanel.add( message );

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        yesButton = new JButton("Yes");
        JPanel yesButtonPanel = new JPanel();
        yesButtonPanel.setLayout(new FlowLayout());
        yesButton.addActionListener(this);
        yesButtonPanel.add(yesButton);

        noButton = new JButton("No");
        JPanel noButtonPanel = new JPanel();
        noButtonPanel.setLayout(new FlowLayout());
        noButton.addActionListener(this);
        noButtonPanel.add(noButton);

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Clean up main panel
        colPanel.add(labelPanel);
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
     * This method is called when there is an action performed on the prompt. If it is the yes button
     * being clicked, the result is 1, if it is the no button, the result is 2. This then is sent to the
     * control desk so that it knows whether the game is over or not
     *
     * @param e the action event on screen
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(yesButton)) {
            result=1;
        }
        if (e.getSource().equals(noButton)) {
            result=2;
        }

    }

    /**
     * This method is called while the class is waiting for an event to occurr. This method returns a result
     * when an action is completed, such as the yes or no button being clicked
     *
     * @return returns the value of the event result
     */
    public int getResult(){
        while ( result == 0 ) {
            try {
                Thread.sleep(10);
            } catch ( InterruptedException e ) {
                System.err.println( "Interrupted" );
            }
        }
        return result;
    }

    /**
     * This method is called to hide the prompt when an action is successfully completed
     */
    public void distroy(){
        win.hide();
    }

}

