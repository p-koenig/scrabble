package gui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import mechanic.Player;
import mechanic.Tile;
import network.messages.Message;
import network.messages.SendChatMessage;
import network.messages.UpdateChatMessage;


/**
 * Class to handle all Chat Messages in Lobby and in game.
 * 
 * @author nilbecke
 *
 */
public class ChatController implements Sender {

  private Player player;

  public ChatController(Player p) {
    this.player = p;
  }

  /**
   * updates the chat field with a given message.
   * 
   * @author mschmauch
   * @param message content of message
   * @param dateTime time, message was sent
   * @param sender username of player sending message
   */
  public String updateChat(String message, LocalDateTime dateTime, String sender) {

    String newChatMessage = "";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    newChatMessage = newChatMessage + sender + ", ";
    newChatMessage = newChatMessage + dateTime.format(dtf) + ": ";
    newChatMessage += message;
    return newChatMessage;

  }

  @Override
  public void sendChatMessage(String sender, String message) {
    Message m;
    if (this.player.isHost()) {
      m = new UpdateChatMessage(sender, message, LocalDateTime.now());
    } else {
      m = new SendChatMessage(sender, message, LocalDateTime.now());
    }
    sendMessage(m);
  }

  /**
   * Sends a given message to all players.
   * 
   * @param m The Message to be sent
   * @return true if message was sent, false otherwise
   */
  public boolean sendMessage(Message m) {
    try {
      if (this.player.isHost()) {
        this.player.getServer().sendToAll(m);

      } else {
        this.player.getClientProtocol().sendToServer(m);
      }
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public void sendTileMove(String nickName, Tile tile, int newX, int newY) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sendCommitTurn(String nickName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void sendDisconnectMessage(String playerID) {
    // TODO Auto-generated method stub

  }



}
