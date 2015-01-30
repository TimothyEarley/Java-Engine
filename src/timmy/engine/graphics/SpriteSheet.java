package timmy.engine.graphics;

public class SpriteSheet extends Sprite {
	
	int spriteWidth, spriteHeight;

	public SpriteSheet(String res, int spriteWidth, int spriteHeight) {
		super(res);
		this.spriteWidth = spriteHeight;
		this.spriteHeight = spriteHeight;
	}
	
	
	public Sprite getSprite(int x, int y) {
		return new Sprite(image.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight));
	}

}
