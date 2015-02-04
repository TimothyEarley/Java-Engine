package timmy.engine.tilemap.entities.mobs;

import timmy.engine.tilemap.TileMap;
import timmy.engine.tilemap.entities.Entity;
import timmy.engine.util.vectors.Vector2i;

public abstract class Mob extends Entity {

	public Mob(Vector2i pos, TileMap map) {
		super(pos, map);
  	}
	
	public void move(float dx, float dy) {
		
		if (dx != 0 && dy != 0) {
			move(0, dy);
			move(dx, 0);
		} else if (dx >= map.movePrecission || dy >= map.movePrecission) {
			move(dx/2, dy/2);
			move(dx/2, dy/2);
		} else {
			
			// TODO collision code
			
		}
		
	}
	
}
