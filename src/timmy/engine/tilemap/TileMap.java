package timmy.engine.tilemap;

import java.util.HashMap;

import timmy.engine.games.BasicGame;
import timmy.engine.graphics.Sprite;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.Random;
import timmy.engine.util.vectors.Vector2i;

public class TileMap {

	private Tile[] tiles;
	private int width, height, tileWidth, tileHeight;

	public TileMap(String path, HashMap<Integer, Tile[]> tilecolours, int size) {
		this(path, tilecolours, size, size);
	}

	public TileMap(String path, HashMap<Integer, Tile[]> tilecolours, int tileWidth, int tileHeight) {

		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;

		Sprite map = new Sprite(path);

		width = map.getWidth();
		height = map.getHeight();
		tiles = new Tile[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile[] newtiles = tilecolours.get(map.getRGB(x, y));
				if (newtiles == null || newtiles.length == 0) {
					tiles[x + y * width] = Tile.Void;
				} else if (newtiles.length == 1) {
					tiles[x + y * width] = newtiles[0];
				} else {
					tiles[x + y * width] = newtiles[Random.rand.nextInt(newtiles.length)];
				}
			}
		}

	}

	public TileMap(Tile[] tiles, int width, int height) {
		this.tiles = tiles;
		this.height = height;
		this.width = width;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.Void;
		else
			return tiles[x + y * width];
	}

	public void setTile(int x, int y, Tile tile) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		else
			tiles[x + y * width] = tile;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void render(GraphicsHelper gh, Vector2i offset, BasicGame game) {

		// TODO render
	}
}
