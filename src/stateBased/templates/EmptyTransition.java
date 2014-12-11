package stateBased.templates;

import gui.GraphicsHelper;
import in.Input;
import stateBased.Transition;

public class EmptyTransition extends Transition {

	public EmptyTransition(int nextState) {
		super(nextState);
	}

	@Override
	public void render(GraphicsHelper gh) {
		// Do nothing
	}

	@Override
	public boolean update(Input input) {
		return true;
	}

}
