package timmy.engine.stateBased;

import java.applet.Applet;
import java.io.IOException;

import timmy.engine.games.BasicGame;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.util.Crash;

public abstract class StateBasedGame extends BasicGame {

	/*
	 * public final int Menu = 0; ect.
	 */

	GameState[] gs;
	int currentGameState;
	Transition transition;
	boolean transtioning = false;
	
	public StateBasedGame(Applet applet, GameState[] gs) {
		this(applet, gs, 0);
	}
	
	public StateBasedGame(Applet applet, GameState[] gs, int start) {
		this(applet, gs, start, BasicGame.defWidth, BasicGame.defHeight);
	}
	
	public StateBasedGame(Applet applet, GameState[] gs, int width, int height) {
		this(applet, gs, 0, width, height);
	}
	
	public StateBasedGame(Applet applet, GameState[] gs, int start, int width, int height) {
		super(applet, width, height);
		this.gs = gs;
		this.currentGameState = start;
	}

	public StateBasedGame(String title, GameState[] gs) {
		this(title, gs, 0);
	}

	public StateBasedGame(String title, GameState[] gs, int start) {
		this(title, gs, start, BasicGame.defWidth, BasicGame.defHeight);
	}

	public StateBasedGame(String title, GameState[] gs, int width, int height) {
		this(title, gs, 0, width, height);
	}

	public StateBasedGame(String title, GameState[] gs, int start, int width, int height) {
		super(title, width, height);
		this.gs = gs;
		this.currentGameState = start;
	}

	@Override
	protected void init() throws IOException {
		enter(currentGameState);
	}

	@Override
	public void render(BasicGame game, GraphicsHelper gh) {
		gs[currentGameState].render(this, gh);
		if (transtioning) {
			transition.render(gh);
		}
	}

	@Override
	protected void update(Input input) {
		if (transtioning) {
			if (transition.update(input)) {
				transtioning = false;
				enter(transition.getNextState());
			}
		} else
			gs[currentGameState].update(this, input);
	}

	public void enter(Transition trans) {
		transtioning = true;
		this.transition = trans;
	}

	public void enter(int id) {
		if (id < 0 || id >= gs.length) {
			Crash.crash("Invalid GameState: " + id);
			return;
		} else {
			currentGameState = id;
			if (!gs[id].initialized) {
				gs[id].init(this);
				gs[id].initialized = true;
			}
			else {
				gs[id].reenter();
			}
		}
	}

	public void initAll() {
		for (GameState state : gs) {
			if (!state.initialized)
				state.init(this);
		}
	}

}
