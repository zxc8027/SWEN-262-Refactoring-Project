/**
 *  Class that holds all bowler info.
 */
public class Bowler {
    private String fullName;
    private String nickName;
    private String email;

    /**
     * Creates a bowler.
     *
     * @param nick the nickname of the bowler.
     * @param full the full name of the bowler.
     * @param mail the email address of the bowler.
     */
    public Bowler( String nick,String full,String mail){
        this.nickName = nick;
        this.fullName = full;
        this.email = mail;
    }

    /**
     * Returns the nickname of the bowler.
     *
     * @return the nickname of the bowler.
     */
    public String getNickName(){
        return this.nickName;
    }

    /**
     * Returns the nickname of the bowler.
     *
     * @return the nickname of the bowler.
     */
    public String getNick(){
        return this.nickName;
    }

    /**
     * Returns the full name of the bowler.
     *
     * @return the full name of the bowler.
     */
    public String getFullName(){
        return this.fullName;
    }

    /**
     * Returns the email of the bowler.
     *
     * @return the email of the bowler.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Determines if two bowlers are equal.
     *
     * @param b the bowl to compare.
     * @return if the bowlers are equal.
     */
    public boolean equals(Bowler b){
        boolean retval = true;
        if (!(nickName.equals(b.getNickName()))) {
            retval = false;
        }
        if (!(fullName.equals(b.getFullName()))) {
            retval = false;
        }
        if (!(email.equals(b.getEmail()))) {
            retval = false;
        }
        return retval;
    }
}