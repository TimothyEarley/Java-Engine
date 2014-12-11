package games;

import gui.GraphicsHelper;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class GameApplet extends Applet {
	private static final long serialVersionUID = 6642100018706244474L;	
	
	/**
	 * Use this game
	 */
	protected BasicGame game;
	
	@Override
	public void init() {
		initGame();
		game.start();
	}
	
	protected abstract void initGame();
	
	@Override
	public void paint(Graphics g) {
		game.render(game, new GraphicsHelper((Graphics2D) g));
	}
}
