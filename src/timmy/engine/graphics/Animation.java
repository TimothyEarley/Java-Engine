package timmy.engine.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation extends Sprite {
	
	/**
	 * keep track of all anims
	 */
	private static ArrayList<Animation> anims = new ArrayList<Animation>();
	
	public static void tickAll() {
		for (Animation animation : anims) {
			animation.tick();
		}
	}

	private Sprite[] frames;

	private int currentFrame, tick, ticksPerFrame;

	public Animation(String ref, int width, int height, int ticksPerFrame) {

		super(ref);

		
		Sprite[] frames = new Sprite[image.getWidth() / width * image.getHeight() * height];

		for (int x = 0; x < image.getWidth(); x += width) {
			for (int y = 0; y < image.getHeight(); y += height) {
				frames[x + y * width] = new Sprite(image.getSubimage(x, y, width, height));
			}
		} 
		
		setup(frames, ticksPerFrame);

	}
	
	public Animation(Sprite ... frames) {
		this(20, frames);
	}

	public Animation(int ticksPerFrame, Sprite ... frames) {
		super();
		setup(frames, ticksPerFrame);
	}

	private void setup(Sprite[] frames, int ticksPerFrame) {
		this.ticksPerFrame = ticksPerFrame;
		this.frames = frames;	
		anims.add(this);
	}

	@Override
	public BufferedImage getImage() {
		if (frames.length == 0)
			return image;
		return frames[currentFrame].getImage();
	}

	@Override
	public int getWidth() {
		if (frames.length == 0)
			return image.getWidth();
		return frames[0].getWidth();
	}

	@Override
	public int getHeight() {
		if (frames.length == 0)
			return image.getHeight();
		return frames[0].getHeight();
	}  

	@Override
	public Sprite colour(float rF, float gF, float bF) {
		for (Sprite sprite : frames) {
			sprite.colour(rF, gF, bF);
		}
		return this;
	}

	@Override
	public Sprite multAlpha(float m) {
		for (Sprite sprite : frames) {
			sprite.multAlpha(m);
		}
		return this;
	}

	@Override
	public Sprite copy() {
		return new Animation(ticksPerFrame, deepCopy(frames));
	}

	private Sprite[] deepCopy(Sprite[] frames) {
		Sprite[] newFrames = new Sprite[frames.length];
		for (int i = 0; i < frames.length; i++) {
			newFrames[i] = frames[i].copy();
		}
		return newFrames;
	}

	public void nextFrame() {
		currentFrame++;
		if (currentFrame >= frames.length) {
			currentFrame = 0;
		}
	}
	
	public void tick() {
		tick++;
		
		if ( tick > ticksPerFrame) {
			nextFrame();
			tick = 0;
		}
	}
}
