package tilemap;

import timmy.engine.graphics.Sprite;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.util.vectors.Vector2i;

public class Tile {

	public static final Tile Void = null;
	
	/**
	 * Use static sprites when using the same multiple times
	 */
	private Sprite sprite;
	
	private Type[] type;
	
	public Tile(Type ... type) {
		this.type = type;
	}
	
	public void render(GraphicsHelper gh, Vector2i loc) {
		gh.drawImage(sprite, loc);
	}
	
	public boolean isType(Type t) {
		for (Type type2 : type) {
			if (type2.equals(t))
				return true;
		}
		return false;
	}

}
