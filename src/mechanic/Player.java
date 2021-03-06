package mechanic;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.GameSettings;
import gui.GamePanelController;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import network.client.ClientProtocol;
import network.messages.AddTileMessage;
import network.messages.RemoveTileMessage;
import network.server.Server;
import util.Sound;

/**
 * The Player Class includes PlayerData, containing nickname and avatar. Local GameSettings that
 * should be loaded automatically like volume settings are Player attributes. Also a player's rack
 * is kept local and managed with attributes and methods of this class. A player object can be read
 * from and stored to a JSON file, ignoring the rack methods and attributes.
 *
 * @author ldreyer
 */

public class Player {

  private PlayerData info;

  private int volume;
  private String customGameSettings;

  private Field[] rack;
  private ClientProtocol client = null;
  private Server server = null;
  private GamePanelController gpc = null;

  static final int RACK_FIELDS = 12;

  /** Creates player instance. */

  public Player(String nickname) {
    this.info = new PlayerData(nickname);

    this.rack = new Field[RACK_FIELDS];
    for (int i = 0; i < RACK_FIELDS; i++) {
      this.rack[i] = new Field(i, -1);
    }
  }

  /**
   * This constructor is used for initializing a Player Object with an ObjectMapper from a JSON
   * file.
   *
   * @author ldreyer
   */
  @JsonCreator
  public Player(@JsonProperty("nickname") String nickname, @JsonProperty("avatar") String avatar,
      @JsonProperty("volume") int volume,
      @JsonProperty("customGameSettings") String customGameSettings,
      @JsonProperty("gameCount") int gameCount, @JsonProperty("bestTurn") int bestTurn,
      @JsonProperty("score") int score, @JsonProperty("bestWord") String bestWord,
      @JsonProperty("playTime") int playTime, @JsonProperty("wins") int wins,
      @JsonProperty("playedTiles") int playedTiles) {
    info = new PlayerData(nickname);
    info.setAvatar(avatar);
    this.volume = volume;
    this.customGameSettings = customGameSettings;

    info.setStatistics(gameCount, bestTurn, bestWord, score, playTime, wins, playedTiles);
    this.rack = new Field[RACK_FIELDS];
    for (int i = 0; i < RACK_FIELDS; i++) {
      this.rack[i] = new Field(i, -1);
    }
  }

  /*
   * PLAYER INFO
   */

  /**
   * gets the variable playerInfo of the current instance.
   */
  @JsonIgnore
  public PlayerData getPlayerInfo() {
    return this.info;
  }

  /**
   * sets the variable nickname of the current instance.
   */
  public void setNickname(String nickname) {
    this.info.setNickname(nickname);
  }

  /**
   * gets the variable nickname of the current instance.
   */
  public String getNickname() {
    return this.info.getNickname();
  }

  /**
   * sets the variable avatar of the current instance.
   */
  public void setAvatar(String avatar) {
    this.info.setAvatar(avatar);
  }

  /**
   * gets the variable avatar of the current instance.
   */
  public String getAvatar() {
    return this.info.getAvatar();
  }

  /**
   * gets the variable playerStatistics of the current instance.
   */
  @JsonIgnore
  public PlayerStatistics getStatistics() {
    return this.info.getPlayerStatistics();
  }

  /**
   * gets the variable gameCount of the current instance.
   */
  public int getGameCount() {
    return getStatistics().getGameCount();
  }

  /**
   * gets the variable bestTurn of the current instance.
   */
  public int getBestTurn() {
    return this.getStatistics().getBestTurn();
  }

  /**
   * gets the variable bestWord of the current instance.
   */
  public String getBestWord() {
    return this.getStatistics().getBestWord();
  }

  /**
   * gets the variable winner of the current instance.
   */
  public int getPlayTime() {
    return this.getStatistics().getPlayTime();
  }

  /**
   * gets the variable score of the current instance.
   */
  public int getScore() {
    return this.getStatistics().getScore();
  }

  /**
   * gets the variable wins of the current instance.
   */
  public int getWins() {
    return this.getStatistics().getWins();
  }

