package timmy.engine.test;

import java.io.IOException;
import java.util.HashMap;

import timmy.engine.games.BasicGame;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.tilemap.Tile;
import timmy.engine.tilemap.TileMap;
import timmy.engine.util.Sound;
import timmy.engine.util.vectors.Vector2i;

import com.sun.glass.events.KeyEvent;

public class Test extends BasicGame {

	Sound sound;
	TileMap map;
	private Vector2i offset = new Vector2i(0, 0);

	public Test(String title) {
		super(title, 1600, 900);
	}

	public static void main(String[] args) {
		new Test("Hello").start();
	}

	@Override
	protected void init() throws IOException {
		sound = new Sound("/beep.wav");
		map = new TileMap("/map.png", getColours(), 32);
	}

	private HashMap<Integer, Tile[]> getColours() {
		HashMap<Integer, Tile[]> map = new HashMap<Integer, Tile[]>();
		map.put(0xff00ff00, new Tile[] {Tiles.grass, Tiles.grass2, Tiles.grass3});
		return map;
	}

	@Override
	public void render(BasicGame game, GraphicsHelper gh) {
		map.render(gh, offset, game);
//		gh.setColor(Color.RED);
//		gh.fillRect(20, 10, 200, 100);
	}

	@Override
	protected void update(Input input) {
		// if (Math.random() > 0.9 )
		// playSound(sound);

		int speed = 5;
		
		if (input.keyPressed[KeyEvent.VK_A]) {
			offset.x-=speed;
		}
		if (input.keyPressed[KeyEvent.VK_D]) {
			offset.x+=speed;
		}
		if (input.keyPressed[KeyEvent.VK_W]) {
			offset.y-=speed;
		}
		if (input.keyPressed[KeyEvent.VK_S]) {
			offset.y+=speed;
		}
	}

}
