
package orage.model.superviser;

public class ActionEvent {

	private String message;
	
	public ActionEvent (String msg) {
		message = msg;
	}
	
	public String getMessage() {
		return message;
	}
}
