package timmy.engine.tilemap;

import timmy.engine.graphics.Sprite;

public class Tile {

	public static int BREAKABLE = 0x0;
	public static int WALKABLE = 0x1;
	public static int TRANSPARENT = 0x2;
	public static int PROJECTILECATCHER = 0x3;

	public static final Tile Void = new Tile(new Sprite(1, 1, 0x0), TRANSPARENT);

	/**
	 * Use static sprites when using the same multiple times
	 */
	private Sprite sprite;

	private int[] type;

	public Tile(Sprite sprite, int... type) {
		this.type = type;
		this.sprite = sprite;
	}

	public boolean isType(int t) {
		for (int type2 : type) {
			if (type2 == t)
				return true;
		}
		return false;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

}
