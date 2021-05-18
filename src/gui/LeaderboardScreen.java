package gui;

import java.io.File;
import java.io.IOException;
import game.GameState;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
  private static GameState gs;

  public LeaderboardScreen(GameState current) {
    gs = current;
  }



  @Override
  public void start(Stage stage) {
    Parent root = null;
    Font.loadFont(getClass().getResourceAsStream("Scrabble.ttf"), 14);
    try {
      root = FXMLLoader
          .load(new File(FileParameters.fxmlPath + "LeaderboardScreen.fxml").toURI().toURL());
    } catch (IOException e) {
      e.printStackTrace();
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.initStyle(StageStyle.UNDECORATED);

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

  public static GameState getGameState() {
    return gs;
  }

}