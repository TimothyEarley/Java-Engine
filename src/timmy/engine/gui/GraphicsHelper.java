package timmy.engine.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import timmy.engine.graphics.Sprite;
import timmy.engine.util.vectors.Vector2f;
import timmy.engine.util.vectors.Vector2i;

public class GraphicsHelper {

	public static Font font;

	private Graphics2D g2d;

	private Vector2i offset;
	private Vector2f stretch;

	private Display parent;

	public GraphicsHelper(BufferStrategy bs, Vector2i offset, Vector2f stretch, Display parent) {
		if (bs != null)
			this.g2d = (Graphics2D) bs.getDrawGraphics();
		this.offset = offset;
		this.stretch = stretch;
		this.parent = parent;
	}

	public GraphicsHelper(Graphics2D g2d) {
		this.g2d = g2d;
	}

	public void clearSreen(Vector2i size) {
		setColor(Display.backgroundColor);
		g2d.fillRect(0, 0, transformW(size.x), transformH(size.y));
	}

	public void clearSides() {
		setColor(Display.backgroundColor);
		if (parent != null) {
			// left edge
			g2d.fillRect(-1, 0, offset.x + 1, (int) (1.1f * transformH(parent.size.y)));
			// right edge
			g2d.fillRect(transformW(parent.size.x) + offset.x, 0, offset.x + 10, (int) (1.1f * transformH(parent.size.y)));
			// top edge
			g2d.fillRect(0, -1, transformW(parent.size.x), offset.y + 1);
			// bottom edge
			g2d.fillRect(0, transformH(parent.size.y) + offset.y, transformW(parent.size.x), offset.y + 10);
		}
	}

	// reverse transform
	public Vector2i mouseToScreen(Vector2i v) {
		if (parent != null) {
			v.x = (int) ((v.x - offset.x) / stretch.x);
			v.y = (int) ((v.y - offset.y) / stretch.y);
		}
		return v;
	}

	public int transformX(int x) {
		if (parent != null) {
			return (int) (offset.x + x * stretch.x);
		} else
			return x;
	}

	public int transformY(int y) {
		if (parent != null) {
			return (int) (offset.y + y * stretch.y);
		} else
			return y;
	}

	public int transformW(int w) {
		if (parent != null) {
			return (int) (w * stretch.x);
		} else
			return w;
	}

	public int transformH(int h) {
		if (parent != null) {
			return (int) (h * stretch.y);
		} else
			return h;
	}

	public void dispose() {
		g2d.dispose();
	}

	public void setColor(Color c) {
		g2d.setColor(c);
	}

	public void setAlpha(float opacity) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	public void drawImage(Sprite sprite, Vector2i loc, int width, int height) {
		drawImage(sprite, loc.x, loc.y, width, height);
	}

	public void drawImage(Sprite sprite, int x, int y, Vector2i size) {
		drawImage(sprite, x, y, size.x, size.y);
	}

	public void drawImage(Sprite sprite, int x, int y, int w, int h) {
		g2d.drawImage(sprite.getImage(), transformX(x), transformY(y), transformW(w), transformH(h), null);
	}

	public void drawImage(Sprite sprite, Vector2i loc) {
		drawImage(sprite, loc.x, loc.y);
	}

	public void drawImage(Sprite sprite, int x, int y) {
		drawImage(sprite, x, y, sprite.getWidth(), sprite.getHeight());
	}

	public void drawImage(Sprite sprite, Vector2f pos, float angle, Vector2f center) {

		AffineTransform at = new AffineTransform();

		int tx = transformX((int) pos.x);
		int ty = transformY((int) pos.y);

		float scaleW = transformW(sprite.getWidth()) / sprite.getWidth();
		float scaleH = transformH(sprite.getHeight()) / sprite.getHeight();

		at.translate(tx, ty);
		at.rotate(angle, center.x, center.y);
		at.scale(scaleW, scaleH);

		g2d.drawImage(sprite.getImage(), at, null);

	}

	public void drawImage(Sprite sprite, Vector2f pos, float angle) {

		AffineTransform at = new AffineTransform();

		int tx = transformX((int) pos.x);
		int ty = transformY((int) pos.y);

		float scaleW = transformW(sprite.getWidth()) / sprite.getWidth();
		float scaleH = transformH(sprite.getHeight()) / sprite.getHeight();

		at.translate(tx, ty);
		at.rotate(angle, sprite.getWidth() / 2, sprite.getHeight() / 2);
		at.scale(scaleW, scaleH);

		g2d.drawImage(sprite.getImage(), at, null);

	}

	public void drawImage(Sprite sprite, Vector2f pos, AffineTransform at) {
		at.translate(transformX((int) pos.x), transformY((int) pos.y));
		g2d.drawImage(sprite.getImage(), at, null);
	}

	public void drawImage(Sprite sprite, AffineTransform at) {
		int oldX = (int) at.getTranslateX();
		int oldY = (int) at.getTranslateY();
		at.translate(-oldX, -oldY);
		at.translate(transformX(oldX), transformY(oldY));
		g2d.drawImage(sprite.getImage(), at, null);
	}

	public void drawImage(BufferedImage image) {
		drawImage(image, 0, 0);
	}

