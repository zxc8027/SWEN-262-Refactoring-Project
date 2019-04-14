/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.util.*;
import java.io.*;

/**
 * This class acts as a way to store scores for a person in a file to be accesssed at a later date.
 * Uses
 */
public class ScoreHistoryFile {

    private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

    /**
     * This method is a way to add a nicknames score to the score file. Uses the name and date to see
     * if an existing name exists, checks for IO exceptions and if all passes, it writes the save information
     * @param nick nickname of the player to keep track of score
     * @param date date of score
     * @param score score number
     * @throws IOException if there is an exception thrown when trying to write the bytes
     * @throws FileNotFoundException if the file to be accessed does not exist
     */
    public static void addScore(String nick, String date, String score)
    throws IOException, FileNotFoundException {

        String data = nick + "\t" + date + "\t" + score + "\n";

        RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
        out.skipBytes((int) out.length());
        out.writeBytes(data);
        out.close();
    }

    /**
     * This method acts as a way to retrieve scores from the scoresheet. Uses IO streams to read.
     * @param nick the nickname for the score to be accessed
     * @return return the score of the given nickname
     * @throws IOException if there is an error reading data
     * @throws FileNotFoundException if the file given does not exist
     */
    public static Vector getScores(String nick)
    throws IOException, FileNotFoundException {
        Vector scores = new Vector();

        BufferedReader in =
            new BufferedReader(new FileReader(SCOREHISTORY_DAT));
        String data;
        while ((data = in.readLine()) != null) {
            // File format is nick\tfname\te-mail
            String[] scoredata = data.split("\t");
            //"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
            if (nick.equals(scoredata[0])) {
                scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
            }
        }
        return scores;
    }

}
