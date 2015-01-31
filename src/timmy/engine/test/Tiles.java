package timmy.engine.test;

import java.util.HashMap;

import timmy.engine.tilemap.Tile;

public class Tiles {

	public static Tile grass = new Tile(Sprites.grass, "Grass");
	public static Tile grass2 = new Tile(Sprites.grass2, "Grass");
	public static Tile grass3 = new Tile(Sprites.grass3, "Grass");
	
	public static Tile torch = new Tile(Sprites.torch, "Torch");
	
	public static Tile grassExtra = new Tile(Sprites.grassExtra, "Extra Grass");
	public static Tile grassExtra2 = new Tile(Sprites.grassExtra2, "Extra Grass");
	
	public static Tile rock = new Tile(Sprites.rock, "Rock");
	
	
	public static Tile brown_wall = new Tile(Sprites.brown_wall, "Brown Wall");
	public static Tile gray_wall = new Tile(Sprites.gray_wall, "Gray Wall");
	public static Tile dark_brown_wall = new Tile(Sprites.dark_brown_wall, "Dark Brown Wall");
	public static Tile floor = new Tile(Sprites.floor, "Floor");
	
	public static Tile gravel = new Tile(Sprites.gravel, "Gravel");
	public static Tile hedge = new Tile(Sprites.hedge, "Hedge");
	
	public static Tile water = new Tile(Sprites.water, "Water");

	public static HashMap<Integer, Tile[]> getColours() {
			HashMap<Integer, Tile[]> map = new HashMap<Integer, Tile[]>();
			map.put(0xff00ff00, new Tile[] { grass, grass2, grass3 });
			map.put(0xffffff00, new Tile[] { grassExtra, grassExtra2 });
			map.put(0xff7f7f00, new Tile[] { rock });
			map.put(0xffC56A30, new Tile[] { brown_wall });
			map.put(0xff777777, new Tile[] { gravel });
			map.put(0xff007700, new Tile[] { hedge });
			map.put(0xff1111ff, new Tile[] { water });
			map.put(0xffff7700, new Tile[] { floor });
			map.put(0xff414141, new Tile[] { gray_wall });
			map.put(0xffCC6600, new Tile[] { dark_brown_wall });
			map.put(0xffFFFF64, new Tile[] { torch });

			return map;
	}
		
}
