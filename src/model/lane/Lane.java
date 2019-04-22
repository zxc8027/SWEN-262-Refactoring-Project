package model.lane;
/* $Id$
 *
 * Revisions:
 *   $Log: model.lane.Lane.java,v $
 *   Revision 1.52  2003/02/20 20:27:45  ???
 *   Fouls disables.
 *
 *   Revision 1.51  2003/02/20 20:01:32  ???
 *   Added things.
 *
 *   Revision 1.50  2003/02/20 19:53:52  ???
 *   Added foul support.  Still need to update laneview and test this.
 *
 *   Revision 1.49  2003/02/20 11:18:22  ???
 *   Works beautifully.
 *
 *   Revision 1.48  2003/02/20 04:10:58  ???
 *   model.Score reporting code should be good.
 *
 *   Revision 1.47  2003/02/17 00:25:28  ???
 *   Added disbale controls for View objects.
 *
 *   Revision 1.46  2003/02/17 00:20:47  ???
 *   fix for event when game ends
 *
 *   Revision 1.43  2003/02/17 00:09:42  ???
 *   fix for event when game ends
 *
 *   Revision 1.42  2003/02/17 00:03:34  ???
 *   Bug fixed
 *
 *   Revision 1.41  2003/02/16 23:59:49  ???
 *   Reporting of sorts.
 *
 *   Revision 1.40  2003/02/16 23:44:33  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.39  2003/02/16 23:43:08  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.38  2003/02/16 23:41:05  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.37  2003/02/16 23:00:26  ???
 *   added mechnanical problem flag
 *
 *   Revision 1.36  2003/02/16 21:31:04  ???
 *   model.Score logging.
 *
 *   Revision 1.35  2003/02/09 21:38:00  ???
 *   Added lots of comments
 *
 *   Revision 1.34  2003/02/06 00:27:46  ???
 *   Fixed a race condition
 *
 *   Revision 1.33  2003/02/05 11:16:34  ???
 *   Boom-Shacka-Lacka!!!
 *
 *   Revision 1.32  2003/02/05 01:15:19  ???
 *   Real close now.  Honest.
 *
 *   Revision 1.31  2003/02/04 22:02:04  ???
 *   Still not quite working...
 *
 *   Revision 1.30  2003/02/04 13:33:04  ???
 *   model.lane.Lane may very well work now.
 *
 *   Revision 1.29  2003/02/02 23:57:27  ???
 *   fix on pinsetter hack
 *
 *   Revision 1.28  2003/02/02 23:49:48  ???
 *   model.pinsetter.Pinsetter generates an event when all pins are reset
 *
 *   Revision 1.27  2003/02/02 23:26:32  ???
 *   ControlDesk now runs its own thread and polls for free lanes to assign queue members to
 *
 *   Revision 1.26  2003/02/02 23:11:42  ???
 *   parties can now play more than 1 game on a lane, and lanes are properly released after games
 *
 *   Revision 1.25  2003/02/02 22:52:19  ???
 *   model.lane.Lane compiles
 *
 *   Revision 1.24  2003/02/02 22:50:10  ???
 *   model.lane.Lane compiles
 *
 *   Revision 1.23  2003/02/02 22:47:34  ???
 *   More observering.
 *
 *   Revision 1.22  2003/02/02 22:15:40  ???
 *   Add accessor for pinsetter.
 *
 *   Revision 1.21  2003/02/02 21:59:20  ???
 *   added conditions for the party choosing to play another game
 *
 *   Revision 1.20  2003/02/02 21:51:54  ???
 *   model.lane.LaneEvent may very well be observer method.
 *
 *   Revision 1.19  2003/02/02 20:28:59  ???
 *   fixed sleep thread bug in lane
 *
 *   Revision 1.18  2003/02/02 18:18:51  ???
 *   more changes. just need to fix scoring.
 *
 *   Revision 1.17  2003/02/02 17:47:02  ???
 *   Things are pretty close to working now...
 *
 *   Revision 1.16  2003/01/30 22:09:32  ???
 *   Worked on scoring.
 *
 *   Revision 1.15  2003/01/30 21:45:08  ???
 *   Fixed speling of received in model.lane.Lane.
 *
 *   Revision 1.14  2003/01/30 21:29:30  ???
 *   Fixed some MVC stuff
 *
 *   Revision 1.13  2003/01/30 03:45:26  ???
 *   *** empty log message ***
 *
 *   Revision 1.12  2003/01/26 23:16:10  ???
 *   Improved thread handeling in lane/controldesk
 *
 *   Revision 1.11  2003/01/26 22:34:44  ???
 *   Total rewrite of lane and pinsetter for R2's observer model
 *   Added model.lane.Lane/model.pinsetter.Pinsetter Observer
 *   Rewrite of scoring algorythm in lane
 *
 *   Revision 1.10  2003/01/26 20:44:05  ???
 *   small changes
 *
 *
 */

