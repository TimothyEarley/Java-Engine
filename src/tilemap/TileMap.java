package tilemap;

import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2i;

public class TileMap {

	private Tile[] tiles;
	private int width, height;
	
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
	
	public void render(GraphicsHelper gh, Vector2i offset) {
		// TODO render map here
	}
}
