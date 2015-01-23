package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import util.Crash;

public class Sprite {

	public static boolean deferred = false;

	private static ArrayList<Sprite> toLoad = new ArrayList<Sprite>();

	BufferedImage image;
	String res;

	/**
	 * @return sprite loaded
	 */
	public static Sprite loadNext() {
		if (toLoad.size() <= 0)
			return null;

		Sprite s = toLoad.remove(0);
		s.load();
		return s;
	}

	public static int leftToLoad() {
		return toLoad.size();
	}

	/**
	 * @param res
	 *            in the form: "/..."
	 */
	public Sprite(String res) {
		this.res = res;
		if (deferred)
			toLoad.add(this);
		else
			load();

	}

	public Sprite(BufferedImage bufferedImage) {
		image = bufferedImage;
	}

	public Sprite(int w, int h, int c) {
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				image.setRGB(x, y, c);
			}
		}
	}

	public void load() {
		if (image != null)
			return;
		try {
			image = ImageIO.read(Sprite.class.getResource(res));
		} catch (Exception e) {
			Crash.crash(e, "Failed to load image: " + res);
		}
	}

	public Sprite getScaledSprite(int newWidth, int newHeight) {
		return new Sprite(this.getScaled(newWidth, newHeight));
	}

	public BufferedImage getScaled(float scale) {
		return getScaled((int) (getImage().getWidth() * scale), (int) (getImage().getHeight() * scale));
	}

	/**
	 * 
	 * @param width
	 *            - the desired with
	 * @param height
	 *            - the desired height
	 * @param hints
	 *            - algorithm type
	 * @return
	 */
	public BufferedImage getScaled(int newWidth, int newHeight) {
		if (newWidth == 0 || newHeight == 0) {
			return null;
		}
		BufferedImage resized = new BufferedImage(newWidth, newHeight, getImage().getType());
		Graphics2D g = resized.createGraphics();
		g.drawImage(image, 0, 0, newWidth, newHeight, 0, 0, getImage().getWidth(), getImage().getHeight(), null);
		g.dispose();
		return resized;
	}

	public Sprite multAlpha(float m) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int argb = getImage().getRGB(x, y);
				int a = (argb & 0xff000000) >> 24;
				int r = (argb & 0x00ff0000) >> 16;
				int g = (argb & 0x0000ff00) >> 8;
				int b = (argb & 0x000000ff) >> 0;

				a *= m;
				this.getImage().setRGB(x, y, a << 24 | r << 16 | g << 8 | b);
			}
		}

		return this;
	}

	public Sprite colour(int colour) {
		return colour(((colour & 0xff0000) >> 16) / (float) 0xff, ((colour & 0x00ff00) >> 8) / (float) 0xff, ((colour & 0x0000ff) >> 0) / (float) 0xff);
	}

	/**
	 * Mult the colours by these factors
	 * 
	 * @param r
	 *            betwenn 0 and 1
	 * @param g
	 *            betwenn 0 and 1
	 * @param b
	 *            betwenn 0 and 1
	 * @return
	 */
	public Sprite colour(float rF, float gF, float bF) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int argb = getImage().getRGB(x, y);
				int a = (argb & 0xff000000) >> 24;
				int r = (argb & 0x00ff0000) >> 16;
				int g = (argb & 0x0000ff00) >> 8;
				int b = (argb & 0x000000ff) >> 0;

				r = (int) (r * rF);
				g = (int) (g * gF);
				b = (int) (b * bF);

				this.getImage().setRGB(x, y, a << 24 | r << 16 | g << 8 | b << 0);
			}
		}
		return this;
	}

	/**
	 * With alpha
	 * 
	 * @param searchFor
	 * @param replaceWith
	 * @return
	 */
	public Sprite replaceColour(int searchFor, int replaceWith) {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int argb = getImage().getRGB(x, y);

				if (argb == searchFor)
					this.getImage().setRGB(x, y, replaceWith);
			}
		}
		return this;
	}

	public Sprite copy() {
		return new Sprite(deepCopy(getImage()));
	}

	public BufferedImage getImage() {
		load();
		return image;
	}

	public int getWidth() {
		return getImage().getWidth();
	}

	public int getHeight() {
		return getImage().getHeight();
	}
	
	public String getPath() {
		return res;
	}

	private static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}
