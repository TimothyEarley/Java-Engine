package stateBased;

import in.Input;
import gui.GraphicsHelper;

public abstract class GameState {
	
	public boolean initialized = false;

	abstract public void render(StateBasedGame stateBasedGame, GraphicsHelper gh);

	abstract public void update(StateBasedGame stateBasedGame, Input input);

	abstract public void init(StateBasedGame game);
	
	/**
	 * Reenter code here, e.g. reset variables
	 */
	public void reenter() {
		
	}

}
