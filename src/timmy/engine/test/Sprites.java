package timmy.engine.test;

import timmy.engine.graphics.Sprite;
import timmy.engine.graphics.SpriteSheet;

public class Sprites {

	
	private static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 32, 32);
	
	public static Sprite grass = tiles.getSprite(0, 0);
	public static Sprite grass2 = tiles.getSprite(0, 1);
	public static Sprite grass3 = tiles.getSprite(0, 2);
	


	
}
