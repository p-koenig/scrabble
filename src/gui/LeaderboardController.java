package gui;

import java.util.Collections;
import java.util.List;
import game.GameState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import mechanic.Player;
import mechanic.PlayerData;

/**
 * This class initializes all leaderboard related labels.
 * 
 * @author nilbecke
 *
 */

public class LeaderboardController implements EventHandler<ActionEvent> {

  private GameState gs;
  private List<PlayerData> players;

  @FXML
  private Label leaderboard;
  @FXML
  private Label first;
  @FXML
  private Label second;
  @FXML
  private Label third;
  @FXML
  private Label fourth;
  @FXML
  private Label time;
  @FXML
  private Label turn;
  @FXML
  private Label word;

  @FXML
  public void initialize() {
    // this.gs = LeaderboardScreen.getGameState(); Hier DummyValues zum testen

    this.gs = new GameState(new Player("test1").getPlayerInfo(), null);
    this.gs.joinGame(new Player("test2").getPlayerInfo());
    this.gs.joinGame(new Player("test3").getPlayerInfo());
    this.gs.joinGame(new Player("test4").getPlayerInfo());



    this.gs.initializeScoresWithZero(this.gs.getAllPlayers());

    this.gs.addScore("test1", 15);
    this.gs.addScore("test2", 435);
    this.gs.addScore("test3", 34);
    this.gs.addScore("test4", 4);

    Font f = Font.loadFont(getClass().getResourceAsStream("Scrabble.ttf"), 40);
    leaderboard.setFont(f);
    this.players = this.gs.getAllPlayers();
    determineWinner();
    Label[] standings = {first, second, third, fourth};
    for (int i = 0; i < standings.length; i++) {
      // Enter player nicknames
      if (players.get(i) != null) {
        String ru = "Runner Up:           ";
        if (i != 3) {
          standings[i].setText(players.get(i).getNickname() + " "
              + gs.getScore(players.get(i).getNickname()) + " pt");
        } else {
          standings[i].setText(ru + players.get(i).getNickname() + " "
              + gs.getScore(players.get(i).getNickname()) + " pt");
        }
      } else {
        if (i == 3) {
          standings[i].setOpacity(0);
        }
        standings[i].setDisable(true);
      }

    }
    updateStatistics();
  }

  @Override
  public void handle(ActionEvent event) {
    // Only "leave game" button.
    System.exit(0);
  }

  /**
   * Determines the winner based on the score archieved. Players will be sorted by descending order
   * into the "players" List.
   */
  public void determineWinner() {
    boolean swap = true;
    while (swap) {
      swap = false;
      for (int i = 0; i < players.size() - 1; i++) {
        if (gs.getScore(players.get(i).getNickname()) < gs
            .getScore(players.get(i + 1).getNickname())) {
          Collections.swap(this.players, i, i + 1);
          swap = true;
        }
      }
    }
  }

  /**
   * This methods updates all user statistics acooring to the game state
   * 
   * @author pkoenig TODO
   */
  public void updateStatistics() {

  }

}