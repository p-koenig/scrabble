package gui;

import game.GameStatistic;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mechanic.Player;

/**
 * This class launches the leaderboard screen after a game has finished. Get's initialized with the
 * GameState at the end of the game.
 *
 * @author nilbecke
 *
 */

public class LeaderboardScreen extends Application {

  private double xoffset;
  private double yoffset;
  private static HashMap<String, GameStatistic> gameStatistics = new HashMap<>();
  private static Player player;
  
  /**
   * Start a new Leaderboard screen. 
   *
   * @param gameStatistic as statistics of current game.
   * @param p as current player.
   */

  public LeaderboardScreen(HashMap<String, GameStatistic> gameStatistic, Player p) {
    gameStatistics = gameStatistic;
    player = p;
  }



  @Override
  public void start(Stage stage) {
    Parent root = null;
    Font.loadFont(getClass().getResourceAsStream("Scrabble.ttf"), 14);
    try {
      root = FXMLLoader.load(getClass().getResource("/fxml/LeaderboardScreen.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/ScrabbleIcon.png")));

    root.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        xoffset = event.getSceneX();
        yoffset = event.getSceneY();
      }
    });

    root.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        stage.setX(event.getScreenX() - xoffset);
        stage.setY(event.getScreenY() - yoffset);
      }
    });

    stage.setTitle("Leaderboard");
    stage.show();

  }

  /**
   * Get current game statistics.
   *
   * @return gameStatistics as instance of current game.
   */

  public static HashMap<String, GameStatistic> getGameStatistic() {
    return gameStatistics;
  }

  /**
   * Get current player.
   *
   * @return player as current player.
   */

  public static Player getPlayer() {
    return player;
  }

}
