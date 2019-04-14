/**
 *
 * SMTP implementation based on code by Real Gagnon mailto:real@rgagnon.com
 *
 */


import java.io.*;
import java.util.Vector;
import java.util.Iterator;
import java.net.*;
import java.awt.*;
import java.awt.print.*;

/**
 * This class creates a report of all the scores for a given bowler
 */
public class ScoreReport {

    private String content;

    /**
     * This method constructs a single string carrying all information for a single bowler
     * @param bowler bowler the report is about
     * @param scores scores of the bowler
     * @param games all games the bowler has been in
     */
    public ScoreReport( Bowler bowler, int[] scores, int games ){
        String nick = bowler.getNick();
        String full = bowler.getFullName();
        Vector v = null;
        try{
            v = ScoreHistoryFile.getScores(nick);
        } catch (Exception e) {System.err.println("Error: " + e);}

        Iterator scoreIt = v.iterator();

        content = "";
        content += "--Lucky Strike Bowling Alley Score Report--\n";
        content += "\n";
        content += "Report for " + full + ", aka \"" + nick + "\":\n";
        content += "\n";
        content += "Final scores for this session: ";
        content += scores[0];
        for (int i = 1; i < games; i++) {
            content += ", " + scores[i];
        }
        content += ".\n";
        content += "\n";
        content += "\n";
        content += "Previous scores by date: \n";
        while (scoreIt.hasNext()) {
            Score score = (Score) scoreIt.next();
            content += "  " + score.getDate() + " - " +  score.getScore();
            content += "\n";
        }
        content += "\n\n";
        content += "Thank you for your continuing patronage.";

    }

    /**
     * This method sends an email containing the data for this report to a given recipient
     * @param recipient email of recipient for report
     */
    public void sendEmail(String recipient){
        try {
            Socket s = new Socket("bnb4505.rit.edu", 25);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(s.getInputStream(), "8859_1"));
            BufferedWriter out =
                new BufferedWriter(
                    new OutputStreamWriter(s.getOutputStream(), "8859_1"));

            String boundary = "DataSeparatorString";

            // here you are supposed to send your username
            sendln(in, out, "HELO world");
            sendln(in, out, "MAIL FROM: <abc1234@rit.edu>");
            sendln(in, out, "RCPT TO: <" + recipient + ">");
            sendln(in, out, "DATA");
            sendln(out, "Subject: Bowling Score Report ");
            sendln(out, "From: <Lucky Strikes Bowling Club>");

            sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
            sendln(out, content + "\n\n");
            sendln(out, "\r\n");

            sendln(in, out, ".");
            sendln(in, out, "QUIT");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is how this program can send a printout of the score report to the printer.
     * Dont know how this is actually done, as there is no specified printer.
     */
    public void sendPrintout(){
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintableText printobj = new PrintableText(content);

        job.setPrintable(printobj);

        if (job.printDialog()) {
            try {
                job.print();
            } catch (PrinterException e) {
                System.out.println(e);
            }
        }

    }

    /**
     * This method sends the report through a buffered reader out to a string output
     * @param in the in buffered reader
     * @param out buffered writer out
     * @param s string to be changed
     */
    public void sendln(BufferedReader in, BufferedWriter out, String s){
        try {
            out.write(s + "\r\n");
            out.flush();
            // System.out.println(s);
            s = in.readLine();
            // System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes a string to a certain buffered writer
     * @param out buffered writer out
     * @param s string to be written
     */
    public void sendln(BufferedWriter out, String s){
        try {
            out.write(s + "\r\n");
            out.flush();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
