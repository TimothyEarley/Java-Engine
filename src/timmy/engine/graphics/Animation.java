package timmy.engine.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation extends Sprite {

	private static final int milisecondsPerFrameDef = 500;

	/**
	 * keep track of all anims
	 */
	private static ArrayList<Animation> anims = new ArrayList<Animation>();

	public static boolean updated;

	public static void tickAll(int delta) {
		updated = false;
		for (Animation animation : anims) {
			animation.tick(delta);
		}
	}

	private Sprite[] frames;

	private int currentFrame, delta, milisecondsPerFrame;

	public Animation(String ref, int width, int height, int milisecondsPerFrame) {

		super(ref);

		Sprite[] frames = new Sprite[image.getWidth() / width * image.getHeight() * height];

		for (int x = 0; x < image.getWidth(); x += width) {
			for (int y = 0; y < image.getHeight(); y += height) {
				frames[x + y * width] = new Sprite(image.getSubimage(x, y, width, height));
			}
		}

		setup(frames, milisecondsPerFrame);

	}

	public Animation(Sprite... frames) {
		this(milisecondsPerFrameDef, frames);
	}

	public Animation(int ticksPerFrame, Sprite... frames) {
		super();
		setup(frames, ticksPerFrame);
	}

	private void setup(Sprite[] frames, int milisecondsPerFrame) {
		this.milisecondsPerFrame = milisecondsPerFrame;
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
		return new Animation(milisecondsPerFrame, deepCopy(frames));
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

	public void tick(int delta) {
		this.delta += delta;
		if (this.delta / milisecondsPerFrame != 0) {
			updated = true;
			nextFrame();
			this.delta = 0;
		}
	}
}
