package gui;


/** 
 * @author nilbecke
 * Launch the Lobby GUI
 * **/

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import mechanic.Player;

public class LobbyScreen extends Application {

	private Parent root;
	private static Player player;

	@FXML
	private Label ip;
	
	public LobbyScreen(Player current) {
		player = current;
	}
	
	/**
	 * Reads the "Lobby.fxml" file (@author nilbecke) to create the Lobby
	 **/
	@Override
	public void start(Stage stage) {
		try {
			Font.loadFont(getClass().getResourceAsStream("Scrabble.ttf"), 14);
			this.root = FXMLLoader.load(getClass().getResource("Lobby.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Lobby");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static Player getPlayer() {
		return player;
	}

}
