package timmy.engine.tilemap.entities;

import timmy.engine.graphics.Sprite;
import timmy.engine.gui.GraphicsHelper;
import timmy.engine.tilemap.TileMap;
import timmy.engine.util.vectors.Vector2i;

public class Entity {

	protected TileMap map;
	protected Vector2i pos;
	protected Sprite sprite = Sprite.VOID;
	
	public Entity(Vector2i pos, TileMap map) {
		this.pos = pos;
		this.map =map;
	}
	
	public void update(int delta) {
		
	}
	
	public void render(GraphicsHelper gh, Vector2i offset) {
		gh.drawImage(sprite, pos.copy().add(offset));
	}
	
}
