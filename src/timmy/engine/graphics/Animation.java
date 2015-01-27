package timmy.engine.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation extends Sprite {

	private ArrayList<Sprite> frames;

	private int currentFrame, tick, ticksPerFrame;

	public Animation(String ref, int width, int height, int ticksPerFrame) {

		super(ref);

		this.ticksPerFrame = ticksPerFrame;
		
		frames = new ArrayList<Sprite>();

		for (int x = 0; x < image.getWidth(); x += width) {
			for (int y = 0; y < image.getHeight(); y += height) {
				frames.add(new Sprite(image.getSubimage(x, y, width, height)));
			}
		}

	}

	public Animation(ArrayList<Sprite> frames) {
		super(1, 1, 0);
		this.frames = frames;
	}

	@Override
	public BufferedImage getImage() {
		if (frames.size() == 0)
			return image;
		return frames.get(currentFrame).getImage();
	}

	@Override
	public int getWidth() {
		if (frames.size() == 0)
			return image.getWidth();
		return frames.get(0).getWidth();
	}

	@Override
	public int getHeight() {
		if (frames.size() == 0)
			return image.getHeight();
		return frames.get(0).getHeight();
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
		return new Animation(deepCopy(frames));
	}

	private ArrayList<Sprite> deepCopy(ArrayList<Sprite> frames) {
		ArrayList<Sprite> newFrames = new ArrayList<Sprite>();
		for (Sprite sprite : frames) {
			newFrames.add(sprite.copy());
		}
		return newFrames;
	}

	public void nextFrame() {
		currentFrame++;
		if (currentFrame >= frames.size()) {
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
