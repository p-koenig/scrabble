package network.messages;

import game.GameStatistic;
import java.util.HashMap;

/**
 * This Message sends relevant statistcs from the played and finished game to every player.
 *
 * @author lurny
 */

public class GameStatisticMessage extends Message {

  private static final long serialVersionUID = 1L;
  private HashMap<String, GameStatistic> gameStatistics = new HashMap<>();

  /**
   * This method creates an instance of the class.
   */
  public GameStatisticMessage(String from, HashMap<String, GameStatistic> gameStatistics) {
    super(MessageType.GAME_STATISTIC, from);
    this.gameStatistics = gameStatistics;
  }

  /**
   * gets the variable gameStatistics of the current instance.
   */
  public HashMap<String, GameStatistic> getGameStatistics() {
    return gameStatistics;
  }
}
