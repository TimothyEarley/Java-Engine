package timmy.engine.test;

import java.awt.Color;
import java.io.IOException;

import timmy.engine.games.BasicGame;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.util.Sound;

public class Test extends BasicGame {

	Sound sound;

	public Test(String title) {
		super(title);
	}

	public static void main(String[] args) {
		new Test("Hello").start();
	}

	@Override
	protected void init() throws IOException {
		sound = new Sound("/beep.wav");
	}

	@Override
	public void render(BasicGame game, GraphicsHelper gh) {
		gh.setColor(Color.CYAN);
		gh.fillRect(-100, -100, 1000, 1000);
		gh.setColor(Color.RED);
		gh.fillRect(0, 0, game.display.size.x, game.display.size.y);
	}

	@Override
	protected void update(Input input) {
		if (Math.random() > 0.9 )
			playSound(sound);
	}

}
