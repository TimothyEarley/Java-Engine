package timmy.engine.stateBased.templates;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.stateBased.Transition;

public class EmptyTransition extends Transition {

	public EmptyTransition(int nextState) {
		super(nextState);
	}

	@Override
	public void render(GraphicsHelper gh) {
		// Do nothing
	}

	@Override
	public boolean update(Input input, int delta) {
		return true;
	}

}
