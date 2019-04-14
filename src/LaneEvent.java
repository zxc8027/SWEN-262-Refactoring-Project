/*  $Id$
 *
 *  Revisions:
 *    $Log: LaneEvent.java,v $
 *    Revision 1.6  2003/02/16 22:59:34  ???
 *    added mechnanical problem flag
 *
 *    Revision 1.5  2003/02/02 23:55:31  ???
 *    Many many changes.
 *
 *    Revision 1.4  2003/02/02 22:44:26  ???
 *    More data.
 *
 *    Revision 1.3  2003/02/02 17:49:31  ???
 *    Modified.
 *
 *    Revision 1.2  2003/01/30 21:21:07  ???
 *    *** empty log message ***
 *
 *    Revision 1.1  2003/01/19 22:12:40  ???
 *    created laneevent and laneobserver
 *
 *
 */

import java.util.HashMap;

/**
 * Stores data for a new lane event for a certain lane. Is created by the lane and given to the subscribed laneview
 * Contains newly updated data for that lane
 */
public class LaneEvent {

    private Party p;
    int frame;
    int ball;
    Bowler bowler;
    int[][] cumulScore;
    HashMap score;
    int index;
    int frameNum;
    int[] curScores;
    boolean mechProb;

    /**
     * Constructor for the new lane event object
     * @param pty the party who occupies this lane
     * @param theIndex index of the lane
     * @param theBowler bowler this event is made for
     * @param theCumulScore cumulative score of this bowler
     * @param theScore current frame score of this bowler
     * @param theFrameNum frame number of this bowler
     * @param theCurScores current scores of all bowlers
     * @param theBall the ball of the bowler
     * @param mechProblem the problem (if it exists)
     */
    public LaneEvent( Party pty, int theIndex, Bowler theBowler, int[][] theCumulScore, HashMap theScore, int theFrameNum, int[] theCurScores, int theBall, boolean mechProblem){
        p = pty;
        index = theIndex;
        bowler = theBowler;
        cumulScore = theCumulScore;
        score = theScore;
        curScores = theCurScores;
        frameNum = theFrameNum;
        ball = theBall;
        mechProb = mechProblem;
    }

    /**
     * If this event is a mechanical problem
     * @return if the event is a mechanical problem
     */
    public boolean isMechanicalProblem(){
        return mechProb;
    }

    /**
     * Getter for frame number
     * @return frame number
     */
    public int getFrameNum(){
        return frameNum;
    }

    /**
     * Getter for score
     * @return score
     */
    public HashMap getScore( ){
        return score;
    }

    /**
     * Getter for current scores
     * @return current scores
     */
    public int[] getCurScores(){
        return curScores;
    }

    /**
     * getter for the index
     * @return index
     */
    public int getIndex(){
        return index;
    }

    /**
     * Getter for the frame
     * @return frame
     */
    public int getFrame( ){
        return frame;
    }

    /**
     * Getter for the ball
     * @return ball
     */
    public int getBall( ){
        return ball;
    }

    /**
     * Getter for the cumulative score
     * @return cumulative score
     */
    public int[][] getCumulScore(){
        return cumulScore;
    }

    /**
     * Getter for the party
     * @return party
     */
    public Party getParty(){
        return p;
    }

    /**
     * Getter for the bowler
     * @return bowler
     */
    public Bowler getBowler(){
        return bowler;
    }

};

