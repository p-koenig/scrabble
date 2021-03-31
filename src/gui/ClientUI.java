package gui;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** @author mschmauc */

/**
 * This class implements the GUI for the client including all gui elements that come along after
 * joining the game server. The class extends Application and will initialize the client login panel
 * in the createLoginPanel() method using the launch() method from javaFX Also the GamePanel is
 * being created by using the createGamePanel() method from the GamePanel class
 */

public class ClientUI extends Application { // implements sender?
  private Parent root;
  private static ClientUI instance;

  public static void main(String[] args) {
    launch();
  }

  public static ClientUI getInstance() {
    if (instance == null) {
      instance = new ClientUI();
    }
    return instance;
  }

  public ClientUI() {
    createGamePanel();
    // createLoginPanel();
  }

  // private void createLoginPanel() {
  // launch();
  // }

  private void createGamePanel() {
    // TODO: wird eventuell in eigener Klasse erzeugt, damit man auch von der ServerUI gut drauf
    // zugreifen kann und genau das gleiche Panel hat

  }

  public Parent getParent() {
    return this.root;
  }


  @Override
  public void start(Stage primaryStage) throws Exception {
    this.root = FXMLLoader.load(new File(FileParameters.fxmlPath).toURI().toURL());
    Scene scene1 = new Scene(root);
    primaryStage.setScene(scene1);
    primaryStage.setTitle("Scrabble3");
    primaryStage.show();
  }
}
