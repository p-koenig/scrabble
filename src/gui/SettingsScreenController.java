package gui;

import java.net.URL;
import java.util.ResourceBundle;

import game.GameSettings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mechanic.Player;

/** @author nilbecke **/
/** Handles user-inputs in the Gamesettings screen **/

public class SettingsScreenController implements EventHandler<ActionEvent> {

	private Player currentPlayer;
	private static SettingsScreenController instance;
	private static GameSettings settings;

	@FXML
	private Label username, time, overtime, score, size, bingo, ai;
	@FXML
	private ImageView avatar;
	@FXML
	private Button user;

	/**
	 * Initialize the Settings Screen with username, avatar and all current settings
	 */

	@FXML
	public void initialize() {
		instance = this;
		this.currentPlayer = LobbyScreen.getInstance().getPlayer();
		this.username.setText(this.currentPlayer.getNickname());
		this.avatar.setImage(new Image("file:" + FileParameters.datadir + this.currentPlayer.getAvatar()));
		settings = SettingsScreen.getSettings();
		time.setText(settings.getTimePerPlayer() + "");
		overtime.setText(settings.getMaxOvertime()+"");
		score.setText(settings.getMaxScore()+"");
		size.setText(settings.getGameBoardSize()+"");
		bingo.setText(settings.getBingo()+"");
		ai.setText(settings.getAiDifficulty().substring(0,1).toUpperCase()+settings.getAiDifficulty().substring(1));

	}

	/**
	 * Handles all user inputs
	 */
	@Override
	public void handle(ActionEvent e) {
		String s = ((Node) e.getSource()).getId();
		System.out.println(s);
		switch (s) {
		case "user":
			new UserSettingsScreen().start(new Stage());
			break;
		}
	}

	/**
	 * Get a reference on the current Game settings controller
	 * 
	 * @return: Current instance of the settings controller if present, null
	 *          otherwise
	 */
	public static SettingsScreenController getInstance() {
		return instance;
	}

	/**
	 * Updates the displayed username
	 * 
	 * @param newName: updated username
	 */
	public void setUserLabel(String newName) {
		this.username.setText(newName);
	}
}