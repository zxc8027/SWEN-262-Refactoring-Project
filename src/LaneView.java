/*
 *  constructs a prototype Lane View
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * This class represents the GUI that shows the score of each of the players and which frame they are in.
 * Uses updates from the lane to change what scores are showing. This view is shown when the user clicks on the
 * ViewLane button on the lane status page.
 */
public class LaneView implements LaneObserver, ActionListener {

    private int roll;
    private boolean initDone = true;

    JFrame frame;
    Container cpanel;
    Vector bowlers;
    int cur;
    Iterator bowlIt;

    JPanel[][] balls;
    JLabel[][] ballLabel;
    JPanel[][] scores;
    JLabel[][] scoreLabel;
    JPanel[][] ballGrid;
    JPanel[] pins;

    JButton maintenance;
    Lane lane;

    /**
     * This constructs the lane view, it takes the lane and the lane number so that it can label itself
     * correctly
     *
     * @param lane the lane object it will be observing
     * @param laneNum the lane number the lane is
     */
    public LaneView(Lane lane, int laneNum){

        this.lane = lane;

        initDone = true;
        frame = new JFrame("Lane " + laneNum + ":");
        cpanel = frame.getContentPane();
        cpanel.setLayout(new BorderLayout());

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.hide();
            }
        });

        cpanel.add(new JPanel());

    }

    /**
     * This method shows this window when called.
     */
    public void show(){
        frame.show();
    }

    /**
     * This method hides this window when called
     */
    public void hide(){
        frame.hide();
    }

    /**
     * This method creates the layout of the graphics that this window holds. It is large because it
     * has to create some pretty fancy looking score cards.
     *
     * @param party the party that will be bowling
     * @return returns the jpanel so it can be displayed
     */
    private JPanel makeFrame(Party party){

        initDone = false;
        bowlers = party.getMembers();
        int numBowlers = bowlers.size();

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(0, 1));

        balls = new JPanel[numBowlers][23];
        ballLabel = new JLabel[numBowlers][23];
        scores = new JPanel[numBowlers][10];
        scoreLabel = new JLabel[numBowlers][10];
        ballGrid = new JPanel[numBowlers][10];
        pins = new JPanel[numBowlers];

        for (int i = 0; i != numBowlers; i++) {
            for (int j = 0; j != 23; j++) {
                ballLabel[i][j] = new JLabel(" ");
                balls[i][j] = new JPanel();
                balls[i][j].setBorder(
                    BorderFactory.createLineBorder(Color.BLACK));
                balls[i][j].add(ballLabel[i][j]);
            }
        }

        for (int i = 0; i != numBowlers; i++) {
            for (int j = 0; j != 9; j++) {
                ballGrid[i][j] = new JPanel();
                ballGrid[i][j].setLayout(new GridLayout(0, 3));
                ballGrid[i][j].add(new JLabel("  "), BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j], BorderLayout.EAST);
                ballGrid[i][j].add(balls[i][2 * j + 1], BorderLayout.EAST);
            }
            int j = 9;
            ballGrid[i][j] = new JPanel();
            ballGrid[i][j].setLayout(new GridLayout(0, 3));
            ballGrid[i][j].add(balls[i][2 * j]);
            ballGrid[i][j].add(balls[i][2 * j + 1]);
            ballGrid[i][j].add(balls[i][2 * j + 2]);
        }

        for (int i = 0; i != numBowlers; i++) {
            pins[i] = new JPanel();
            pins[i].setBorder(
                BorderFactory.createTitledBorder(
                    ((Bowler) bowlers.get(i)).getNick()));
            pins[i].setLayout(new GridLayout(0, 10));
            for (int k = 0; k != 10; k++) {
                scores[i][k] = new JPanel();
                scoreLabel[i][k] = new JLabel("  ", SwingConstants.CENTER);
                scores[i][k].setBorder(
                    BorderFactory.createLineBorder(Color.BLACK));
                scores[i][k].setLayout(new GridLayout(0, 1));
                scores[i][k].add(ballGrid[i][k], BorderLayout.EAST);
                scores[i][k].add(scoreLabel[i][k], BorderLayout.SOUTH);
                pins[i].add(scores[i][k], BorderLayout.EAST);
            }
            panel.add(pins[i]);
        }

        initDone = true;
        return panel;
    }

    /**
     * This method updates the players scores when the lane event is received. If the lane has a party assigned, then this
     *
     * @param le the lane event to be handled
     */
    public void receiveLaneEvent(LaneEvent le){
        if (lane.isPartyAssigned()) {
            int numBowlers = le.getParty().getMembers().size();
            while (!initDone) {
                //System.out.println("chillin' here.");
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                }
            }

            if (le.getFrameNum() == 1 // if the frame is the first frame
                && le.getBall() == 0
                && le.getIndex() == 0) {
                System.out.println("Making the frame.");
                cpanel.removeAll();
                cpanel.add(makeFrame(le.getParty()), "Center");

                // Button Panel
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());

                Insets buttonMargin = new Insets(4, 4, 4, 4);

                maintenance = new JButton("Maintenance Call");
                JPanel maintenancePanel = new JPanel();
                maintenancePanel.setLayout(new FlowLayout());
                maintenance.addActionListener(this);
                maintenancePanel.add(maintenance);

                buttonPanel.add(maintenancePanel);

                cpanel.add(buttonPanel, "South");

                frame.pack();

            }

            int[][] lescores = le.getCumulScore();
            for (int k = 0; k < numBowlers; k++) {
                for (int i = 0; i <= le.getFrameNum() - 1; i++) {
                    if (lescores[k][i] != 0)
                        scoreLabel[k][i].setText(
                            (new Integer(lescores[k][i])).toString());
                }
                for (int i = 0; i < 21; i++) {
                    if (((int[]) ((HashMap) le.getScore())
                         .get(bowlers.get(k)))[i]
                        != -1)
                        if (((int[]) ((HashMap) le.getScore())
                             .get(bowlers.get(k)))[i]
                            == 10
                            && (i % 2 == 0 || i == 19))
                            ballLabel[k][i].setText("X");
                        else if (
                            i > 0
                            && ((int[]) ((HashMap) le.getScore())
                                .get(bowlers.get(k)))[i]
                            + ((int[]) ((HashMap) le.getScore())
                               .get(bowlers.get(k)))[i
                                                     - 1]
                            == 10
                            && i % 2 == 1)
                            ballLabel[k][i].setText("/");
                        else if ( ((int[])((HashMap) le.getScore()).get(bowlers.get(k)))[i] == -2 ) {

                            ballLabel[k][i].setText("F");
                        } else
                            ballLabel[k][i].setText(
                                (new Integer(((int[]) ((HashMap) le.getScore())
                                              .get(bowlers.get(k)))[i]))
                                .toString());
                }
            }

        }
    }

    /**
     * If there is a click and the click is on the maintenance button, then the game is paused.
     * @param e the action event that was recieved.
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(maintenance)) {
            lane.pauseGame();
        }
    }

}
