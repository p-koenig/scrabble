package gui;

import javafx.application.Platform;

/** @Author nilbecke **/

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mechanic.Player;
import util.JSONHandler;

/**
 * This Class is a Basic Handler for all the input options present in the Login
 * Screen
 **/

public class LoginScreenActionHandler extends LoginScreenFXML implements EventHandler<ActionEvent> {

	private Player player;
	private static LoginScreenActionHandler instance;

	@FXML
	private TextField LinkField;
	@FXML
	private Label username;
	@FXML
	private ImageView avatar;
	
	/**
	 * Handles the different Buttons in the Login Screen
	 * 
	 * @param Input event
	 * @see Reaction to given input
	 **/
	@Override
	public void handle(ActionEvent e) {
		if (instance == null) {
			instance = this;
		}
		this.player = new JSONHandler().loadPlayerProfile("resources/playerProfileTest.json");
		if (e.getSource().getClass() != Button.class) {
			join();
		} else {
			Button button = (Button) e.getSource();
			Stage s;
			switch (button.getText()) {
			case "Join":
				this.player.setIsHost(false);
				startLobby();
				s = (Stage) button.getScene().getWindow();
				s.close();
				break;
			case "Host Game":
				this.player.setIsHost(true);
				this.player.setHost(this.player);
				startLobby();
				s = (Stage) button.getScene().getWindow();
				s.close();
				break;
			case "Exit":
				System.exit(0);
				break;
			case "Tutorial":
				OpenTutorial.open();
				break;

			case "Settings":
				//new SettingsScreen().start(new Stage());
				break;
			case "Account":
				new UserSettingsScreen().start(new Stage());
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
	 * Opens Lobby as Host or Player
	 * 
	 * @param isHost: defines if the player who is joining is the host or not
	 */

	public void startLobby() {
		new LobbyScreen(this.player).start(new Stage());

	}

	/**
	 * Access the current instane of the LognScreenController
	 * 
	 * @return current instance of the controller
	 */
	public static LoginScreenActionHandler getInstance() {
		return instance;
	}

	/**
	 * Change the text of the username label
	 * 
	 * @param input: Nickname of current player
	 */
	public void setUsername(String input) {
		this.username.setText(input);
	}
	
	/**
	 * Updates the avatar image of the current player
	 * @param avatar: String to the location of the new avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar.setImage(new Image(avatar));
	}

	/**
	 * Handles the process when a player is trying to join a game via link. It can
	 * be accessed via "enter" after an input in the LinkField or via a press on the
	 * "Join" Button
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