import controller.ScoringAPIController;
import model.Bowler;
import model.Party;
import model.ScoreHistoryFile;
import model.pinsetter.Pinsetter;
import model.pinsetter.PinsetterEvent;
import model.pinsetter.PinsetterObserver;
import ui.EndGamePrompt;
import ui.EndGameReport;
import ui.ScoreReport;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Date;




/**
 * This class represents the entirety of a single late. Keeps track of each turn made,
 * the current players, the status of the game, and all sorts of other stuff. This is too big a class.
 * Will be refactored into a pattern and subclasses to remove the work this class does
 *
 * Uses a bowlingAPI score api from this library:
 * https://github.com/dgestep/bowling-score-keeper
 *
 * All code credit within the bowlingAPI package goes to him
 */
public class Lane extends Thread implements PinsetterObserver {
    private Party party; //the party that is using this lane
    private Pinsetter setter; // the setter object that is part of this lane
    private HashMap scores; //the scores of each player in this lane
    private Vector subscribers; //the GUI subscribers that this method updates

    private boolean gameIsHalted; //whether the game is halted or not

    private boolean partyAssigned; //whether the lane is occupied or not
    private boolean gameFinished; //whether the game is ended or not
    private Iterator bowlerIterator; //the iterator that moves through each bowler
    private int ball; //the ball that is being thrown down the lane
    private int bowlIndex;
    private int frameNumber; //the frame that the current game is in
    private boolean tenthFrameStrike; //whether the frame has been a strike or not

    private int[] curScores; //the current scores of each of the bowlers
    private int[][] cumulScores; //the cumulative scores of each of the bowlers,
                // first param is the bowler, second is the frame.
    private boolean canThrowAgain; //whether the current bowler can throw again.

    private int[][] finalScores; //the final scores of each bowler in the lane
    private int gameNumber; //the game number (number of games the lane has)

    private ScoringAPIController apiController;

    private Bowler currentThrower;          // = the thrower who just took a throw

    /** model.lane.Lane()
     *
     * Constructs a new lane and starts its thread
     *
     * @pre none
     * @post a new lane has been created and its thered is executing
     */
    public Lane(){
        setter = new Pinsetter();
        scores = new HashMap();
        subscribers = new Vector();

        gameIsHalted = false;
        partyAssigned = false;

        gameNumber = 0;

        setter.subscribe( this );

        this.start();
    }

