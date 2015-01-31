package timmy.engine.stateBased.templates;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.stateBased.Transition;

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
	public boolean update(Input input, int delta) {
		System.exit(0);
		return false;
	}

}
