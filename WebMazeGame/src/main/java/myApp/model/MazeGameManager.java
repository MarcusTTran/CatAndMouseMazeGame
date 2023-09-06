package myApp.model;

import myApp.distribute.restapi.ApiGameWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager class holds a collection of MazeGames and manages them
 * for the controller based on user commands. Instantiated by
 * the controller class.
 */
public class MazeGameManager {
    private List<MazeGame> mazeGames;

    public MazeGameManager() {
        this.mazeGames = new ArrayList<>();
    }

    public void add() {
        MazeGame game = new MazeGame();
        mazeGames.add(game);
    }
    public MazeGame getGame(int id) {
        if (id >= mazeGames.size() || mazeGames.size() == 0) {
            throw new RuntimeException();
        }
        return mazeGames.get(id);
    }
    public List<ApiGameWrapper> getAllApiGames() {
        List<ApiGameWrapper> ApiMazeGameList = new ArrayList<>();

        for (int i = 0; i < mazeGames.size(); i++) {
            int newGameID = i;
            boolean userWon = mazeGames.get(i).hasUserWon();
            boolean userLost = mazeGames.get(i).hasUserLost();
            ApiGameWrapper newGame = ApiGameWrapper.makeGameWrapper(mazeGames.get(i), newGameID, userWon, userLost);
            ApiMazeGameList.add(newGame);
        }

        return ApiMazeGameList;
    }


}
