/** @author ldreyer */
package network.messages;

import java.util.List;
import mechanic.Field;

public class UpdateFieldMessage extends Message {

  private static final long serialVersionUID = 1L;
  private List<Field> fields;


  public UpdateFieldMessage(String from, List<Field> fields) {
    super(MessageType.UPDATE_GAMEBOARD, from);
    this.fields = fields;
  }


  public List<Field> getFields() {
    return fields;
  }

}
