package stateBased;

import in.Input;
import gui.GraphicsHelper;

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
