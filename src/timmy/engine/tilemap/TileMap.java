package timmy.engine.tilemap;

import java.util.HashMap;

import timmy.engine.graphics.Animation;
import timmy.engine.graphics.Sprite;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.Random;
import timmy.engine.util.vectors.Vector2i;

public class TileMap {

	/**
	 * For any mob on the map in pixels
	 */
	public float movePrecission = 2;

	private Tile[] tiles;
	private int width, height, tileWidth, tileHeight;
	private Sprite sprite;
	private boolean spriteCreatorIsRunning;

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

		spriteCreatorIsRunning = false;
		sprite = createSprite(false);

	}

	private Sprite createSprite(boolean thread) {
		if (spriteCreatorIsRunning)
			return sprite;
		// Run in new thread if not important
		if (thread) {
			Thread spriteCreator = new Thread(new Runnable() {
				@Override
				public void run() {
					sprite = createSprite(false);
				}
			}, "Sprite Creator");
			spriteCreator.start();
			return sprite;
		}

		spriteCreatorIsRunning = true;

		int[] pixels = new int[width * tileHeight * height * tileHeight];

		for (int xT = 0; xT < width; xT++) {
			for (int yT = 0; yT < height; yT++) {
				Tile tile = getTile(xT, yT);
				Sprite s = tile.getSprite();
				for (int x = 0; x < tileWidth; x++) {
					if (s.getWidth() <= x)
						continue;
					for (int y = 0; y < tileHeight; y++) {
						if (s.getWidth() <= y)
							continue;
						int col = s.getRGB(x, y);
						pixels[xT * tileWidth + x + (yT * tileHeight + y) * (width * tileWidth)] = col;
					}
				}
			}
		}
		spriteCreatorIsRunning = false;
		return new Sprite(pixels, width * tileWidth, height * tileHeight);
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
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void update() {
		if (Animation.updated) {
			sprite = createSprite(true);
		}
	}

	public void render(GraphicsHelper gh, Vector2i offset, int zoomfactor) {

		gh.drawImage(sprite, offset, sprite.getWidth() * zoomfactor, sprite.getHeight() * zoomfactor);

	}
}
