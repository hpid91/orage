package orage.control;

import orage.model.Model;
import orage.ui.main.OrageWindow;

public abstract class Command {
	
	protected Model model;
	protected OrageWindow view;
	
	public Command(Model m, OrageWindow v) {
		model = m;
		view = v;
	}
	
	public void execute(){}
	
}