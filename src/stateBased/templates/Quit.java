package stateBased.templates;

import gui.GraphicsHelper;
import in.Input;
import stateBased.Transition;

public class Quit extends Transition {

	public Quit() {
		super(0);
	}
	
	public Quit(int nextState) {
		super(nextState);
	}

	@Override
	public void render(GraphicsHelper gh) {
	}

	@Override
	public boolean update(Input input) {
		System.exit(0);
		return false;
	}

}