    /** run()
     *
     * entry point for execution of this lane. Loops through for each play of the bowlers until the game is over
     * Needs to be compressed and have functionality pulled out.
     */
    public void run(){

        while (true) {
            if (partyAssigned && !gameFinished) {   // we have a party on this lane,
                // so next bower can take a throw

                while (gameIsHalted) { //while the game is halted, it sleeps this thread
                    try {
                        sleep(10);
                    } catch (Exception e) {}
                }


                if (bowlerIterator.hasNext()) { //iterates through each bowler for each round
                    currentThrower = (Bowler)bowlerIterator.next();

                    canThrowAgain = true; //resets for each bowler
                    tenthFrameStrike = false;
                    ball = 0;
                    while (canThrowAgain) {
                        setter.ballThrown();        // simulate the thrower's ball hiting
                        ball++;
                    }

                    if (frameNumber == 9) { //certain functionality if the frame is the second to last
                        finalScores[bowlIndex][gameNumber] = cumulScores[bowlIndex][9];
                        try{
                            Date date = new Date();
                            String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth() + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                            ScoreHistoryFile.addScore(currentThrower.getNick(), dateString, new Integer(cumulScores[bowlIndex][9]).toString());
                        } catch (Exception e) {System.err.println("Exception in addScore. "+ e );}
                    }


                    setter.reset();
                    bowlIndex++;

                } else { // if the bowler cannot throw anymore, resets all stats, resets bowler iterator
                    frameNumber++;
                    resetBowlerIterator();
                    bowlIndex = 0;
                    if (frameNumber > 9) {
                        gameFinished = true;
                        gameNumber++;
                    }
                }
            } else if (partyAssigned && gameFinished) { //if the game is over, creates an end game prompt
                //when end game prompt responds, it is destroyed
                EndGamePrompt egp = new EndGamePrompt( ((Bowler) party.getMembers().get(0)).getNickName() + "'s model.Party" );
                int result = egp.getResult();
                egp.distroy();
                egp = null;


                System.out.println("result was: " + result);

                // TODO: send record of scores to control desk
                if (result == 1) {                  // yes, want to play again, reset scores and iterator
                    resetScores();
                    resetBowlerIterator();

                } else if (result == 2) {// no, dont want to play another game, return back to desk
                    Vector printVector;
                    EndGameReport egr = new EndGameReport( ((Bowler)party.getMembers().get(0)).getNickName() + "'s model.Party", party);
                    printVector = egr.getResult();
                    partyAssigned = false;
                    Iterator scoreIt = party.getMembers().iterator();
                    party = null;
                    partyAssigned = false;

                    publish(lanePublish());

                    int myIndex = 0;
                    while (scoreIt.hasNext()) { //iterates through score and creates a score report for the whole game
                        Bowler thisBowler = (Bowler)scoreIt.next();
                        ScoreReport sr = new ScoreReport( thisBowler, finalScores[myIndex++], gameNumber );
                        sr.sendEmail(thisBowler.getEmail());
                        Iterator printIt = printVector.iterator();
                        while (printIt.hasNext()) {
                            if (thisBowler.getNick() == (String)printIt.next()) {
                                System.out.println("Printing " + thisBowler.getNick());
                                sr.sendPrintout();
                            }
                        }

                    }
                }
            }


            try {
                sleep(10);
            } catch (Exception e) {}
        }
    }

    /** recievePinsetterEvent()
     *
     * recieves the thrown event from the pinsetter
     *
     * @pre none
     * @post the event has been acted upon if desiered
     *
     * @param pe        The pinsetter event that has been received.
     */
    public void receivePinsetterEvent(PinsetterEvent pe){

        if (pe.pinsDownOnThisThrow() >=  0) {               // this is a real throw
            markScore(currentThrower, frameNumber + 1, pe.getThrowNumber(), pe.pinsDownOnThisThrow());

            // next logic handles the ?: what conditions dont allow them another throw?
            // handle the case of 10th frame first
            if (frameNumber == 9) {
                if (pe.totalPinsDown() == 10) {
                    setter.resetPins();
                    if(pe.getThrowNumber() == 1) {
                        tenthFrameStrike = true;
                    }
                }

                if ((pe.totalPinsDown() != 10) && (pe.getThrowNumber() == 2 && tenthFrameStrike == false)) {
                    canThrowAgain = false;
                    //publish( lanePublish() );
                }

                if (pe.getThrowNumber() == 3) { // if the throw number is over 2, cannot throw again
                    canThrowAgain = false;
                    //publish( lanePublish() );
                }
            } else {     // its not the 10th frame

                if (pe.pinsDownOnThisThrow() == 10) {           // threw a strike
                    canThrowAgain = false;
                    //publish( lanePublish() );
                } else if (pe.getThrowNumber() == 2) {
                    canThrowAgain = false;
                    //publish( lanePublish() );
                } else if (pe.getThrowNumber() == 3)
                    System.out.println("I'm here...");
            }
        } else {                                    //  this is not a real throw, probably a reset
        }
    }

    /** resetBowlerIterator()
     *
     * sets the current bower iterator back to the first bowler
     *
     * @pre the party as been assigned
     * @post the iterator points to the first bowler in the party
     */
    private void resetBowlerIterator(){
        bowlerIterator = (party.getMembers()).iterator();
    }

