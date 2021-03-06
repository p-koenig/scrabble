package game;

import gui.TutorialController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mechanic.Player;

/**
 * This class lets a user play the tutorial.
 * 
 *
 * @author nilbecke
 *
 */

public class Tutorial {

  private Player player;

  /**
   * creates an instance of the class.
   */
  public Tutorial(Player player) {
    this.player = player;
    this.player.playTutorial();
    this.player.getServer().startGame();
    startScreen();
  }

  /**
   * Launches the main game screen used for the tutorial.
   */

  public void startScreen() {
    try {
      Stage stage = new Stage(StageStyle.DECORATED);
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TutorialScreen.fxml"));
      stage.setScene(new Scene(loader.load()));
      TutorialController controller = loader.getController();
      player.getServer().setGamePanelController(controller);
      player.setGamePanelController(controller);
      controller.initData(player);
      stage.setOnCloseRequest(e -> controller.closeTutorial());
      stage.setTitle("Scrabble3");
      stage.getIcons().add(new Image(getClass().getResourceAsStream("/ScrabbleIcon.png")));
      stage.show();
      stage.setResizable(false);
      TutorialController.getInstance().showTip(0);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
