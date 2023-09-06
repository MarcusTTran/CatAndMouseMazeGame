# Cat and Mouse Maze Web Video Game <br>
<b>using Spring Boot API</b>

<i>Traverse through a randomly generated maze one move at a time until you've collected all the cheese. Watch out for cats though, they'll eat you!</i>

## Gameplay

- The randomly generated maze spawns you (the mouse) in the top left corner of the screen and three mice in the other corners.
- 1 piece of cheese is spawned at a time in a random location. Once caught by the mouse, it will add to the player's cheese counter and respawn elsewhere.
- The player can move with the arrow keys (↑, →, ↓, ←), but every space they move, the cats also move one space randomly.
- If the player and cats collide, the player will lose!
- If the player gets a total of 5 cheeses, they will win and can play again on a new map.
- Walls are all hidden at the start, but slowly revealed as the mouse moves throughout the maze.

<b>Some hidden secrets: <b/> <br>
- There is a button that can allow the player to see where all the walls.
- A different button lets the player win at 1 cheese collected.

## Technical Details
To play the game, open up your prefered browser then go to this URL: <br>
<u>http://localhost:8080/</u>




