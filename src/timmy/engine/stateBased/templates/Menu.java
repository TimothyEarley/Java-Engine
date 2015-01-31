package timmy.engine.stateBased.templates;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.stateBased.GameState;
import timmy.engine.stateBased.StateBasedGame;

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
	public void update(StateBasedGame game, Input input, int delta) {
		if (btns != null)
			for (MenuButton btn : btns)
				btn.update(this, game, input);
		
	}

}
