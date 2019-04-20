import bowling.data.BowlingFrame;

import java.util.*;

public class ScoringAPIController {

    private ArrayList<bowling.GameManager> games = new ArrayList<>(); //the list of games for each bowler for the scoring api
    private ArrayList<bowling.data.Bowler> bowlers = new ArrayList<>(); //the bowlers for the scoring api

    /**
     * The constructor adds bowlers from the party to each ones individual game.
     * @param bowlingParty the party that will be bowling this game
     */
    public ScoringAPIController(Party bowlingParty){
        for(int i = 0; bowlingParty.getMembers().size() > 0; i++){
            Bowler newBowler = (Bowler)bowlingParty.getMembers().get(i);
            bowling.data.Bowler apiBowler = new bowling.data.Bowler().setFirstName(newBowler.getNick());
            apiBowler.setLastName("");

            bowling.GameManager apiGame = bowling.impl.GameManagerImpl.newGame(apiBowler);
            bowlers.add(apiBowler);
            games.add(apiGame);
        }
    }


    /**
     * This method is used to get the game for the bowler that we are trying to change the score for.
     * @param bowler bowler that is bowling currently
     * @return returns the gamemanager for the api game that this bowler is linked to
     */
    private bowling.GameManager getBowlersGame(Bowler bowler){

        //create a new temp api bowler to check the list for.
        bowling.data.Bowler tempBowler = new bowling.data.Bowler().setFirstName(bowler.getNick());

        if(bowlers.contains(tempBowler)){ //if the bowler is within the list, there will be a game for that bowler

            for(bowling.GameManager apiGame : games) { //for each game, if the first name of the bowler = the nickname of
                //parameter bowler, then this game matches, so return this game manager for score addition
                if (apiGame.getGame().getBowler().getFirstName().equals(bowler.getNick())) {
                    return apiGame;
                }
            }
            return null;

        }else{
            System.out.println("Bowler not in the list!");
            return null;
        }
    }

    /**
     * This is called when the bowler scores a spare for a total frame.
     * @param bowler bowler that is bowling
     */
    public void addSpare(Bowler bowler, int frame){
        bowling.GameManager game = getBowlersGame(bowler);

        game.addFrame(new BowlingFrame().setSplit(true));
    }

    /**
     * This is called when the bowler scores a strike in the frame.
     * @param bowler bowler that is bowling
     */
    public void addStrike(Bowler bowler){
        bowling.GameManager game = getBowlersGame(bowler);

        game.addFrame(BowlingFrame.strike());
    }

    /**
     * This method is used to add a frame where neither a strike nor a spare was scored.
     * @param bowler the bowler who is throwing
     * @param score1 the score of the first ball
     * @param score2 the score of the second ball
     */
    public void addFrame(Bowler bowler, int score1, int score2){
        bowling.GameManager game = getBowlersGame(bowler);

        game.addFrame(new BowlingFrame(score1, score2));
    }

    /**
     * This method gets the score for the bowler so far in the game
     * @param bowler bowler that is currently throwing
     */
    public int getScore(Bowler bowler){
        bowling.GameManager game = getBowlersGame(bowler);

        game.calculateScore();

        return game.getGame().getScore();
    }

    /**
     * This method gets the score for the bowler for a specific frame.
     * @param bowler bowler that is throwing
     * @param frame frame to get score for
     */
    public int getFrameScore(Bowler bowler, int frame){
        bowling.GameManager game = getBowlersGame(bowler);

        bowling.data.BowlingFrame bowlerFrame = game.retrieveFrame(frame);

        return bowlerFrame.getScore();
    }



}
