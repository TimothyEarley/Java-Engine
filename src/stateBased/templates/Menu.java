package stateBased.templates;

import gui.GraphicsHelper;
import in.Input;
import stateBased.GameState;
import stateBased.StateBasedGame;

public abstract class Menu extends GameState {

	protected MenuButton[] btns;

	@Override
	public void render(StateBasedGame stateBasedGame, GraphicsHelper gh) {
		renderBack(stateBasedGame, gh);
		
		if (btns != null)
			for (MenuButton btn : btns)
				btn.render(gh);
	
		renderFront(stateBasedGame, gh);
	}

	protected void renderBack(StateBasedGame stateBasedGame, GraphicsHelper gh) {
	}
	
	protected void renderFront(StateBasedGame stateBasedGame, GraphicsHelper gh) {
	}

	@Override
	public void update(StateBasedGame game, Input input) {
		if (btns != null)
			for (MenuButton btn : btns)
				btn.update(this, game, input);
		
	}

}
