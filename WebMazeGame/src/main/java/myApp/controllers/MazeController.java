package myApp.controllers;


import myApp.distribute.restapi.ApiBoardWrapper;
import myApp.distribute.restapi.ApiGameWrapper;
import myApp.model.MazeGame;
import myApp.model.MazeGameManager;
import myApp.model.MoveDirection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Controller class that interacts between the web client
 * and model logic classes of the maze game. Holds a collection
 * of games and uses a MazeGameManager
 * to handle changing them.
 */
@RestController
 @RequestMapping(path = "/api")
public class MazeController {
    final static String moveUp = "MOVE_UP";
    final static String moveDown = "MOVE_DOWN";
    final static String moveLeft = "MOVE_LEFT";
    final static String moveRight = "MOVE_RIGHT";
    final static String moveCats = "MOVE_CATS";
    final static String oneCheese = "1_CHEESE";
    final static String showAll = "SHOW_ALL";
    private static final int CHEAT_LESS_CHEESE = 1;
    List<String> movesList = new ArrayList<>();
    MazeGameManager gameManager = new MazeGameManager();

    @GetMapping("/about")
    public String getAboutMessage() {
        String creatorName = "Marcus Tran";
        return creatorName;
    }

    @GetMapping("/games")
    public List<ApiGameWrapper> getListOfGames() {

        return gameManager.getAllApiGames();
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        // Make new game in manager
        gameManager.add();
        // Access and return it
        ApiGameWrapper newGame = gameManager.getAllApiGames().get(gameManager.getAllApiGames().size() - 1);
        return newGame;
    }

    @GetMapping("/games/{id}")
    public ApiGameWrapper getGame(@PathVariable("id") int id) {
        if (id >= gameManager.getAllApiGames().size()) {
            throw new FileNotFound("Game with ID:<1> does not exist!");
        }
        return gameManager.getAllApiGames().get(id);
    }

    @GetMapping("games/{id}/board")
    public ApiBoardWrapper getBoardForGame(@PathVariable("id") int id) {
        MazeGame game = getGameFromManager(id);

        ApiBoardWrapper boardWrapper = ApiBoardWrapper.makeFromGame(game);
        return boardWrapper;
    }


    @PostMapping("games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void move(@RequestBody String nextMove, @PathVariable("id") int id) {
        MazeGame game = getGameFromManager(id);
        MoveDirection direction = null;

        switch (nextMove) {
            case moveUp -> direction = MoveDirection.MOVE_UP;
            case moveDown -> direction = MoveDirection.MOVE_DOWN;
            case moveLeft -> direction = MoveDirection.MOVE_LEFT;
            case moveRight -> direction = MoveDirection.MOVE_RIGHT;
            case moveCats -> game.doCatMoves();
            default -> throw new BadRequest("Invalid move entered.");
        }

        if (direction != null) {
            if (game.isValidPlayerMove(direction)) {
                game.recordPlayerMove(direction);
            } else {
                throw new BadRequest("Cannot move into walls.");
            }
        }

    }

    @PostMapping("games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void applyCheat(@RequestBody String cheat, @PathVariable("id") int id) {
        MazeGame game = getGameFromManager(id);
        if (cheat.equals(oneCheese)) {
            game.setNumberCheeseToCollect(CHEAT_LESS_CHEESE);
        } else if (cheat.equals(showAll)) {
            game.revealAllCells();
        } else {
            throw new BadRequest("Invalid cheat entered.");
        }
    }

    private MazeGame getGameFromManager(int id) {
        MazeGame game;
        try {
            game = gameManager.getGame(id);
        } catch (RuntimeException e) {
            throw new FileNotFound("Could not find game with id = " + id);
        }
        return game;
    }

    // For errors!
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class BadRequest extends RuntimeException {
        public BadRequest() {}
        public BadRequest(String str) {
            super(str);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class FileNotFound extends RuntimeException {
        public FileNotFound() {}
        public FileNotFound(String str) {
            super(str);
        }
    }
}

//