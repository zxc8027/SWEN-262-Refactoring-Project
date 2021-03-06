package bowlingAPI.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Represents a bowlingAPI game for a particular bowler.
 *
 * @author dougestep
 */


public class Game implements Serializable {
    private static final long serialVersionUID = 751620950527583199L;
    private UUID uid;
    private List<BowlingFrame> frames;
    private int score;
    private boolean complete;
    private Bowler bowler;

    /**
     * Creates an instance of this class.
     */
    public Game() {
    }

    /**
     * Returns a {@link UUID} that uniquely identifying this game.
     *
     * @return the UUID.
     */
    public UUID getUid() {
        return uid;
    }

    /**
     * Sets the {@link UUID} that uniquely identifying this game.
     *
     * @param uid the UUID.
     * @return returns this instance.
     */
    public Game setUid(final UUID uid) {
        this.uid = uid;
        return this;
    }

    /**
     * Returns a list of bowlingAPI frames recorded for this game.
     *
     * @return the bowlingAPI frames.
     */
    public List<BowlingFrame> getFrames() {
        return frames;
    }

    /**
     * Sets the list of bowlingAPI frames recorded for this game.
     *
     * @param frames the bowlingAPI frames.
     * @return returns this instance.
     */
    public Game setFrames(final List<BowlingFrame> frames) {
        this.frames = frames;
        return this;
    }

    /**
     * Adds the supplied bowlingAPI frame to the list of bowlingAPI frames recorded for this game.
     *
     * @param frame the bowlingAPI frame.
     * @return returns this instance.
     */
    public Game addFrame(final BowlingFrame frame) {
        if (frames == null) {
            frames = new ArrayList<>();
        }
        frames.add(frame);
        return this;
    }

    /**
     * Removes all the bowlingAPI frames recorded for this game.
     */
    public void clearFrames() {
        if (frames == null) {
            frames = new ArrayList<>();
        } else {
            frames.clear();
        }
    }

    /**
     * Returns the number of bowlingAPI frames recorded for this game.
     *
     * @return the number of bowlingAPI frames.
     */
    public int getNumberOfFrames() {
        return frames == null ? 0 : frames.size();
    }

    /**
     * Returns the score for this game.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score for this game.
     *
     * @param score the score.
     * @return returns this instance.
     */
    public Game setScore(final int score) {
        this.score = score;
        return this;
    }

    /**
     * Returns true if this game has completed.
     *
     * @return true if completed.
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Set to true to indicate that this game has completed.
     *
     * @param complete true if completed.
     * @return returns this instance.
     */
    public Game setComplete(final boolean complete) {
        this.complete = complete;
        return this;
    }

    /**
     * Returns the bowler associated with this game.
     *
     * @return the bowler.
     */
    public Bowler getBowler() {
        return bowler;
    }

    /**
     * Sets the bowler that is bowlingAPI in this game.
     *
     * @param bowler the bowler.
     * @return returns this instance.
     */
    public Game setBowler(final Bowler bowler) {
        this.bowler = bowler;
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Game other = (Game) obj;
        return this.uid == other.uid;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public String toString() {
        String str = "";
        str += "player " + bowler;
        str += "score " + score;
        str += "complete " + complete;
        return str;
    }
}