  /**
   * gets the variable playedTiles of the current instance.
   */
  public int getPlayedTiles() {
    return this.getStatistics().getPlayedTiles();
  }



  /*
   * RACK METHODS
   */

  /**
   * gets the rackTile at parameter index.
   */
  public Tile getRackTile(int index) {
    return this.rack[index].getTile();
  }

  /**
   * sets a Tile onto the rack at Index index.
   */
  public void setRackTile(int index, Tile tile) {

    this.rack[index].setTile(tile);
  }

  /**
   * This method searches the rack for the first field, that is not covered by a tile.
   *
   * @return emptyField
   */

  @JsonIgnore
  public Field getFreeRackField() { // rack koordinate fortlaufend in xCoordinate
    int i = 0;
    while (rack[i].getTile() != null) {
      i++;
    }

    return rack[i];
  }

  public Field getRackField(int index) {
    return rack[index];
  }

  /**
   * This method takes a tile and puts it on a free field on the player's rack.
   * 
   */
  public void addTileToRack(Tile tile) {
    tile.setField(getFreeRackField());
    tile.setOnRack(true);
    tile.setOnGameBoard(false);
  }

  /**
   * This method adds a List of tiles on the player's rack.
   *
   * @author lurny
   */
  public void addTilesToRack(List<Tile> tileList) {
    for (Tile t : tileList) {
      addTileToRack(t);
    }
  }

  /**
   * This method is used to remove the tile at position index from the rack.
   */
  public Tile removeRackTile(int index) {
    Tile tile = rack[index].getTile();
    rack[index].setTileOneDirection(null);
    return tile;
  }

  /**
   * This method creates a list of all tiles on the rack, ignoring empty fields.
   */

  @JsonIgnore
  public List<Tile> getRackTiles() {
    List<Tile> tiles = new ArrayList<Tile>();
    for (int i = 0; i < rack.length; i++) {
      if (rack[i].getTile() != null) {
        tiles.add(rack[i].getTile());
      }
    }
    return tiles;
  }

  @JsonIgnore
  public int getTileCountOnRack() {
    return this.getRackTiles().size();
  }

  /**
   * Takes indices of two rack fields and moves the tile from the before-index to the after-index.
   * If the operation was successful the method returns true.
   *
   * @author ldreyer
   */

