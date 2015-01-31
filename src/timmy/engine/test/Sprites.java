package timmy.engine.test;

import timmy.engine.graphics.Animation;
import timmy.engine.graphics.Sprite;
import timmy.engine.graphics.SpriteSheet;

public class Sprites {

	
	private static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 32, 32);
	
	public static Sprite grass = tiles.getSprite(0, 0);
	public static Sprite grass2 = tiles.getSprite(0, 1);
	public static Sprite grass3 = tiles.getSprite(0, 2);
	
	public static Sprite torch = new Animation(tiles.getSprite(15, 0), tiles.getSprite(15, 1), tiles.getSprite(15, 2));
	
	public static Sprite grassExtra = tiles.getSprite(1, 0);
	public static Sprite grassExtra2 = tiles.getSprite(1, 1);
	
	public static Sprite rock = tiles.getSprite(2, 0);

	public static Sprite brown_wall = tiles.getSprite(3, 0);
	public static Sprite gray_wall = tiles.getSprite(3, 1);
	public static Sprite dark_brown_wall = tiles.getSprite(3, 2);

	public static Sprite gravel = tiles.getSprite(4, 1);
	public static Sprite hedge = tiles.getSprite(5, 0);

	public static Sprite water = tiles.getSprite(6, 0);
	public static Sprite floor = tiles.getSprite(7, 0);


	
}
