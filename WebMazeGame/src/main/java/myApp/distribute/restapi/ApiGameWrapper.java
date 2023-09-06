package myApp.distribute.restapi;

import myApp.model.MazeGame;

/**
 * Wrapper class that send the information about the maze game to
 * the web client, such as if the game is won, the ID number, and
 * information relating to cheese.
 * Contains only a static factory method for creation.
 */
public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    public static ApiGameWrapper makeGameWrapper(MazeGame game, int gameID, boolean hasWon, boolean hasLost) {
        ApiGameWrapper gameWrapper = new ApiGameWrapper();

        gameWrapper.gameNumber = gameID;

        gameWrapper.isGameWon = hasWon;
        gameWrapper.isGameLost = hasLost;

        gameWrapper.numCheeseFound = game.getNumberCheeseCollected();
        gameWrapper.numCheeseGoal = game.getNumberCheeseToCollect();

        return gameWrapper;
    }
}
