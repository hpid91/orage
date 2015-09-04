
package orage.model.superviser;

import java.util.EventListener;


public interface ActionLoggerListener extends EventListener  {

	public void newMessage(ActionEvent e);
}
