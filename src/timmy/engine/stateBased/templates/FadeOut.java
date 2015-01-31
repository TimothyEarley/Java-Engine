package timmy.engine.stateBased.templates;

import java.awt.Color;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.stateBased.Transition;


public class FadeOut extends Transition {

	private int totalSteps, cachedTotalSteps;
	private int now;
	private Color color;
	private int width, height;

	public FadeOut(int nextState, int time, Color color, int width, int height) {
		super(nextState);
		if (time <= 0)
			time = 1;
		cachedTotalSteps = time;
		reset();
		this.color = color;
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(GraphicsHelper gh) {
		float opacity = now / (float) totalSteps;
		if (opacity < 0 || opacity > 1)
			return;
		gh.setAlpha(opacity);
		gh.setColor(color);
		gh.fillRect(0, 0, width, height);
		gh.setAlpha(1f);
	}

	@Override
	public boolean update(Input input, int delta) {
		now+=delta;
		if (now >= totalSteps) {
			reset();
			return true;
		}
		return false;
	}

	private void reset() {
		totalSteps = cachedTotalSteps;
		now = 0;
	}

}
