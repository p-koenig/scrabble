package gui;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/** @Author nilbecke **/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mechanic.Player;

/**
 * This Class is a Basic Handler for all the input options present in the Login Screen
 **/

public class LoginScreenController extends LoginScreen implements EventHandler<ActionEvent> {

  private Player player;
  private static LoginScreenController instance;

  @FXML
  private TextField LinkField;
  @FXML
  private Label username;
  @FXML
  private ImageView avatar;

  /**
   * Handles the different Buttons in the Login Screen.
   * 
   * @param e Input event .
   **/
  @Override
  public void handle(ActionEvent e) {
    if (instance == null) {
      instance = this;
    }
    this.player = currentPlayer;
    if (e.getSource().getClass() != Button.class) {
      join();
    } else {
      Button button = (Button) e.getSource();
      Stage s = (Stage) button.getScene().getWindow();;
      switch (button.getText()) {
        case "Join":
          this.player.setHost(false);
          startLobby();
          s.close();
          break;
        case "Host Game":
          this.player.setHost(true);
          startLobby();
          s = (Stage) button.getScene().getWindow();
          s.close();
          break;
        case "Exit":
          System.exit(0);
          break;
        case "Tutorial":
          OpenExternalScreen
              .open(System.getProperty("user.dir") + "/src/gui/images/ScrabbleRules.pdf");
          break;

        case "Settings":
          // new SettingsScreen().start(new Stage());
          break;
        case "Account":
          openAccount(s);
          break;
        default:
          Alert alert = new Alert(AlertType.ERROR);
          alert.setTitle("Too early");
          alert.setHeaderText(null);
          alert.setContentText("Function for " + button.getText() + " Not Yet Implemented :D");
          alert.show();
      }
    }
  }

  /**
   * Lets a user access the user settings. If no user profile is present the system asks the user if
   * he wants to create one.
   * 
   * @param s defines the Stage which is cloes if the user settings screen is launched
   */
  public void openAccount(Stage s) {
    File f = new File(FileParameters.datadir + ("/playerProfileTest.json"));
    if (f.exists()) {
      s.close();
      new UserSettingsScreen(this.player).start(new Stage());
    } else {
      CustomAlert alert = new CustomAlert(AlertType.CONFIRMATION);
      alert.setTitle("No profile created yet");
      alert.setHeaderText("Create profile?");
      alert.setContentText("Do you want to create a new profile ");
      alert.initStyle(StageStyle.UNDECORATED);

      alert.changeButtonText("Yes", ButtonType.OK);
      alert.changeButtonText("No", ButtonType.CANCEL);

      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        try {
          f.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
        openAccount(s);
      } else {
        alert.close();

      }
    }

  }

  /**
   * Opens Lobby as Host or Player.
   * 
   * 
   */

  public void startLobby() {
    new LobbyScreen(this.player, this.getConnection()).start(new Stage());
  }

  /**
   * Access the current instane of the LognScreenController
   * 
   * @return current instance of the controller
   */
  public static LoginScreenController getInstance() {
    return instance;
  }

  /**
   * Change the text of the username label.
   * 
   * @param input Nickname of current player
   */
  public void setUsername(String input) {

    this.username.setText(input);
  }

  /**
   * Updates the avatar image of the current player.
   * 
   * @param avatar String to the location of the new avatar
   */
  public void setAvatar(String avatar) {
    this.avatar.setImage(new Image(avatar));
  }

  /**
   * Get the inet address to be connected to as client.
   * 
   * @return the input for the inet adress
   */
  public String getConnection() {
    return this.LinkField.getText();
  }

  /**
   * Handles the process when a player is trying to join a game via link. It can be accessed via
   * "enter" after an input in the LinkField or via a press on the "Join" Button.
   **/
  public void join() {

    String gameID = LinkField.getText();
    Alert connection;
    if (gameID.length() == 0) {
      connection = new Alert(AlertType.ERROR);
      connection.setTitle("Connection Error");
      connection.setHeaderText(null);
      connection.setContentText("Must enter a Link");
      connection.show();
    } else {
      connection = new Alert(AlertType.CONFIRMATION);
      connection.setTitle("Connecting to game");
      connection.setHeaderText(null);
      connection.setContentText("Connecting to \"" + gameID + "\"");
    }
    // connection.show();
  }
}
