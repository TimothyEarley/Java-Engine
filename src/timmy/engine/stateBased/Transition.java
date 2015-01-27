package timmy.engine.stateBased;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;

public abstract class Transition {

	int nextState;
	
	public Transition(int nextState) {
		this.nextState = nextState;
	}
	
	abstract public void render(GraphicsHelper gh);

	/**
	 * 
	 * @param input
	 * @return if ready to move on
	 */
	abstract public boolean update(Input input);

	public int getNextState() {
		return nextState;
	}
	
	
	
}
