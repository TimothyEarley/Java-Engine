package timmy.engine.test;

import java.io.IOException;

import timmy.engine.games.BasicGame;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.in.Input;
import timmy.engine.tilemap.Tile;
import timmy.engine.tilemap.TileMap;
import timmy.engine.tilemap.entities.Entity;
import timmy.engine.util.Sound;
import timmy.engine.util.vectors.Vector2i;

import com.sun.glass.events.KeyEvent;

public class Test extends BasicGame {

	Sound sound;
	TileMap map;
	private Vector2i offset = new Vector2i(0, 0), cursor;
	Entity entity;
	Tile tile;

	public Test(String title) {
		super(title, 1600, 900);
	}

	public static void main(String[] args) {
		new Test("Hello").start();
	}

	@Override
	protected void init() throws IOException {
		sound = new Sound("/beep.wav");
		map = new TileMap("/map.png", Tiles.getColours(), 32);
		entity = new Entity(new Vector2i(10, 10), map);
		DEBUG = true;
		ups = 60;
		setAlwaysRender(true);
	}

	@Override
	public void render(BasicGame game, GraphicsHelper gh) {
		map.render(gh, offset, 2);

		entity.render(gh, offset);
		
		if (tile != null) {
			gh.drawStringCentered(tile.toString(), game.getWidth()/2, game.getHeight()/2, 100);
			gh.setAlpha(0.3f);
			gh.fillRect(cursor, 2 * map.getTileWidth(), 2 * map.getTileHeight());
		}
		
	}

	@Override
	protected void update(Input input, int delta) {
		// if (Math.random() > 0.9 )
		// playSound(sound);

		int speed = delta;

		if (input.isKeyPressed(KeyEvent.VK_A)) {
			offset.x += speed;
		}
		if (input.isKeyPressed(KeyEvent.VK_D)) {
			offset.x -= speed;
		}
		if (input.isKeyPressed(KeyEvent.VK_W)) {
			offset.y += speed;
		}
		if (input.isKeyPressed(KeyEvent.VK_S)) {
			offset.y -= speed;
		}

		map.update();

		// Tile marking
		Vector2i mouse = input.getMouse();
		mouse.sub(offset);
		int x = mouse.x/map.getTileWidth();
		int y = mouse.y/map.getTileHeight();
		x /= 2;
		y /= 2;
		tile = map.getTile(x, y);
		cursor = new Vector2i(2*x * map.getTileWidth(), 2*y * map.getTileWidth());
		cursor.add(offset);
	}

}
