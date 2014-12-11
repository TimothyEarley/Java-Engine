package test;

import games.BasicGame;
import gui.GraphicsHelper;
import in.Input;

import java.awt.Color;
import java.io.IOException;

public class Test extends BasicGame {

	public Test(String title) {
		super(title);
	}

	public static void main(String[] args) {
		new Test("Hello").start();
	}

	@Override
	protected void init() throws IOException {
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
	}

}
