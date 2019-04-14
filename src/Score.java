/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

public class Score {

    private String nick;
    private String date;
    private String score;

    /**
     * Constructor for the score. All the data is added in when this score object is created
     * @param nick name of the player this score belongs to
     * @param date date of the score
     * @param score score to be stored
     */
    public Score( String nick, String date, String score ){
        this.nick=nick;
        this.date=date;
        this.score=score;
    }

    /**
     * Getter for the nickname
     * @return nickname
     */
    public String getNickName(){
        return nick;
    }

    /**
     * Getter for the date
     * @return date
     */
    public String getDate(){
        return date;
    }

    /**
     * Getter for the score
     * @return score
     */
    public String getScore(){
        return score;
    }

    /**
     * Returns a string representation of this object
     * @return string of this object
     */
    public String toString(){
        return nick + "\t" + date + "\t" + score;
    }

}