	public void drawImage(BufferedImage image, int x, int y) {
		drawImage(image, x, y, image.getWidth(), image.getHeight());
	}

	public void drawImage(BufferedImage image, int x, int y, int w, int h) {
		g2d.drawImage(image, transformX(x), transformY(y), transformW(w), transformH(h), null);
	}

	public void fillRect(Vector2i coords, int width, int height) {
		fillRect(coords.x, coords.y, width, height);
	}

	public void fillRect(int x, int y, int width, int height) {
		g2d.fillRect(transformX(x), transformY(y), transformW(width), transformH(height));
	}

	public void drawRect(int x, int y, int w, int h) {
		g2d.drawRect(transformX(x), transformY(y), transformW(w), transformH(h));
	}

	/**
	 * 
	 * @param a
	 *            the starting point
	 * @param l
	 *            the length
	 * @param angle
	 *            in radians
	 */
	public void drawLine(Vector2i a, int l, double angle) {
		drawLine(a, new Vector2i((int) (l * Math.sin(angle)), (int) (l * Math.cos(angle))).add(a));
	}

	public void drawLine(Vector2i a, Vector2i b) {
		g2d.drawLine(transformX(a.x), transformY(a.y), transformX(b.x), transformY(b.y));
	}

	public void drawCircle(int x, int y, int radius, int strength) {
		g2d.setStroke(new BasicStroke(strength));
		g2d.drawOval(transformX(x), transformY(y), transformW(2*radius), transformH(2*radius));
	}

	public void fillCircle(Vector2f pos, int radius) {
		fillCircle(pos.toVector2i(), radius);
	}

	public void fillCircle(Vector2i pos, int radius) {
		fillCircle(pos.x, pos.y, radius);
	}

	public void fillCircle(int x, int y, int radius) {
		g2d.fillOval(transformX(x), transformY(y), transformW(2*radius), transformH(2*radius));
	}

	public void drawStringRight(String string, int x, int y, float height) {
		drawStringRight(string, x, y, font.deriveFont(height));
	}

	/**
	 * Draws to the right of x
	 * @param string
	 * @param x
	 * @param y
	 * @param font
	 */
	public void drawStringRight(String string, int x, int y, Font font) {
		if (string == null || font == null)
			return;
		x = transformX(x);
		y = transformY(y);
		g2d.setFont(deriveFont(font));
		int height = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getHeight();

		g2d.drawString(string, x, y - height / 2);
	}

	public void drawStringLeft(String string, int x, int y, float height) {
		drawStringLeft(string, x, y, font.deriveFont(height));
	}

	/**
	 * Draws  to the left of x
	 * @param string
	 * @param x
	 * @param y
	 * @param font
	 */
	public void drawStringLeft(String string, int x, int y, Font font) {
		if (string == null || font == null)
			return;
		x = transformX(x);
		y = transformY(y);
		g2d.setFont(deriveFont(font));
		int lenght = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getWidth();
		int height = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getHeight();

		g2d.drawString(string, x - lenght, y - height / 2);
	}

	public void drawStringCentered(String string, int x, int y, float height) {
		drawStringCentered(string, x, y, font.deriveFont(height));
	}


	/**
	 * Centers the string in x and y direction. Includes line breaks
	 * @param string
	 * @param x
	 * @param y
	 * @param font
	 */
	public void drawStringCentered(String string, int x, int y, Font font) {
		if (string == null || font == null)
			return;
		g2d.setFont(deriveFont(font));
		int height = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getHeight();
		String[] lines = string.split("\n", -1);
		if (lines.length != 1) {
			for (int i = 0; i < lines.length; i++) {
				// convert empty lines to a line with space, so that double \n have an effect
				drawStringCentered(lines[i].isEmpty() ? " " : lines[i], x, y + (i - lines.length/2) * height, font);
			}
			return;
		}

		x = transformX(x);
		y = transformY(y);
		int lenght = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getWidth();
		g2d.drawString(string, x - lenght / 2, y - height / 2);
	}

	public void drawStringCenteredTop(String string, int x, int y, float height) {
		drawStringCenteredTop(string, x, y, font.deriveFont(height));
	}

	public void drawStringCenteredTop(String string, int x, int y, Font font) {
		if (string == null || font == null)
			return;
		x = transformX(x);
		y = transformY(y);
		g2d.setFont(deriveFont(font));
		int lenght = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getWidth();
		int height = (int) g2d.getFontMetrics().getStringBounds(string, g2d).getHeight();

		g2d.drawString(string, x - lenght / 2, y + height / 2);
	}

	private Font deriveFont(Font font) {
		return font.deriveFont((float) transformH(font.getSize()));
	}

	public void fillPolygon(Polygon poly) {
		poly = new Polygon(poly.xpoints, poly.ypoints, poly.npoints);
		for (int i = 0; i < poly.npoints; i++) {
			poly.xpoints[i] = transformX(poly.xpoints[i]);
			poly.ypoints[i] = transformY(poly.ypoints[i]);
		}
		g2d.fillPolygon(poly);
	}

	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		g2d.fillArc(transformX(x), transformY(y), transformW(width), transformH(height), startAngle, arcAngle);
	}

}