    /** resetScores()
     *
     * resets the scoring mechanism, must be called before scoring starts
     *
     * @pre the party has been assigned
     * @post scoring system is initialized
     */
    private void resetScores(){
        Iterator bowlIt = (party.getMembers()).iterator();

        while ( bowlIt.hasNext() ) {
            int[] toPut = new int[25];
            for ( int i = 0; i != 25; i++) {
                toPut[i] = -1;
            }
            scores.put( bowlIt.next(), toPut );
        }



        gameFinished = false;
        frameNumber = 0;
    }

    /** assignParty()
     *
     * assigns a party to this lane
     *
     * @pre none
     * @post the party has been assigned to the lane
     *
     * @param theParty		model.Party to be assigned
     */
    public void assignParty( Party theParty ){
        party = theParty;
        resetBowlerIterator();
        partyAssigned = true;

        apiController = new ScoringAPIController(theParty);

        curScores = new int[party.getMembers().size()];
        cumulScores = new int[party.getMembers().size()][10];
        finalScores = new int[party.getMembers().size()][128]; //Hardcoding a max of 128 games, bite me.
        gameNumber = 0;

        apiController.resetGames();
        resetScores();
    }

    /** markScore()
     *
     * Method that marks a bowlers score on the board.
     *
     * @param Cur		The current bowler
     * @param frame	The frame that bowler is on
     * @param ball		The ball the bowler is on
     * @param score	The bowler's score
     */
    private void markScore( Bowler Cur, int frame, int ball, int score ){
        int[] curScore;
        int index =  ( (frame - 1) * 2 + ball);

        curScore = (int[]) scores.get(Cur);

        curScore[ index - 1] = score;
        scores.put(Cur, curScore);
        getScore( Cur, frame );
        publish( lanePublish() );
    }

    /** lanePublish()
     *
     * Method that creates and returns a newly created laneEvent
     *
     * @return		The new lane event
     */
    private LaneEvent lanePublish(  ){
        LaneEvent laneEvent = new LaneEvent(party, bowlIndex, currentThrower, cumulScores, scores, frameNumber+1, curScores, ball, gameIsHalted);
        return laneEvent;
    }

