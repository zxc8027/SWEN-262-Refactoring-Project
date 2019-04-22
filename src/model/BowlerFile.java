package model; /**
 * Class for interfacing with model.Bowler database.
 */
import model.Bowler;

import java.util.*;
import java.io.*;

public class BowlerFile {

    /** The location of the bowelr database */
    private static String BOWLER_DAT = "BOWLERS.DAT";

    /**
     * Retrieves bowler information from the database and returns a model.Bowler objects with populated fields.
     *
     * @param nickName	the nickName of the bowler to retrieve.
     *
     * @return a model.Bowler object.
     */
    public static Bowler getBowlerInfo(String nickName) throws IOException,FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(BOWLER_DAT));
        String data;

        // Read the data.
        while ((data = in.readLine()) != null) {
            // File format is nick\tfname\te-mail
            String[] bowler = data.split("\t");
            if (nickName.equals(bowler[0])) {
                System.out.println(
                    "Nick: "
                    + bowler[0]
                    + " Full: "
                    + bowler[1]
                    + " email: "
                    + bowler[2]);
                return (new Bowler(bowler[0], bowler[1], bowler[2]));
            }
        }
        System.out.println("Nick not found...");
        return null;
    }

    /**
     * Stores a model.Bowler in the database.
     *
     * @param nickName	the NickName of the model.Bowler.
     * @param fullName	the FullName of the model.Bowler.
     * @param email	the E-mail Address of the model.Bowler.
     *
     */
    public static void putBowlerInfo(String nickName,String fullName,String email) throws IOException,FileNotFoundException {
        String data = nickName + "\t" + fullName + "\t" + email + "\n";

        RandomAccessFile out = new RandomAccessFile(BOWLER_DAT, "rw");
        out.skipBytes((int) out.length());
        out.writeBytes(data);
        out.close();
    }

    /**
     * Retrieves a list of nicknames in the bowler database.
     *
     * @return a Vector of Strings.
     *
     */
    public static Vector getBowlers() throws IOException,FileNotFoundException {
        // Create the base vector.
        Vector allBowlers = new Vector();

        // REad the bowlers.
        BufferedReader in = new BufferedReader(new FileReader(BOWLER_DAT));
        String data;
        while ((data = in.readLine()) != null) {
            String[] bowler = data.split("\t");
            allBowlers.add(bowler[0]);
        }

        // Return the bowlers.
        return allBowlers;
    }

}