package network.messages;

/** @author lurny */

public class StartGameMessage extends Message {
  private static final long serialVersionUID = 1L;
  private int countdown;

  public StartGameMessage(String from, int countdown) {
    super(MessageType.START_GAME, from);
    this.setCountdown(countdown);
  }

  public int getCountdown() {
    return countdown;
  }

  public void setCountdown(int countdown) {
    this.countdown = countdown;
  }

}
