package network.messages;

import java.time.LocalDateTime;

/**
 * After the server recieved the sendChatMessage, he sends the UpdateChatMessage to all players to
 * update the chat.
 * 
 * @author mschmauc
 */

public class UpdateChatMessage extends Message {
  private static final long serialVersionUID = 1L;
  private String text;
  private LocalDateTime dateTime;


  public UpdateChatMessage(String from, String content, LocalDateTime timeStamp) {
    super(MessageType.UPDATE_CHAT, from);
    this.text = content;
    this.dateTime = timeStamp;
  }

  public String getText() {
    return text;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }


}