  public void reorganizeRackTile(int indexBefore, int indexAfter) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        if (rack[indexBefore].getTile() == null || rack[indexAfter].getTile() != null) {
          gpc.indicateInvalidTurn(getNickname(), "Invalid Field Selection.");
          return;
        }
        Tile t = rack[indexBefore].getTile();
        removeRackTile(indexBefore);
        setRackTile(indexAfter, t);
        t.setField(rack[indexAfter]);
        Sound.playMoveTileSound();
        gpc.moveToRack(t, indexBefore, -1);
      }
    });
  }

  /**
   * This method checks if rack is free at the given index. If true, the tile is added to the rack
   * and the server is informed about the update, by sending a remove Tile Message for the tile that
   * has been previously on the gameboard.
   *
   * @author ldreyer
   */

  public void moveToRack(Tile tile, int newIndex) {
    if (newIndex == -1 && getFreeRackField() != null) {
      newIndex = getFreeRackField().getxCoordinate();
    }

    if (newIndex == -1 || rack[newIndex].getTile() != null) {
      gpc.indicateInvalidTurn(this.getNickname(), "Field on Rack not free.");
      return;
    }

    RemoveTileMessage rtm = new RemoveTileMessage(this.getNickname(),
        tile.getField().getxCoordinate(), tile.getField().getyCoordinate());

    if (this.isHost()) {
      server.sendToAll(rtm);
      server.getGameController().removeTileFromGameBoard(this.getNickname(),
          tile.getField().getxCoordinate(), tile.getField().getyCoordinate());
      tile.setField(getRackField(newIndex));
      tile.setOnRack(true);
      tile.setOnGameBoard(false);
      AddTileMessage atm = new AddTileMessage(this.getNickname(), tile, newIndex, -1);
      server.updateServerUi(atm);
    } else {
      client.sendToServer(rtm);
      gpc.removeTile(rtm.getX(), rtm.getY(), (rtm.getY() == -1));
      tile.setField(getRackField(newIndex));
      tile.setOnRack(true);
      tile.setOnGameBoard(false);
      gpc.addTile(tile);
      Sound.playMoveTileSound();
    }
  }

  /**
   * This method checks if the selected field on the rack contains a tile. If true, the selected
   * tile is sent to the server, which handles the rest of the move and will reply if the move was
   * found valid by the game controller as well.
   *
   * @author ldreyer
   */

  public void moveToGameBoard(int oldIndex, int newX, int newY) {
    Tile t = rack[oldIndex].getTile();
    if (t == null) {
      gpc.indicateInvalidTurn(this.getNickname(), "Selected field on Rack is empty.");
      return;
    }
    AddTileMessage atm = new AddTileMessage(this.getNickname(), t, newX, newY);
    if (this.isHost()) {
      server.handleAddTileToGameBoard(atm);
    } else {
      client.sendToServer(atm);
    }
  }

  /**
   * Moves a tile t (which has to be currently on the rack, I.e. 
   * has yCoordinate==-1) to Field f by calling removeRackTile and setTile.
   *
   * @author pkoenig
   */
  public void personalMoveToGameBoard(Tile t, Field f) {
    if (t.getField().getyCoordinate() != -1) {
      System.out.println("\nINVALID: personalMovetoGameboard + \n");
    } else {
      this.removeRackTile(t.getField().getxCoordinate());
      f.setTile(t);
    }
  }

  /**
   * gets the variable volume of the current instance.
   */
  public int getVolume() {
    return volume;
  }

  /**
   * gets the variable winner of the current instance.
   */
  public void setVolume(int volume) {
    this.volume = volume;
  }

  /**
   * gets the variable customGameSettings of the current instance.
   */
  public String getCustomGameSettings() {
    return customGameSettings;
  }

  /**
   * sets the variable customGameSettings of the current instance.
   */
  public void setCustomGameSettings(String customGameSettings) {
    this.customGameSettings = customGameSettings;
  }

  /**
   * gets the variable host of the current instance.
   */
  @JsonIgnore
  public boolean isHost() {
    return this.info.isHost();
  }

  /**
   * sets the variable host of the current instance.
   */
  public void setHost(boolean host) {
    this.info.setHost(host);
  }

  /**
   * sets the variable server of the current instance.
   */
  public void setServer(Server s) {
    this.server = s;
  }

  /**
   * gets the variable server of the current instance.
   */
  @JsonIgnore
  public Server getServer() {
    return this.server;
  }

  /**
   * gets the variable client of the current instance.
   */
  @JsonIgnore
  public ClientProtocol getClientProtocol() {
    return this.client;
  }

  /**
   * gets the variable gpc of the current instance.
   */
  @JsonIgnore
  public GamePanelController getGamePanelController() {
    return this.gpc;
  }

  /**
   * sets the variable gpc of the current instance.
   */
  public void setGamePanelController(GamePanelController gpc) {
    this.gpc = gpc;
  }

  /**
   * This method is called, when a player hosts a game.
   *
   * @author nilbecke
   */
  public void host() {
    this.getPlayerInfo().setHost(true);
    this.server = new Server(this, this.customGameSettings);

    Runnable r = new Runnable() {
      public void run() {
        server.listen(false);
      }
    };
    new Thread(r).start();
  }

  /**
   * This method is called, when a client connects to a server.
   *
   * @author nilbecke
   */
  public void connect(String ip) {
    this.getPlayerInfo().setHost(false);

    this.client = new ClientProtocol(ip, GameSettings.port, this);

    if (this.client.isOk()) {
      this.client.start();
    }
  }
  
  /**
   * This method is called, when a player plays the tutorial.
   *
   * @author nilbecke
   */
  public void playTutorial() {
    this.getPlayerInfo().setHost(true);
    this.server = new Server(this, null);

    Runnable r = new Runnable() {
      public void run() {
        server.listen(true);
      }
    };
    new Thread(r).start();
  }


}