    /** getScore()
     *
     * Method that calculates a bowlers score. Could switch over to state based calculation
     * Every time this method is called the new score is calculated. As of now it does not keep track
     * of previous calculated scores
     *
     * @param Cur		The bowler that is currently up
     * @param frame	    The frame the current bowler is on
     *
     * @return			The bowlers total score
     */
    private int sgetScore( Bowler Cur, int frame){
        int[] curScore; //current score is the array of each ball score thrown.
        int strikeballs = 0; //number of strike balls
        int totalScore = 0; //total score
        curScore = (int[]) scores.get(Cur);

        for (int i = 0; i != 10; i++) {
            cumulScores[bowlIndex][i] = 0; //cumulScores is the array list of scores for the bowler,
        }

        int current = 2*(frame - 1)+ball-1;

        //Iterate through each ball until the current one.
        for (int i = 0; i != current+2; i++) {
            //Spare:
            if( i%2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19) {
                //This ball was a the second of a spare.
                //Also, we're not on the current ball.
                //Add the next ball to the ith one in cumul.
                cumulScores[bowlIndex][(i/2)] += curScore[i+1] + curScore[i];
            } else if( i < current && i%2 == 0 && curScore[i] == 10  && i < 18) {
                strikeballs = 0;
                //This ball is the first ball, and was a strike.
                //If we can get 2 balls after it, good add them to cumul.
                if (curScore[i+2] != -1) {
                    strikeballs = 1;
                    if(curScore[i+3] != -1) {
                        //Still got em.
                        strikeballs = 2;
                    } else if(curScore[i+4] != -1) {
                        //Ok, got it.
                        strikeballs = 2;
                    }
                }
                if (strikeballs == 2) {
                    //Add up the strike.
                    //Add the next two balls to the current cumulscore.
                    cumulScores[bowlIndex][i/2] += 10;
                    if(curScore[i+1] != -1) {
                        cumulScores[bowlIndex][i/2] += curScore[i+1] + cumulScores[bowlIndex][(i/2)-1];
                        if (curScore[i+2] != -1) {
                            if( curScore[i+2] != -2) {
                                cumulScores[bowlIndex][(i/2)] += curScore[i+2];
                            }
                        } else {
                            if( curScore[i+3] != -2) {
                                cumulScores[bowlIndex][(i/2)] += curScore[i+3];
                            }
                        }
                    } else {
                        if ( i/2 > 0 ) {
                            cumulScores[bowlIndex][i/2] += curScore[i+2] + cumulScores[bowlIndex][(i/2)-1];
                        } else {
                            cumulScores[bowlIndex][i/2] += curScore[i+2];
                        }
                        if (curScore[i+3] != -1) {
                            if( curScore[i+3] != -2) {
                                cumulScores[bowlIndex][(i/2)] += curScore[i+3];
                            }
                        } else {
                            cumulScores[bowlIndex][(i/2)] += curScore[i+4];
                        }
                    }
                } else {
                    break;
                }
            }else {
                //We're dealing with a normal throw, add it and be on our way.
                if( i%2 == 0 && i < 18) {
                    if ( i/2 == 0 ) {
                        //First frame, first ball.  Set his cumul score to the first ball
                        if(curScore[i] != -2) {
                            cumulScores[bowlIndex][i/2] += curScore[i];
                        }
                    } else if (i/2 != 9) {
                        //add his last frame's cumul to this ball, make it this frame's cumul.
                        if(curScore[i] != -2) {
                            cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1] + curScore[i];
                        } else {
                            cumulScores[bowlIndex][i/2] += cumulScores[bowlIndex][i/2 - 1];
                        }
                    }
                } else if (i < 18) {
                    if(curScore[i] != -1 && i > 2) {
                        if(curScore[i] != -2) {
                            cumulScores[bowlIndex][i/2] += curScore[i];
                        }
                    }
                }
                if (i/2 == 9) {
                    if (i == 18) {
                        cumulScores[bowlIndex][9] += cumulScores[bowlIndex][8];
                    }
                    if(curScore[i] != -2) {
                        cumulScores[bowlIndex][9] += curScore[i];
                    }
                } else if (i/2 == 10) {
                    if(curScore[i] != -2) {
                        cumulScores[bowlIndex][9] += curScore[i];
                    }
                }
            }
        }
        return totalScore;
    }

    /**
     * New Implementation of getScore, refactored to use the API for bowler scoring. Less cyclomatic complexity, as there is no
     * for loop. Outsources scoring and score storing to api classes. Uses a controller.ScoringAPIController as a facade to talk to api.
     * This method only is used to calculate the score of the frame that we're on.
     *
     * Old method had to recalculate each new frame, because it reset it to 0, but
     * the api does that already. Old method also reupdated each score for each frame
     * however, the api we have adjusts frame scores when dealing with strikes/spares
     *
     * All we have to do is update the new array, and assign the calls for each
     * previous score to calls for the past frames.
     *
     * @param currentBowler the bowler currently throwing
     * @param frame the frame that is currently being bowled
     * @return returns the total score for the bowler
     */
    private int getScore(Bowler currentBowler, int frame){
        int[] currentScoreArray; //this is the array of each ball thrown for the current bowler
        int currentScoreIndex; //the index for the last last ball thrown
        int totalScore = 0;
        boolean incompleteFrame = false;

        frame -= 1;//zero-index frames

        //this represents each number of pins bowled for each frame.
        //0,1 is a frame, 2,3 is a frame, 4,5 is a frame, unless a strike was bowled,
        currentScoreArray = (int[]) scores.get(currentBowler);
        currentScoreIndex = 2*frame+ball;


        //System.out.println(currentScoreArray[((frame*2)-1)-1] + ", " + currentScoreArray[((frame*2)-1)]);

        //int current = 2*(frame -1)+ball-1; //the current ball we're bowlingAPI.
        //if the current ball is at an odd index, we just finished the frame,
        //if its not odd, but a 10, then we can just submit a strike frame and move on.
        //so we can do some logic to see if its a spare, strike, or whatever

        if(currentScoreArray[currentScoreIndex] == 10) {
            //((frame * 2) - 1) is the last ball thrown in a frame, could be -1 too.
            //((frame * 2) - 1) - 1) is the first ball thrown in a frame
            //if the bowl is a -1, its a strike and we just submit a strike
            //this is because a 10 is scored first ball, the second is -1.
            apiController.addStrike(currentBowler);

        }else if(ball == 1 && currentScoreArray[currentScoreIndex - 1] + currentScoreArray[currentScoreIndex] == 10){
            //this compares the first bowled frame score to this bowled frame score
            //if this is a spare, then we have to tell the api controller that
            //a spare was scored.

            apiController.addSpare(currentBowler,
                    currentScoreArray[currentScoreIndex - 1],
                    currentScoreArray[currentScoreIndex]);


        }else if(ball == 2){
            //if this is the last frame being bowled, then the ball can hit 2.
            //if this is the case, then we need to tell the api that
            //this is the last frame and that we wont need any more

            apiController.addFrame(currentBowler,
                    currentScoreArray[currentScoreIndex - 2],
                    currentScoreArray[currentScoreIndex - 1]);
            incompleteFrame = true;

            totalScore = cumulScores[bowlIndex][frame];


        }else{
            //if nothing else triggers, then this is a regular bowl.
            //this can do 2 things. if the ball is 0, then we submit
            //a frame where the score is 0-9 in the first bit, and 0 in the second.
            //if the ball is 1, then we submit a frame with (current-1, current)

            if(ball == 0){
                //this means the ball is the first one of the frame. Simply add this frame using a single bowl.
//                apiController.addFrame(currentBowler, currentScoreArray[currentScoreIndex], 0);
                if(frame != 10) {
                    incompleteFrame = true;
                }

                //System.out.println(currentScoreArray[((frame*2)-1)]);

            }

            if(ball == 1){
                //this means the ball is the last one of the frame, unless the if(ball == 2) was not triggered.
                //Submit the frame to be completed and added to the game.
                apiController.addFrame(currentBowler,
                        currentScoreArray[currentScoreIndex - 1],
                        currentScoreArray[currentScoreIndex]);

                //System.out.println(currentScoreArray[((frame*2)-1)]);
            }
        }

        for (int i = 0; i <= frame; i++) {
            if(incompleteFrame && i == frame) {
                cumulScores[bowlIndex][i] = currentScoreArray[currentScoreIndex];
                if(i > 0) {
                    cumulScores[bowlIndex][i] += apiController.getFrameScore(currentBowler, i - 1);
                }
            } if(i == frame) {
                cumulScores[bowlIndex][i] = 0;
            } else {
                cumulScores[bowlIndex][i] = apiController.getFrameScore(currentBowler, i);
            }
        }

        //System.out.println(cumulScores[bowlIndex][frame]);

        //System.out.println(Arrays.toString(currentScoreArray));
        //System.out.println(currentBowler.getNick() + " " + apiController.getScore(currentBowler));
        //System.out.println(current);
        //System.out.println(frame);

        return totalScore;

    }

    /** isPartyAssigned()
     *
     * checks if a party is assigned to this lane
     *
     * @return true if party assigned, false otherwise
     */
    public boolean isPartyAssigned(){
        return partyAssigned;
        //System.out.println(Arrays.toString(currentScoreArray));
        //System.out.println(Arrays.toString(currentScoreArray));
    }

    /** isGameFinished
     *
     * @return true if the game is done, false otherwise
     */
    public boolean isGameFinished(){
        return gameFinished;
    }

    /** subscribe
     *
     * Method that will add a subscriber
     *
     * @param adding	Observer that is to be added
     */

    public void subscribe( LaneObserver adding ){
        subscribers.add( adding );
    }

    /** unsubscribe
     *
     * Method that unsubscribes an observer from this object
     *
     * @param removing	The observer to be removed
     */

    public void unsubscribe( LaneObserver removing ){
        subscribers.remove( removing );
    }

    /** publish
     *
     * Method that publishes an event to subscribers
     *
     * @param event	Event that is to be published
     */

    public void publish( LaneEvent event ){
        if( subscribers.size() > 0 ) {
            Iterator eventIterator = subscribers.iterator();

            while ( eventIterator.hasNext() ) {
                ( (LaneObserver) eventIterator.next()).receiveLaneEvent( event );
            }
        }
    }

    /**
     * Accessor to get this model.lane.Lane's pinsetter
     *
     * @return		A reference to this lane's pinsetter
     */

    public Pinsetter getPinsetter(){
        return setter;
    }

    /**
     * Pause the execution of this game
     */
    public void pauseGame(){
        gameIsHalted = true;
        publish(lanePublish());
    }

    /**
     * Resume the execution of this game
     */
    public void unPauseGame(){
        gameIsHalted = false;
        publish(lanePublish());
    }

}
