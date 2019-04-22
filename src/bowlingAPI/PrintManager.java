package bowlingAPI;

import java.io.PrintStream;

import bowlingAPI.data.Game;

/**
 * Defines a class which manages printing a bowlingAPI game.
 *
 * @author dougestep
 */
public interface PrintManager {

    /**
     * Prints all supplied games to the supplied output stream.
     * @param games the games.
     * @param out the output stream to print the games to.
     */
    void printGames(Game[] games, PrintStream out);

    /**
     * Prints the supplied game to the supplied output stream.
     * @param game the game.
     * @param out the output stream to print the game to.
     */
    void printGame(Game game, PrintStream out);
}
