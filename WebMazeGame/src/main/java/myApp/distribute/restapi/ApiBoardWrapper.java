package myApp.distribute.restapi;

import myApp.model.Cat;
import myApp.model.CellLocation;
import myApp.model.MazeGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that send the information about the maze board to
 * the web client. Sends information about the characters in
 * the maze and the board.
 * Contains only a static factory method for creation.
 */
public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // Accept whatever object(s) you need to populate this object.
    public static ApiBoardWrapper makeFromGame(MazeGame game) {
        ApiBoardWrapper boardWrapper = new ApiBoardWrapper();
        boardWrapper.boardWidth = MazeGame.getMazeWidth();
        boardWrapper.boardHeight = MazeGame.getMazeHeight();

        boardWrapper.mouseLocation = ApiLocationWrapper.makeFromLocation(game.getPlayerLocation().getX(), game.getPlayerLocation().getY());
        boardWrapper.cheeseLocation = ApiLocationWrapper.makeFromLocation(game.getCheeseLocation().getX(), game.getCheeseLocation().getY());

        boardWrapper.catLocations = new ArrayList<>();
        List<Cat> cats = game.getCats();
        for (Cat cat : cats) {
            CellLocation catLocation = cat.getLocation();
            boardWrapper.catLocations.add( ApiLocationWrapper.makeFromLocation(catLocation.getX(), catLocation.getY()) );
        }

        getWallsAndVisibility(game, boardWrapper);

        return boardWrapper;
    }

    private static void getWallsAndVisibility(MazeGame game, ApiBoardWrapper boardWrapper) {
        boardWrapper.hasWalls = new boolean[MazeGame.getMazeHeight()][MazeGame.getMazeWidth()];
        for (int y = 0; y < MazeGame.getMazeHeight(); y++) {
            for (int x = 0; x < MazeGame.getMazeWidth(); x++) {
                boardWrapper.hasWalls[y][x] = game.getCellState(new CellLocation(x, y)).isWall();
            }
        }

        boardWrapper.isVisible = new boolean[MazeGame.getMazeHeight()][MazeGame.getMazeWidth()];
        for (int y = 0; y < MazeGame.getMazeHeight(); y++) {
            for (int x = 0; x < MazeGame.getMazeWidth(); x++) {
                boardWrapper.isVisible[y][x] = game.getCellState(new CellLocation(x, y)).isVisible();
            }
        }
    }
}
